package src.metier;
import java.util.List;

public class Elimination extends Question
{
	private int           nbIndice;
	private int           nbIndiceUtilise;
	private double[]      nbPointPerdu;
	private List<Reponse> lstReponses;

	public Elimination(Notion notion, String text, int timer, double nbPoint, int difficulte, List<Reponse> lstReponses, List<Double> nbPointPerdu, int nbIndice, String explication)
	{
		super(notion, text, timer, nbPoint, difficulte, explication);

		this.nbIndice = nbIndice;
		this.nbPointPerdu = new double[nbIndice];

		for (int i = 0; i < nbIndice; i++)
		{
			this.nbPointPerdu[i] = nbPointPerdu.get(i);
		}

		this.lstReponses = lstReponses;

		this.nbIndiceUtilise = 0;
	}

	public int    getNbIndiceUtilise() { return this.nbIndiceUtilise; }

	public void setNbIndiceUtilise(int nbIndiceUtilise ) { this.nbIndiceUtilise = nbIndiceUtilise; }

	public void utiliseIndice() 
	{ 
		if ( nbIndiceUtilise >= nbIndice ){ return; }

		nbIndiceUtilise++;
		for ( Reponse reponse : lstReponses)
		{
			if ( reponse.getOrdreIndice() == nbIndiceUtilise ) { reponse.setEstVisible(false); }
		}
	}

	

}
