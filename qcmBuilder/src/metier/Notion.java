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
			Scanner scanner = new Scanner(new File("METTRE NOM"));
			while (scanner.hasNextLine()) 
			{
				String line = scanner.nextLine();
				
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
