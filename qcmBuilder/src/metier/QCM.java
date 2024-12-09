package metier;

import java.util.List;


public class QCM extends Question
{
	boolean estQCU;

	public QCM(Notion notion, String text, int timer, double nbPoint, int difficulte, List<Reponse> lstReponse)
	{
		super(notion, text, timer, nbPoint, difficulte, lstReponse);
		this.estQCU = this.estUnique();
	}

	public boolean estUnique()
	{
		int nbReponseVrai = 0;

		for( Reponse reponse : lstReponses)
		{
			if ( reponse.estVrai()){nbReponseVrai ++;}
		}

		if ( nbReponseVrai > 1){return false;}
		return true;
	}
}