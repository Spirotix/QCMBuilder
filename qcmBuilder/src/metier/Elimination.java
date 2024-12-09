package metier;

import java.util.List;

public class Elimination extends Question
{
	private int nbIndiceUtilise;

	public Elimination(Notion notion, String text, int timer, double nbPoint, int difficulte, List<Reponse> lstReponse)
	{
		super(notion, text, timer, nbPoint, difficulte, lstReponse);

		this.nbIndiceUtilise = 0;
	}

	public int  getNbIndiceUtilise() { return this.nbIndiceUtilise; }

	public void setNbIndiceUtilise(int nbIndiceUtilise ) { this.nbIndiceUtilise = nbIndiceUtilise; }
}
