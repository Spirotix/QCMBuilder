package src.metier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import src.metier.question.*;
import src.metier.reponse.*;

public class Notion
{
	private String         nom      ;
	private Ressource      ressource;
	private List<Question> questions;

	public Notion(String nom, Ressource ressource)
	{
		this.nom       = nom;
		this.ressource = ressource;
		this.questions = lireQuestions();
	}

	private List<Question> lireQuestions()
	{
		List<Question> questions = new ArrayList<>();
		try
		{
			boolean fichieCree = false;

			File fileTextQuestion = new File("../data/questions/" + this.ressource.getCode() + "_" + this.ressource.getNom() + "_" + this.nom + ".rtf");
			File fileInformations = new File("../data/questions/" + this.ressource.getCode() + "_" + this.ressource.getNom() + "_" + this.nom + "_data.rtf");

			if ( !fileTextQuestion.exists() )
			{
				try
				{
					if ( fileTextQuestion.createNewFile() )
					{
						System.out.println("Fichier créé : " + fileTextQuestion.getName());
						fichieCree = true;
					}
				}
				catch (Exception e)
				{
					System.out.println("Une erreur s'est produite : " + e.getMessage());
				}
			}
			if ( !fileInformations.exists() )
			{
				try
				{
					if ( fileInformations.createNewFile() )
					{
						System.out.println("Fichier créé : " + fileInformations.getName());
						fichieCree = true;
					}
				}
				catch (Exception e)
				{
					System.out.println("Une erreur s'est produite : " + e.getMessage());
				}
			}

			if ( fichieCree )
				return null;


			Scanner scTextQuestion = new Scanner( fileTextQuestion );
			Scanner scInformations = new Scanner( fileInformations );
			
			if ( !scTextQuestion.hasNextLine() || !scInformations.hasNextLine() )
			{
				scTextQuestion.close();
				scInformations.close();
				return null;
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

				if ( !scTextQuestion.hasNextLine() || !scInformations.hasNextLine() ) break;

				lineTextQuestion = scTextQuestion.nextLine();
				String text      = lineTextQuestion.substring(0, lineTextQuestion.indexOf("\\par"));

				lineInformations = scInformations.nextLine();
				double nbPoint   = Double.parseDouble( lineInformations.substring(lineInformations.indexOf("} ") + 2) );

				lineInformations = scInformations.nextLine();
				String type      = lineInformations.substring(lineInformations.indexOf("} ") + 2);

				lineInformations = scInformations.nextLine();
				String sNiveau   = lineInformations.substring(lineInformations.indexOf("} ") + 2);

				lineInformations = scInformations.nextLine();
				int temps        = Integer.parseInt( lineInformations.substring( lineInformations.indexOf("} ") + 2) );

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

				scInformations.nextLine();

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
						                                  Integer.parseInt(lineTextQuestion.substring(lineTextQuestion.indexOf("{") + 1, lineInformations.indexOf(":}") - 1)),
						                                  false
						                                 );
						lstReponse.add( reponseA );

						reponseB = new ReponseAssociation(
						                                  textReponseB,
						                                  reponseA,
						                                  Integer.parseInt(lineTextQuestion.substring(lineTextQuestion.indexOf("{") + 1, lineInformations.indexOf(":}") - 1)),
						                                  false
						                                 );
						reponseA.setReponseAssocie( reponseB );
						lstReponse.add( reponseB );

						lineTextQuestion = scTextQuestion.nextLine();
					}

					Association question = new Association(this, text, temps, nbPoint, niveau, lstReponse, "");
					questions.add(question);
				}
				else if ( type.equals("Elimination"))
				{
					List<ReponseElimination> lstReponse = new ArrayList<>();

					int nbIndice = 0;

					while ( !lineTextQuestion.contains("\\par{Fin}") )
					{
						// System.out.println(lineTextQuestion);
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
					questions.add(question);
				}
				else if (type.equals("QCM"))
				{
					List<ReponseQCM> lstReponse = new ArrayList<>();
					while (!lineTextQuestion.contains("\\par{Fin}"))
					{
						lstReponse.add(new ReponseQCM(
						                              lineTextQuestion.substring(lineTextQuestion.indexOf("} ") + 2, lineTextQuestion.indexOf("|")),
						                              lineTextQuestion.substring(lineTextQuestion.indexOf("|") + 1, lineTextQuestion.indexOf("||"))
						                             ));
						lineTextQuestion = scTextQuestion.nextLine();
					}

					QCM question = new QCM(this, text, temps, nbPoint, niveau, lstReponse, "");
					questions.add(question);
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

		return questions;
	}

	public String         getNom      ()
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

		for (Question q : this.questions)
		{
			questionsN.add( q );                                            // Intégrité des données pas folle
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

	public boolean ajouterQuestion (Question question)
	{
		if (question == null)
			return false;

		if (questions.contains(question))
			return false;

		questions.add(question);
		return true;
	}

	public boolean supprimerQuestion (Question question)
	{
		if (question == null)
			return false;

		if (!questions.contains(question))
			return false;

		questions.remove(question);
		return true;
	}

	public Question rechercherQuestion (String text)
	{
		Question questionTrouvee = null;

		for (Question question : questions)
		{
			if (question.getText().equals(text))
				questionTrouvee = question;
		}

		return questionTrouvee;
	}
}
