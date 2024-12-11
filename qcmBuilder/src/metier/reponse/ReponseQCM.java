package src.metier.reponse;

public class ReponseQCM extends Reponse
{
	private boolean estVrai;
	public ReponseQCM(String estVrai, String textReponse)
	{
		super(textReponse);
		if(estVrai.equals("Vrai")){ this.estVrai=true; }
		else{ this.estVrai = false;}
	}

	public boolean estVrai()                      { return estVrai; }
	public void    setEstVrai(boolean estVrai)     { this.estVrai = estVrai; }
	
}
