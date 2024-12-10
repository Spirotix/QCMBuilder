package src.metier;

import java.util.ArrayList;
import java.util.List;

public class Elimination extends Question
{
	private int           nbIndiceUtilise;
	private double        nbPointPerdu;
	private List<Reponse> lstReponses;

	public Elimination(Notion notion, String text, int timer, double nbPoint, int difficulte, List<Reponse> lstReponses,
			String explication)
	{
		super(notion, text, timer, nbPoint, difficulte, explication);

		this.lstReponses = new ArrayList<>();
		this.lstReponses = lstReponses;

		this.nbIndiceUtilise = 0;
		this.nbPointPerdu    = 0.0;
	}

	public int    getNbIndiceUtilise() { return this.nbIndiceUtilise; }
	public double getNbPointPerdu   () { return this.nbPointPerdu   ; }

	public void setNbIndiceUtilise(int nbIndiceUtilise ) { this.nbIndiceUtilise = nbIndiceUtilise; }
	public void setNbPointPerdu   (int nbPointPerdu    ) { this.nbPointPerdu    = nbPointPerdu   ; }
}
