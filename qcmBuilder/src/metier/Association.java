package metier;

public class Association extends Question
{

	Reponse[][] tabCouple;
	public Association(Notion notion, String text, int timer, double nbPoint, int difficulte, Reponse[][] tabCouple)
	{
		super(notion, text, timer, nbPoint, difficulte);
		this.tabCouple = new Reponse[tabCouple.length][2];
		this.tabCouple = tabCouple;
	}
}
