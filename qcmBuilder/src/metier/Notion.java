package metier;

public class Notion 
{
	private String nom;
	private Ressource ressource;

	public Notion(String nom, Ressource ressource) 
	{
		this.nom = nom;
		this.ressource = ressource;
	}

	public String getNom   () { return nom;    }
	public Ressource getRessource() { return ressource; }

	public void setNom   (String nom)    { this.nom    = nom;   }
	public void setRessource(Ressource ressource) { this.ressource = ressource;}
}
