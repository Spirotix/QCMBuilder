package src.metier;

public class Reponse 
{
	private String  text;
	private boolean estVrai;
	private boolean estVisible;
	private int     ordreIndice;

	public Reponse(String estVrai, String textReponse, int ordreIndice)
	{
		this.text = textReponse;
		if(estVrai.equals("Vrai")){ this.estVrai=true; }
		else{ this.estVrai = false;}
		this.estVisible = true;
		
		this.ordreIndice = ordreIndice;

		this.text = textReponse;
		if(estVrai.equals("Vrai")){ this.estVrai=true; }
		else{ this.estVrai = false;}
		this.estVisible = true;
	}

	public String  getText()                         { return text;                    }
	public void    setText(String text)              { this.text = text;               }

	public boolean estVrai()                         { return estVrai;                 }
	public void    setEstVrai(boolean estVrai)       { this.estVrai = estVrai;         }

	public boolean estVisible()                      { return estVisible;              }
	public void    setEstVisible(boolean estVisible) { this.estVisible = estVisible;   }

	public int    getOrdreIndice()                   { return ordreIndice;             }
	public void   setOrdreIndice(int ordreIndice)    { this.ordreIndice = ordreIndice; }
}
