package src.metier.question;

import java.util.ArrayList;
import java.util.List;

import src.metier.Couple;
import src.metier.Notion;

public class Association extends Question
{

	private List<Couple> lstCouple;

	public Association(Notion notion, String text, int timer, double nbPoint, int difficulte, List<Couple> lstCouple, String explication)
	{
		super(notion, text, timer, nbPoint, difficulte, explication);

		this.lstCouple = lstCouple;
	}
}
