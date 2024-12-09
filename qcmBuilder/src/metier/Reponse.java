package metier;

public class Reponse 
{
	private String  text;
	private boolean estVrai;
	private double     pointPerdu;

	public Reponse(String estVrai, String textReponse, double nbPointPerdu)
	{
		this.text = textReponse;
		if(estVrai.equals("Vrai")){this.estVrai=true;}
		else{ this.estVrai = false;}
		this.pointPerdu = nbPointPerdu;
	}

	public String  getText()                        { return text;                  }
	public void    setText(String text)             { this.text = text;             }

	public boolean estVrai()                        { return estVrai;               }
	public void    setEstVrai(boolean estVrai)      { this.estVrai = estVrai;       }
	
	public double  getPointPerdu()                  { return pointPerdu;            }
	public void    setPointPerdu(double pointPerdu) { this.pointPerdu = pointPerdu; }
}


