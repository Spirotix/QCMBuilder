package metier;

import java.util.List;

public class Ressource
{
	private String nom;
	private List<Notion> notions;

	public Ressource(String nom, List<Notion> notions)
	{
		this.nom = nom;
		this.notions = notions;
	}

	public String getNom() { return nom; }
	public List<Notion> getNotions() { return notions; }

	public void setNom(String nom) { this.nom = nom; }
	public void setNotions(List<Notion> notions) { this.notions = notions; }
}