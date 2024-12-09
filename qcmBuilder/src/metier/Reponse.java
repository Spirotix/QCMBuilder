package metier;

public class Reponse 
{
	private String  text;
	private boolean estVrai;
	private int     pointPerdu;

	public String  getText()                     { return text;                  }
	public void    setText(String text)          { this.text = text;             }

	public boolean isEstVrai()                   { return estVrai;               }
	public void    setEstVrai(boolean estVrai)   { this.estVrai = estVrai;       }
	
	public int     getPointPerdu()               { return pointPerdu;            }
	public void    setPointPerdu(int pointPerdu) { this.pointPerdu = pointPerdu; }
}


