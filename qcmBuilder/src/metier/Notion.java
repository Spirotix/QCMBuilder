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
			File fileInformations  = new File( "../data/questions_NOUVEAU/" + this.ressource.getCode() + "/" + this.nom + "/" + this.nom + ".csv" );

			Scanner scInformations   = new Scanner( fileInformations );
			
			if ( !scInformations.hasNextLine() )
			{
				scInformations.close();
				return new ArrayList<>();
			}
			else
			{
				scInformations.nextLine();
			}

			while ( scInformations.hasNextLine() )
			{
				File dossierComplement = new File( "../data/questions_NOUVEAU/" + this.ressource.getCode() + "/" + this.nom + "/question_" + lstQuestions.size() + "/complement" );
				dossierComplement.mkdirs();

				File fileTextQuestion  = new File( "../data/questions_NOUVEAU/" + this.ressource.getCode() + "/" + this.nom + "/question_" + lstQuestions.size() + "/text_question.rtf" );
				fileTextQuestion.getParentFile().mkdirs();

				// Créer les répertoires non existants (ou ce trouve le rtf)
				fileTextQuestion.getParentFile().mkdirs();

				if (!fileTextQuestion.exists())
				{
					try
					{
						PrintWriter tmp = new PrintWriter( new FileWriter(fileTextQuestion) );
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}

					System.out.println( "\tFICHIER text_question CREE" );
				}


				Scanner scTextQuestion   = new Scanner( fileTextQuestion );

				if ( !scTextQuestion.hasNextLine() || !scInformations.hasNextLine() )
				{
					scInformations.close();
					scTextQuestion.close();
					return new ArrayList<>();
				}

				String lineTextQuestion = scTextQuestion.nextLine();
				String lineInformations = scInformations.nextLine();

				while ( scTextQuestion.hasNextLine() )
				{
					lineTextQuestion += scTextQuestion.nextLine();
				}
				String textQuestion   = lineTextQuestion;

				String[] informations = lineInformations.split(";"); // N_QUESTION;NOMBRE_REPONSES;POINT;TYPE;NIVEAU;TEMPS;EXPLICATION

				int       nReponse    = Integer.parseInt  ( informations[0] );
				int       nbReponses  = Integer.parseInt  ( informations[1] );
				double    nbPoint     = Double.parseDouble( informations[2] );
				String    type        =                     informations[3];
				String    sNiveau     =                     informations[4];
				int       temps       = Integer.parseInt  ( informations[5] );
				String    explication =                     informations[6];

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

				if ( type.equals("Association") )
				{
					List<ReponseAssociation> lstReponse = new ArrayList<>();

					for ( int numReponse = 1 ; numReponse < nbReponses ; numReponse++ )
					{
						Scanner scReponse = new Scanner(
							new File( "../data/questions_NOUVEAU/" + this.ressource.getCode() + "/" + this.nom + "/question_" + (lstQuestions.size()+1) + "text_reponse_" + numReponse + ".rtf" )
						);

						String lineTextReponse = scReponse.nextLine();

						ReponseAssociation reponseA;
						String textReponseA = lineTextReponse.substring(0, lineTextReponse.indexOf("::"));

						ReponseAssociation reponseB;
						String textReponseB = lineTextReponse.substring(lineTextReponse.indexOf("::") + 2);

						// System.out.println(lineTextQuestion);
						// System.out.println(textReponseA);
						// System.out.println(textReponseB);
						// System.out.println();

						reponseA = new ReponseAssociation(
						                                  textReponseA,
						                                  null,
						                                  false
						                                 );
						lstReponse.add( reponseA );

						reponseB = new ReponseAssociation(
						                                  textReponseB,
						                                  reponseA,
						                                  false
						                                 );

						reponseA.setReponseAssocie( reponseB );

						lstReponse.add( reponseB );

						scReponse.close();
					}

					Association question = new Association(this, textQuestion, temps, nbPoint, niveau, lstReponse, explication);
					lstQuestions.add(question);
				}
				else if ( type.equals("Elimination"))
				{
					List<ReponseElimination> lstReponse = new ArrayList<>();

					int nbIndice = 0;

					for ( int numReponse = 1 ; numReponse < nbReponses ; numReponse++ )
					{
						Scanner scReponse = new Scanner(
							new File( "../data/questions_NOUVEAU/" + this.ressource.getCode() + "/" + this.nom + "/question_" + (lstQuestions.size()+1) + "text_reponse_" + numReponse + ".rtf" )
						);

						String lineTextReponse = scReponse.nextLine();

						// System.out.println(lineTextReponse);
						// System.out.println(lineTextReponse.substring(lineTextReponse.indexOf("} ") + 2, lineTextReponse.indexOf("|")));
						// System.out.println(lineTextReponse.substring(lineTextReponse.indexOf("|") + 1, lineTextReponse.indexOf("||")));
						// System.out.println(lineTextReponse.substring(lineTextReponse.indexOf("||") + 2, lineTextReponse.indexOf("/")));
						// System.out.println(lineTextReponse.substring(lineTextReponse.indexOf("/") + 1));
						// System.out.println();

						lstReponse.add(new ReponseElimination(
						                                      lineTextReponse.substring(lineTextReponse.indexOf("} ") + 2, lineTextReponse.indexOf("|")),
						                                      lineTextReponse.substring(lineTextReponse.indexOf("|") + 1, lineTextReponse.indexOf("||")),
						                                      Integer.parseInt  (lineTextReponse.substring(lineTextReponse.indexOf("||") + 2, lineTextReponse.indexOf("/"))),
						                                      Double.parseDouble(lineTextReponse.substring(lineTextReponse.indexOf("/") + 1))
						                                     ));

						if ( nbIndice < Integer.parseInt( lineTextReponse.substring(lineTextReponse.indexOf("||") + 2, lineTextReponse.indexOf("/")) ) )
							nbIndice  = Integer.parseInt( lineTextReponse.substring(lineTextReponse.indexOf("||") + 2, lineTextReponse.indexOf("/")) );

						scReponse.close();
					}

					Elimination question = new Elimination(this, textQuestion, temps, nbPoint, niveau, lstReponse, nbIndice, explication);
					lstQuestions.add(question);
				}
				else if (type.equals("QCM"))
				{
					List<ReponseQCM> lstReponse = new ArrayList<>();

					for ( int numReponse = 1 ; numReponse < nbReponses ; numReponse++ )
					{
						Scanner scReponse = new Scanner(
							new File( "../data/questions_NOUVEAU/" + this.ressource.getCode() + "/" + this.nom + "/question_" + (lstQuestions.size()+1) + "text_reponse_" + numReponse + ".rtf" )
						);

						String lineTextReponse = scReponse.nextLine();

						lstReponse.add(new ReponseQCM(
						                              lineTextReponse.substring(lineTextReponse.indexOf("} ") + 2, lineTextReponse.indexOf("|")),
						                              lineTextReponse.substring(lineTextReponse.indexOf("|") + 1)
						                             ));
						scReponse.close();
					}

					QCM question = new QCM(this, textQuestion, temps, nbPoint, niveau, lstReponse, explication);
					lstQuestions.add(question);
				}
				else
				{
					scTextQuestion.close();
					scInformations.close();
					throw new IllegalArgumentException("Le type doit être appartenir aux options suivantes : 'Association','Elimination','QCM'"); 
				}
				scTextQuestion.close();
			}
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
			questionsN.add( q );                                                                         // Intégrité des données pas folle
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
			if ( q.getText().equals(question.getText()) )
			{
				System.out.println( "Cette question existe déjà, veuillez changer la question." );
				return false;
			}
		}

		if ( !lstQuestions.contains(question) )
		{
			lstQuestions.add(question);

			try
			{
				// Créer le répertoire de la question, avec un rtf pour le texte de la question formaté
				File fileTextQuestion  = new File( "../data/questions_NOUVEAU/" + this.ressource.getCode() + "/" + this.nom + "/question_" + this.lstQuestions.size() + "/text_question.rtf" );

				// Créer les répertoires non existants (ou ce trouve le rtf)
				fileTextQuestion.getParentFile().mkdirs();

				if (!fileTextQuestion.exists())
				{
					try
					{
						PrintWriter writerQues = new PrintWriter( new FileWriter(fileTextQuestion) );
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}

				// Créer le répertoire complément pour les images ou audios potentiels
				File dossierComplement = new File( "../data/questions_NOUVEAU/" + this.ressource.getCode() + "/" + this.nom + "/question_" + this.lstQuestions.size() + "/complement" );

				// Créer les répertoires non existants et le répertoire complement
				dossierComplement.mkdirs();

				// On prend le nom_notion.csv de la notion pour y rajouter par la suite la question et ses informations
				File fileData = new File( "../data/questions_NOUVEAU/" + this.ressource.getCode() + "/" + this.nom + "/" + this.nom + ".csv" );

				try ( PrintWriter writerTextQuestion = new PrintWriter(new FileWriter(fileTextQuestion, false));
					  PrintWriter writerData         = new PrintWriter(new FileWriter(fileData        , true )))
				{
					writerTextQuestion.println( question.getText() );

					int indRep = 1;

					if (question.getClass().getSimpleName().equals("QCM"))
					{
						QCM qcm = (QCM) question;

						for (ReponseQCM r : qcm.getlstReponses())
						{
							try
							{
								PrintWriter writerTextReponse = new PrintWriter(
									new FileWriter(
										new File( "../data/questions_NOUVEAU/" + this.ressource.getCode() + "/" + this.nom + "/question_" + this.lstQuestions.size() + "/text_reponse_" + indRep++ + ".rtf" ),
										false
									)
								);

								writerTextReponse.println( r.getText() + "|" + (r.estVrai() ? "Vrai" : "Faux") );

								writerTextReponse.close();
							}
							catch (Exception e) { e.printStackTrace(); }
						}
					}
					else if (question.getClass().getSimpleName().equals("Elimination"))
					{
						Elimination elimination = (Elimination) question;

						for (ReponseElimination r : elimination.getLstReponses())
						{
							try
							{
								PrintWriter writerTextReponse = new PrintWriter(
									new FileWriter(
										new File( "../data/questions_NOUVEAU/" + this.ressource.getCode() + "/" + this.nom + "/question_" + this.lstQuestions.size() + "/text_reponse_" + indRep++ + ".rtf" ),
										false
									)
								);

								writerTextReponse.println( (r.estVrai() ? "Vrai" : "Faux") + "|" + r.getText() + "||" + r.getOrdreIndice() + "/" + r.getNbPointPerdu() );

								writerTextReponse.close();
							}
							catch (Exception e) { e.printStackTrace(); }
						}
					}
					else if (question.getClass().getSimpleName().equals("Association"))
					{
						Association association = (Association) question;

						for (ReponseAssociation r : association.getLstReponses())
						{
							if (r.estAGauche())
							{
								try
								{
									PrintWriter writerTextReponse = new PrintWriter(
										new FileWriter(
											new File( "../data/questions_NOUVEAU/" + this.ressource.getCode() + "/" + this.nom + "/question_" + this.lstQuestions.size() + "/text_reponse_" + indRep++ + ".rtf" ),
											false
										)
									);

									writerTextReponse.println( r.getText() + "::" + r.getReponseAssocie().getText() );

									writerTextReponse.close();
								}
								catch (Exception e) { e.printStackTrace(); }
							}
						}
					}

					writerData.println(
						this.lstQuestions.size()            + ";" +
						(indRep - 1)                        + ";" +
						question.getNbPoint()               + ";" +
						question.getClass().getSimpleName() + ";" +
						question.getStringDifficulte()      + ";" +
						question.getTimer()                 + ";" +
						question.getExplication()
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

		System.out.println("Supprimer2");
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
