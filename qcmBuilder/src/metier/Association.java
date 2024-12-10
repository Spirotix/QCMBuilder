package src.metier;

import java.util.ArrayList;
import java.util.List;

public class Association extends Question
{

	List<Couple> lstCouple;
	public Association(Notion notion, String text, int timer, double nbPoint, int difficulte, List<Couple> lstCouple,
			String explication)
	{
		super(notion, text, timer, nbPoint, difficulte, explication);
		this.lstCouple = new ArrayList<>();
		this.lstCouple = lstCouple;
	}
}
