package metier;

import java.util.List;


public class QCM extends Question
{
	public QCM(Notion notion, String text, int timer, double nbPoint, int difficulte, List<Reponse> lstReponse)
	{
		super(notion, text, timer, nbPoint, difficulte, lstReponse);
	}
}