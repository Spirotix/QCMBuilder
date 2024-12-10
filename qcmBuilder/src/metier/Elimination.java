package src.metier;
import java.util.List;

public class Elimination extends Question
{
	private int           nbIndiceUtilise;
	private double[]      nbPointPerdu;
	private List<Reponse> lstReponses;

	public Elimination(Notion notion, String text, int timer, double nbPoint, int difficulte, List<Reponse> lstReponses, String explication)
	{
		super(notion, text, timer, nbPoint, difficulte, explication);

		this.lstReponses = lstReponses;

		this.nbIndiceUtilise = 0;
	}

	public int    getNbIndiceUtilise() { return this.nbIndiceUtilise; }

	

	public void setNbIndiceUtilise(int nbIndiceUtilise ) { this.nbIndiceUtilise = nbIndiceUtilise; }

}
