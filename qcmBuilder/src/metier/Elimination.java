package metier;

import java.util.List;

public class Elimination extends Question
{

	public Elimination(Notion notion, String text, int timer, double nbPoint, int difficulte, List<Reponse> lstReponse)
	{
		super(notion, text, timer, nbPoint, difficulte, lstReponse);
	}
}
