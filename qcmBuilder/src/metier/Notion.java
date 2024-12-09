package metier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Notion 
{
	private String    nom;
	private Ressource ressource;
	List<Question>    questions;

	public Notion(String nom, Ressource ressource) 
	{
		this.nom = nom;
		this.ressource = ressource;
		this.questions = lireQuestions();
	}

	private List<Question> lireQuestions() 
	{
		List<Question> questions = new ArrayList<>();
		try 
		{
			Scanner scanner = new Scanner(new File("../data/questions/"));
			if( scanner.hasNextLine()){	scanner.nextLine();	}
			while (scanner.hasNextLine()) 
			{
				String line = scanner.nextLine();
				String[] parts = line.split(";");

				if (parts[0].equals(nom));
				{
					String type            = parts[1];
					int    id              = Integer.parseInt(parts[2]);
					String text            = parts[3];
					int    timer           = Integer.parseInt(parts[4]);
					int    nbPoint         = Integer.parseInt(parts[5]);
					int    nbIndiceUtilisé = Integer.parseInt(parts[6]);
					int    difficulte      = Integer.parseInt(parts[7]);


					switch (type) {
						
						case "QCM" -> {
							Question question = new QCM(this, id, text, timer, nbPoint, nbIndiceUtilisé, difficulte);
							questions.add(question);
						}

						case "Association" -> {
							Question question = new Association(this, id, text, timer, nbPoint, nbIndiceUtilisé, difficulte);
							questions.add(question);
						}

						case "Elimination" -> {
							Question question = new Elimination(this, id, text, timer, nbPoint, nbIndiceUtilisé, difficulte);
							questions.add(question);
						}

						default -> {
							System.out.println("Type de question inconnu");
						}
					}
				}
			}
			scanner.close();
		} 
		catch (FileNotFoundException e) { e.printStackTrace(); }

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
	
}
