package metier;

import java.util.ArrayList;
import java.util.List;

public class Elimination extends Question
{
	private int nbIndiceUtilise;
	List<Reponse> lstReponses;

	public Elimination(Notion notion, String text, int timer, double nbPoint, int difficulte, List<Reponse> lstReponses )
	{
		super(notion, text, timer, nbPoint, difficulte);

		this.lstReponses = new ArrayList<>();
		this.lstReponses = lstReponses;

		this.nbIndiceUtilise = 0;
	}

	public int  getNbIndiceUtilise() { return this.nbIndiceUtilise; }

	public void setNbIndiceUtilise(int nbIndiceUtilise ) { this.nbIndiceUtilise = nbIndiceUtilise; }
}
