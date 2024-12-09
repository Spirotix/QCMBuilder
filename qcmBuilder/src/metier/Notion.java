package src.metier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Notion
{
	private String         nom      ;
	private Ressource      ressource;
	private List<Question> questions;
	private List<Couple>   couples  ;

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
			Scanner scanner = new Scanner(new File("../data/questions/" + this.ressource + "_" + this.nom));
			while (scanner.hasNextLine()) 
			{
				String line = scanner.nextLine();
				while ( !line.contains("Question"))
				{
					line = scanner.nextLine();
				}
				
				String text = line.substring(0, line.indexOf("\\par"));

				line           = scanner.nextLine();
				double nbPoint = Double.parseDouble(line.substring(line.indexOf("} ") + 1, line.indexOf("\\par") - 1));

				line = scanner.nextLine();
				String type = line.substring(line.indexOf("} ") + 1, line.indexOf("\\par") - 1);
				
				scanner.nextLine();
				line       = scanner.nextLine();

				List<Reponse> lstReponse;
				lstReponse = new ArrayList<>();

				List<Couple> lstCouple;
				lstCouple  = new ArrayList<>();

				if ( type.equals("Association"))
				{
					
					while (!line.contains("{Niveau}"))
					{
						Reponse premier;
						Reponse second ;

						if (line.substring(line.indexOf("} ") + 1, line.indexOf(":") - 1).equals("[null]"))
						{
							premier = null;
						}
						else
						{
							premier = new Reponse("Vrai",
						                      line.substring(line.indexOf("} ") + 1, line.indexOf(":")),
						                      0);
						}

						if ( line.substring(line.indexOf(":") + 1, line.indexOf("\\par") - 1).equals("[null]"))
						{
							second = null;
						}
						else
						{
							second = new Reponse("Vrai",
							line.substring(line.indexOf(":") + 1, line.indexOf("\\par") - 1), 0);
						}

						lstCouple.add(new Couple(premier, second));
						line = scanner.nextLine();
					}
				}
				else
				{

					while (!line.contains("{Niveau}"))
					{
						lstReponse.add(new Reponse(line.substring(line.indexOf("} ") + 1, line.indexOf(".")),
								line.substring(line.indexOf(".") + 1, line.indexOf("|")),
								Double.parseDouble(line.substring(line.indexOf("|") + 1, line.indexOf("\\par") - 1))));
						line = scanner.nextLine();
					}
				}

				String sNiveau = line.substring( line.indexOf("} ") + 1, line.indexOf("\\par") - 1);

				line=scanner.nextLine();
				int    temps   = Integer.parseInt(line.substring( line.indexOf("} ") + 1, line.indexOf("\\par") - 1));

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

				switch (type)
				{
					case "QCM" ->
					{
						Question question = new QCM(this, text, temps, nbPoint, niveau, lstReponse);
						questions.add(question);
					}

					case "Association" ->
					{
						Question question = new Association(this, text, temps, nbPoint, niveau, lstCouple);
						questions.add(question);
					}

					case "Elimination" ->
					{
						Question question = new Elimination(this, text, temps, nbPoint, niveau, lstReponse);
						questions.add(question);
					}

					default ->
					{
						System.out.println("Type de question inconnu");
					}
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
