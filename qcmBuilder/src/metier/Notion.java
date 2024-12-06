package metier;

public class Notion 
{
	private String nom;
	private Notion notion;

	public Notion(String nom, Notion notion) 
	{
		this.nom = nom;
		this.notion = notion;
	}

	public String getNom   () { return nom;    }
	public Notion getNotion() { return notion; }

	public void setNom   (String nom)    { this.nom    = nom;   }
	public void setNotion(Notion notion) { this.notion = notion;}
}
