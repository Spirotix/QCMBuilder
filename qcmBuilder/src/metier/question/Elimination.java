package src.metier.question;
import java.util.List;

import src.metier.Notion;
import src.metier.reponse.*;

public class Elimination extends Question
{
	private int           nbIndice;
	private int           nbIndiceUtilise;
	private double[]      nbPointPerdu;
	private List<ReponseElimination> lstReponses;

	public Elimination(Notion notion, String text, int timer, double nbPoint, int difficulte, List<ReponseElimination> lstReponses, List<Double> nbPointPerdu, int nbIndice, String explication)
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
		for ( ReponseElimination reponse : lstReponses)
		{
			if ( reponse.getOrdreIndice() == nbIndiceUtilise ) { reponse.setEstVisible(false); }
		}
	}



}
