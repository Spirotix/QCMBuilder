package src.metier.reponse;

public class ReponseElimination extends Reponse
{
	private int ordreIndice;
	private boolean estVisible;
	private boolean estVrai;
	public ReponseElimination(String estVrai, String textReponse, int ordreIndice )
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
