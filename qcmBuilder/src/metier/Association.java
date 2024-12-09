package metier;

public class Association extends Question
{

	public Association(Notion notion, int id, String text, int timer, int nbPoint, int nbIndiceUtilisé, int difficulte)
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
