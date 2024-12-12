package src.metier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import src.metier.question.Association;
import src.metier.question.QCM;
import src.metier.question.Question;
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
			Scanner scTextQuestion = new Scanner(new File("./data/questions/" + this.ressource.getNom() + "_" + this.nom + ".rtf"));
			Scanner scInformations = new Scanner(new File("./data/questions/" + this.ressource.getNom() + "_" + this.nom + ".csv"));

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

				scTextQuestion.nextLine();
				scInformations.nextLine();
				lineInformations = scInformations.nextLine();

				if ( type.equals("Association") )
				{
					List<ReponseAssociation> lstReponse = new ArrayList<>();

					while (!lineInformations.contains("{Fin}"))
					{
						List<Integer> lstInd = new ArrayList<>();

						String[] tmp = lineInformations.substring(lineInformations.indexOf("->") + 2, lineInformations.indexOf("::") - 1).split("_");
						for (String s : tmp)
							lstInd.add( Integer.parseInt(s) );

						String textReponseDroite = "";

						if ( lineInformations.contains("->") )
							textReponseDroite = lineInformations.substring(lineInformations.indexOf("} ") + 2, lineInformations.indexOf("->"));
						else
							textReponseDroite = lineInformations.substring(lineInformations.indexOf("} ") + 2, lineInformations.indexOf("::"));

						String textReponseGauche = "";

						textReponseGauche = lineInformations.substring(lineInformations.indexOf("} ") + 2, lineInformations.indexOf("::"));
	
						if ( !textReponseDroite.equals("[null]") )
							lstReponse.add(new ReponseAssociation(
							                                      textReponseDroite,
							                                      lstInd,
							                                      Integer.parseInt(lineInformations.substring(lineInformations.indexOf("\b ") + 3, lineInformations.indexOf(":}") - 1)),
							                                      true
							                                     ));
						if ( !textReponseGauche.equals("[null]") )
							lstReponse.add(new ReponseAssociation(
							                                      textReponseGauche,
							                                      null,
							                                      Integer.parseInt(lineInformations.substring(lineInformations.indexOf("\b ") + 3, lineInformations.indexOf(":}") - 1)),
							                                      false
							                                     ));
  
						lineInformations = scInformations.nextLine();
					}

					Question question = new Association(this, text, temps, nbPoint, niveau, lstReponse, "");
					questions.add(question);
				}
				else if ( type.equals("Elimination"))
				{
					List<ReponseElimination> lstReponse = new ArrayList<>();

					boolean bool;

					while ( !lineInformations.contains("{Fin}") )
					{
						lstReponse.add(new ReponseElimination(
						                                      lineInformations.substring(lineInformations.indexOf("} ") + 2, lineInformations.indexOf("|")),
						                                      lineInformations.substring(lineInformations.indexOf("|") + 1, lineInformations.indexOf("||")),
						                                      Integer.parseInt  (lineInformations.substring(lineInformations.indexOf("||") + 2, lineInformations.indexOf("/"))),
						                                      Double.parseDouble(lineInformations.substring(lineInformations.indexOf("/") + 1) )
						                                     ));
						lineInformations = scInformations.nextLine();
					}

					//Question question = new Elimination(this, text, temps, nbPoint, niveau, lstReponse, "");
					//questions.add(question);
				}
				else if (type.equals("QCM"))
				{
					List<ReponseQCM> lstReponse = new ArrayList<>();
					while (!lineInformations.contains("{Fin}"))
					{
						lstReponse.add(new ReponseQCM(
						                              lineInformations.substring(lineInformations.indexOf("} ") + 2, lineInformations.indexOf("|")),
						                              lineInformations.substring(lineInformations.indexOf("|") + 1, lineInformations.indexOf("||"))
						                             ));
						lineInformations = scInformations.nextLine();
					}

					Question question = new QCM(this, text, temps, nbPoint, niveau, lstReponse, "");
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

	public String         getNom      () { return nom;       }
	public Ressource      getRessource() { return ressource; }
	public List<Question> getQuestions() { return questions; }

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
