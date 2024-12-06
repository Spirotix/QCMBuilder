package metier;

import java.util.List;
import java.util.ArrayList;

public class Notion 
{
	private String    nom;
	private Ressource ressource;
	List<Question>    questions;

	public Notion(String nom, Ressource ressource) 
	{
		this.nom = nom;
		this.ressource = ressource;
		this.questions = new ArrayList<>();
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
