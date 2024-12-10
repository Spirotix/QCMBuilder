package src.metier;

public class Reponse 
{
	private String  text;
	private boolean estVrai;
	private boolean estVisible;

	public Reponse(String estVrai, String textReponse)
	{
		this.text = textReponse;
		if(estVrai.equals("Vrai")){ this.estVrai=true; }
		else{ this.estVrai = false;}
		this.estVisible = true;
	}

	public String  getText()                         { return text;                  }
	public void    setText(String text)              { this.text = text;             }

	public boolean estVrai()                         { return estVrai;               }
	public void    setEstVrai(boolean estVrai)       { this.estVrai = estVrai;       }

	public boolean estVisible()                      { return estVisible;            }
	public void    setEstVisible(boolean estVisible) { this.estVisible = estVisible; }
}
