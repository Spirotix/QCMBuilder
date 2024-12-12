package src.metier.reponse;

public class Reponse 
{
	private String  text;

	public Reponse(String textReponse)
	{
		this.text = textReponse;

	}

	public String  getText()                         { return text;                    }
	public void    setText(String text)              { this.text = text;               }

}
