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
			Scanner scanner = new Scanner(new File("./data/questions/" + this.ressource.getNom() + "_" + this.nom + ".rtf"));
			while (scanner.hasNextLine())
			{
				String line = scanner.nextLine();
				while ( scanner.hasNextLine() && ! line.contains("Question") )
				{
					line = scanner.nextLine();
				}

				if ( !scanner.hasNextLine()) break;

				line = scanner.nextLine();
				String text = line.substring(0, line.indexOf("\\par"));

				line           = scanner.nextLine();
				double nbPoint = Double.parseDouble(line.substring(line.indexOf("} ") + 2, line.indexOf("\\par") - 1));

				line = scanner.nextLine();
				String type = line.substring(line.indexOf("} ") + 2, line.indexOf("\\par") - 1);

				line = scanner.nextLine();
				String sNiveau = line.substring( line.indexOf("} ") + 2, line.indexOf("\\par") - 1);

				line = scanner.nextLine();
				int    temps   = Integer.parseInt(line.substring( line.indexOf("} ") + 2, line.indexOf("\\par") - 1));

				int niveau;
				switch(sNiveau)
				{
					case "TF" -> { niveau = 1; }
					case "F"  -> { niveau = 2; }
					case "M"  -> { niveau = 3; }
					case "D"  -> { niveau = 4; }
					default   ->
					{
						scanner.close();
						throw new IllegalArgumentException("Le niveau doit être appartenir aux options suivantes : 'TF','F','M','D'");
					}
				}

				scanner.nextLine();
				line = scanner.nextLine();

				if ( type.equals("Association") )
				{
					List<ReponseAssociation> lstReponse = new ArrayList<>();

					while (!line.contains("{\\b Fin}"))
					{
						List<Integer> lstInd = new ArrayList<>();

						String[] tmp = line.substring(line.indexOf("->") + 2, line.indexOf("::") - 1).split("_");
						for (String s : tmp)
							lstInd.add( Integer.parseInt(s) );

						String textReponseDroite = "";

						if ( line.contains("->") )
							textReponseDroite = line.substring(line.indexOf("} ") + 2, line.indexOf("->"));
						else
							textReponseDroite = line.substring(line.indexOf("} ") + 2, line.indexOf("::"));

						String textReponseGauche = "";

						textReponseGauche = line.substring(line.indexOf("} ") + 2, line.indexOf("::"));
	
						if ( !textReponseDroite.equals("[null]") )
							lstReponse.add(new ReponseAssociation(
							                                      textReponseDroite,
							                                      lstInd,
							                                      Integer.parseInt(line.substring(line.indexOf("\b ") + 3, line.indexOf(":}") - 1)),
							                                      true
							                                     ));
						if ( !textReponseGauche.equals("[null]") )
							lstReponse.add(new ReponseAssociation(
							                                      textReponseGauche,
							                                      null,
							                                      Integer.parseInt(line.substring(line.indexOf("\b ") + 3, line.indexOf(":}") - 1)),
							                                      false
							                                      ));
  
						line = scanner.nextLine();
					}

					Question question = new Association(this, text, temps, nbPoint, niveau, lstReponse, "");
					questions.add(question);
				}
				else if ( type.equals("Elimination"))
				{
					List<ReponseElimination> lstReponse = new ArrayList<>();
					while ( !line.contains("{\\b Fin}") )
					{
						lstReponse.add(new ReponseElimination(
						                                      line.substring(line.indexOf("} ") + 2, line.indexOf("|")),
						                                      line.substring(line.indexOf("|") + 1, line.indexOf("||")),
						                                      Integer.parseInt(line.substring(line.indexOf("||") + 2, line.indexOf("/")))
						                                     ));
						line = scanner.nextLine();
					}

					//Question question = new Elimination(this, text, temps, nbPoint, niveau, lstReponse, "");
					//questions.add(question);
				}
				else if (type.equals("QCM"))
				{
					List<ReponseQCM> lstReponse = new ArrayList<>();
					while (!line.contains("{\\b Fin}"))
					{
						lstReponse.add(new ReponseQCM(
						                              line.substring(line.indexOf("} ") + 2, line.indexOf("|")),
						                              line.substring(line.indexOf("|") + 1, line.indexOf("||"))
						                             ));
						line = scanner.nextLine();
					}

					Question question = new QCM(this, text, temps, nbPoint, niveau, lstReponse, "");
					questions.add(question);
				}
				else
				{
					scanner.close();
					throw new IllegalArgumentException("Le type doit être appartenir aux options suivantes : 'Association','Elimination','QCM'"); 
				}
			}
			scanner.close();
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
