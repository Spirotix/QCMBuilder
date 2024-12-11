package src.metier.reponse;

public class ReponseElimination extends Reponse
{
	private int ordreIndice;

	public ReponseElimination(String estVrai, String textReponse, int ordreIndice)
	{
		super(estVrai, textReponse);
		this.ordreIndice = ordreIndice;
	}

	public int  getOrdreIndice()                   { return ordreIndice;             }
	public void setOrdreIndice(int ordreIndice)    { this.ordreIndice = ordreIndice; }
}
