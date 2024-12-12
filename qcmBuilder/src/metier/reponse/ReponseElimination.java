package src.metier.reponse;

public class ReponseElimination extends Reponse
{
	private int     ordreIndice;
	private boolean estVisible;
	private boolean estVrai;
	private double  nbPointPerdu;

	public ReponseElimination(String estVrai, String textReponse, int ordreIndice, double nbPointPerdu )
	{
		super(textReponse);

		this.ordreIndice  = ordreIndice;
		this.estVisible   = true;
		this.estVrai      = estVrai.equals("Vrai");
		this.nbPointPerdu = nbPointPerdu;
	}

	public int  getOrdreIndice()                   { return ordreIndice;             }
	public void setOrdreIndice(int ordreIndice)    { this.ordreIndice = ordreIndice; }

	public boolean estVisible()                      { return estVisible;              }
	public void    setEstVisible(boolean estVisible) { this.estVisible = estVisible;   }
}
