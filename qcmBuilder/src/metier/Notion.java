package src.metier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import src.metier.question.*;
import src.metier.reponse.*;

public class Notion
{
	private String         nom         ;
	private Ressource      ressource   ;
	private List<Question> lstQuestions;

	public Notion(String nom, Ressource ressource)
	{
		this.nom          = nom;
		this.ressource    = ressource;
		this.lstQuestions = lireQuestions();
	}

	private List<Question> lireQuestions()
	{
		List<Question> lstQuestions = new ArrayList<>();
		try
		{
			File    fileTextQuestion = new File("../data/questions/" + this.ressource.getCode() + "_" + this.ressource.getNom() + "_" + this.nom + ".rtf");
			File    fileInformations = new File("../data/questions/" + this.ressource.getCode() + "_" + this.ressource.getNom() + "_" + this.nom + ".csv");

			boolean fichieCree = false;
			if (!fileTextQuestion.exists())
			{
				try (PrintWriter writerQues = new PrintWriter(new FileWriter(fileTextQuestion)))
				{
					writerQues.println("{\\rtf1\\ansi\\deff0\n{\\fonttbl{\\f0\\fswiss Helvetica;}}\n\\viewkind4\\uc1\\pard\\f0\n");
				}
				catch (IOException e) { e.printStackTrace(); }
				fichieCree = true;
			}

			if (!fileInformations.exists())
			{
				try (PrintWriter writerData = new PrintWriter(new FileWriter(fileInformations)))
				{
					writerData.println("N_QUESTION;POINT;TYPE;NIVEAU;TEMPS");
				}
				catch (IOException e) { e.printStackTrace(); }
				fichieCree = true;
			}
			if ( fichieCree )
				return new ArrayList<>();

			Scanner scTextQuestion   = new Scanner( fileTextQuestion );
			Scanner scInformations   = new Scanner( fileInformations );
			
			if ( !scTextQuestion.hasNextLine() || !scInformations.hasNextLine() )
			{
				scTextQuestion.close();
				scInformations.close();
				return new ArrayList<>();
			}
			else
			{
				scInformations.nextLine();
			}

			while ( scTextQuestion.hasNextLine() && scInformations.hasNextLine() )
			{
				String lineTextQuestion = scTextQuestion.nextLine();
				String lineInformations = scInformations.nextLine();
				while ( scTextQuestion.hasNextLine() && ! lineTextQuestion.contains("Question") )
				{
					lineTextQuestion = scTextQuestion.nextLine();
				}

				if ( !scTextQuestion.hasNextLine() ) break;

				String[] informations = lineInformations.split(";");

				lineTextQuestion = scTextQuestion.nextLine();
				String text      = lineTextQuestion.substring(0, lineTextQuestion.indexOf("\\par"));

				double nbPoint   = Double.parseDouble( informations[1] );

				String type      = informations[2];

				String sNiveau   = informations[3];

				int    temps     = Integer.parseInt( informations[4] );

				int niveau;
				switch(sNiveau)
				{
					case "TF" -> { niveau = 1; }
					case "F"  -> { niveau = 2; }
					case "M"  -> { niveau = 3; }
					case "D"  -> { niveau = 4; }
					default   ->
					{
						scTextQuestion.close();
						scInformations.close();
						throw new IllegalArgumentException("Le niveau doit être appartenir aux options suivantes : 'TF','F','M','D'");
					}
				}

				scTextQuestion.nextLine();
				lineTextQuestion = scTextQuestion.nextLine();

				if ( type.equals("Association") )
				{
					List<ReponseAssociation> lstReponse = new ArrayList<>();

					while (!lineTextQuestion.contains("\\par{Fin}"))
					{
						ReponseAssociation reponseA;
						String textReponseA = lineTextQuestion.substring(lineTextQuestion.indexOf("} ") + 2, lineTextQuestion.indexOf("::"));

						ReponseAssociation reponseB;
						String textReponseB = lineTextQuestion.substring(lineTextQuestion.indexOf("::") + 2);

						// System.out.println(lineTextQuestion);
						// System.out.println(textReponseA);
						// System.out.println(textReponseB);
						// System.out.println();

						reponseA = new ReponseAssociation(
						                                  textReponseA,
						                                  null,
						                                  Integer.parseInt(lineTextQuestion.substring(lineTextQuestion.indexOf("{") + 1, lineTextQuestion.indexOf(":}") - 1)),
						                                  false
						                                 );
						lstReponse.add( reponseA );

						reponseB = new ReponseAssociation(
						                                  textReponseB,
						                                  reponseA,
						                                  Integer.parseInt(lineTextQuestion.substring(lineTextQuestion.indexOf("{") + 1, lineTextQuestion.indexOf(":}") - 1)),
						                                  false
						                                 );
						reponseA.setReponseAssocie( reponseB );
						lstReponse.add( reponseB );

						lineTextQuestion = scTextQuestion.nextLine();
					}

					Association question = new Association(this, text, temps, nbPoint, niveau, lstReponse, "");
					lstQuestions.add(question);
				}
				else if ( type.equals("Elimination"))
				{
					List<ReponseElimination> lstReponse = new ArrayList<>();

					int nbIndice = 0;

					while ( !lineTextQuestion.contains("\\par{Fin}") )
					{
						// System.out.println(lineTextQuestion);
						// System.out.println(lineTextQuestion.substring(lineTextQuestion.indexOf("} ") + 2, lineTextQuestion.indexOf("|")));
						// System.out.println(lineTextQuestion.substring(lineTextQuestion.indexOf("|") + 1, lineTextQuestion.indexOf("||")));
						// System.out.println(lineTextQuestion.substring(lineTextQuestion.indexOf("||") + 2, lineTextQuestion.indexOf("/")));
						// System.out.println(lineTextQuestion.substring(lineTextQuestion.indexOf("/") + 1));
						// System.out.println();

						lstReponse.add(new ReponseElimination(
						                                      lineTextQuestion.substring(lineTextQuestion.indexOf("} ") + 2, lineTextQuestion.indexOf("|")),
						                                      lineTextQuestion.substring(lineTextQuestion.indexOf("|") + 1, lineTextQuestion.indexOf("||")),
						                                      Integer.parseInt  (lineTextQuestion.substring(lineTextQuestion.indexOf("||") + 2, lineTextQuestion.indexOf("/"))),
						                                      Double.parseDouble(lineTextQuestion.substring(lineTextQuestion.indexOf("/") + 1))
						                                     ));

						if ( nbIndice < Integer.parseInt( lineTextQuestion.substring(lineTextQuestion.indexOf("||") + 2, lineTextQuestion.indexOf("/")) ) )
							nbIndice  = Integer.parseInt( lineTextQuestion.substring(lineTextQuestion.indexOf("||") + 2, lineTextQuestion.indexOf("/")) );

						lineTextQuestion = scTextQuestion.nextLine();
					}

					Elimination question = new Elimination(this, text, temps, nbPoint, niveau, lstReponse, nbIndice, "");
					lstQuestions.add(question);
				}
				else if (type.equals("QCM"))
				{
					List<ReponseQCM> lstReponse = new ArrayList<>();
					while (!lineTextQuestion.contains("\\par{Fin}"))
					{
						lstReponse.add(new ReponseQCM(
						                              lineTextQuestion.substring(lineTextQuestion.indexOf("} ") + 2, lineTextQuestion.indexOf("|")),
						                              lineTextQuestion.substring(lineTextQuestion.indexOf("|") + 1)
						                             ));
						lineTextQuestion = scTextQuestion.nextLine();
					}

					QCM question = new QCM(this, text, temps, nbPoint, niveau, lstReponse, "");
					lstQuestions.add(question);
				}
				else
				{
					scTextQuestion.close();
					scInformations.close();
					throw new IllegalArgumentException("Le type doit être appartenir aux options suivantes : 'Association','Elimination','QCM'"); 
				}
			}
			scTextQuestion.close();
			scInformations.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

		return lstQuestions;
	}

	public String         getNom()
	{
		String nomN = this.nom.substring(0);

		return nomN;
	}

	public Ressource      getRessource()
	{
		Ressource ressourceN = new Ressource( this.ressource.getCode(), this.ressource.getNom() );

		return ressourceN;
	}

	public List<Question> getQuestions()
	{
		List<Question> questionsN = new ArrayList<>();

		for (Question q : this.lstQuestions)
		{
			questionsN.add( q );                                           // Intégrité des données pas folle
		}

		return questionsN;
	}

	public boolean setNom (String nom)
	{ 
		this.nom = nom;
		return true;
	}

	public boolean setRessource (Ressource ressource)
	{
		this.ressource = ressource;
		return true;
	}

	public boolean ajouterQuestion(Question question)
	{
		for (Question q : lstQuestions)
		{
			if (q.getText().equals(question.getText()))
			{
				question = q;
			}
		}
	
		if (!lstQuestions.contains(question))
		{
			lstQuestions.add(question);
	
			try
			{
				File fileQues = new File("../data/questions/" + this.ressource.getCode() + "_" + this.ressource.getNom() + "_" + this.nom + ".rtf");
				File fileData = new File("../data/questions/" + this.ressource.getCode() + "_" + this.ressource.getNom() + "_" + this.nom + ".csv");

				if (fileQues.exists() && fileQues.length() > 0)
				{
					RandomAccessFile raf = new RandomAccessFile(fileQues, "rw");
					long length = raf.length();
					if (length > 0)
					{
						raf.setLength(length - 2); // SUPPRIME LE DERNIER }
					}
					raf.close();
				}

				try (PrintWriter writerQues = new PrintWriter(new FileWriter(fileQues, true));
					 PrintWriter writerData = new PrintWriter(new FileWriter(fileData, true)))
				{
					String strReponses = "";
	
					if (question.getClass().getSimpleName().equals("QCM"))
					{
						int indRep = 1;
						QCM qcm = (QCM) question;
	
						for (ReponseQCM r : qcm.getlstReponses())
						{
							strReponses += "\\par{" + indRep++ + " :} " + r.getText() + "|" + (r.estVrai() ? "Vrai" : "Faux") + "\n";
						}
					}
					else if (question.getClass().getSimpleName().equals("Elimination"))
					{
						int indRep = 1;
						Elimination elimination = (Elimination) question;
	
						for (ReponseElimination r : elimination.getLstReponses())
						{
							strReponses += "\\par{" + indRep++ + " :} " + (r.estVrai() ? "Vrai" : "Faux") + "|" + r.getText() + "||" + r.getOrdreIndice() + "/" + r.getNbPointPerdu() + "\n";
						}
					}
					else if (question.getClass().getSimpleName().equals("Association"))
					{
						int indRep = 1;
						Association association = (Association) question;
	
						for (ReponseAssociation r : association.getLstReponses())
						{
							if (r.estAGauche())
							{
								strReponses += "\\par{" + indRep++ + " :} " + r.getText() + "::" + r.getReponseAssocie().getText() + "\n";
							}
						}
					}
	
					writerQues.println(
						"{\\b Question " + this.lstQuestions.size() + " :} \\par\n" +
						question.getText() + "\\par\n" +
						"{Reponses :}\n" +
						strReponses + "\\par{Fin}\n}"
					);

					writerData.println(
						this.lstQuestions.size()            + ";" +
						question.getNbPoint()               + ";" +
						question.getClass().getSimpleName() + ";" +
						question.getStringDifficulte()      + ";" +
						question.getTimer()
					);
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
	
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean supprimerQuestion (Question question)
	{
		if (question == null)
			return false;

		if (!lstQuestions.contains(question))
			return false;

			lstQuestions.remove(question);
		return true;
	}

	public boolean supprimerAllQuestions ()
	{
		File fileTextQuestion = new File("./data/questions/" + this.ressource.getCode() + "_" + this.ressource.getNom() + "_" + this.nom + ".rtf");
		File fileInformations = new File("./data/questions/" + this.ressource.getCode() + "_" + this.ressource.getNom() + "_" + this.nom + "_data.rtf");

		if (fileTextQuestion.exists()) { fileTextQuestion.delete(); }
		else { return false; }

		if (fileInformations.exists()) { fileInformations.delete(); }
		else { return false; }
		
		return true;
	
	}
	public Question rechercherQuestion (String text)
	{
		Question questionTrouvee = null;

		for (Question question : lstQuestions)
		{
			if (question.getText().equals(text))
				questionTrouvee = question;
		}

		return questionTrouvee;
	}
}
