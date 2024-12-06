package metier;

import java.util.ArrayList;
import java.util.List;

public class Ressource
{
	private String nom;
	private List<Notion> notions;

	public Ressource(String nom)
	{
		this.nom = nom;
		this.notions = new ArrayList<>();
	}

	public String getNom() { return nom; }
	public List<Notion> getNotions() { return notions; }

	public boolean setNom(String nom) 
	{ 
		this.nom = nom; 
		return true;
	}

	public boolean ajouterNotion(Notion notion)
	{
		if (notion == null)
			return false;
		if (notions.contains(notion))
			return false;
		notions.add(notion);
		return true;
	}

	public boolean supprimerNotion(Notion notion)
	{
		if (notion == null)
			return false;
		if (!notions.contains(notion))
			return false;
		notions.remove(notion);
		return true;
	}

	
}