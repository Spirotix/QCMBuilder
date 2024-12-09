package metier;

import java.util.List;

public class Elimination extends Question
{

	public Elimination(Notion notion, int id, String text, int timer, int nbPoint, int nbIndiceUtilisé, int difficulte)
	{
		super(notion, id, text, timer, nbPoint, nbIndiceUtilisé, difficulte);
	}

	@Override
	protected List<Reponse> lireReponses()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
}
