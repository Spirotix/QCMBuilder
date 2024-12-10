package src.metier;

public class Reponse 
{
	private String  text;
	private boolean estVrai;

	public Reponse(String estVrai, String textReponse)
	{
		this.text = textReponse;
		if(estVrai.equals("Vrai")){ this.estVrai=true; }
		else{ this.estVrai = false;}
	}

	public String  getText()                        { return text;                  }
	public void    setText(String text)             { this.text = text;             }

	public boolean estVrai()                        { return estVrai;               }
	public void    setEstVrai(boolean estVrai)      { this.estVrai = estVrai;       }
}
