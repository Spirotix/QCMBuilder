package metier;

import java.util.ArrayList;
import java.util.List;

public class Association extends Question
{

	List<Couple> lstCouple;
	public Association(Notion notion, String text, int timer, double nbPoint, int difficulte, List<Couple> lstCouple)
	{
		super(notion, text, timer, nbPoint, difficulte);
		this.lstCouple = new ArrayList<>();
		this.lstCouple = lstCouple;
	}
}
