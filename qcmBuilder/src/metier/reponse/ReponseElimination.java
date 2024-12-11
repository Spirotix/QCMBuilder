package src.metier.reponse;

public class ReponseElimination extends Reponse
{
	int ordreIndice;
	boolean estVisible;
	boolean estVrai;
	public ReponseElimination(String textReponse, int ordreIndice, String estVrai)
	{
		super(textReponse);
		this.ordreIndice = ordreIndice;

		this.estVisible = true;

		if(estVrai.equals("Vrai")){ this.estVrai=true; }
		else{ this.estVrai = false;}

	}

	public int  getOrdreIndice()                   { return ordreIndice;             }
	public void setOrdreIndice(int ordreIndice)    { this.ordreIndice = ordreIndice; }

	public boolean estVisible()                      { return estVisible;              }
	public void    setEstVisible(boolean estVisible) { this.estVisible = estVisible;   }
}
