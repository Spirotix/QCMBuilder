package src.metier;

import java.util.ArrayList;
import java.util.List;


public class QCM extends Question
{
	private boolean       estQCU;
	private List<Reponse> lstReponses;

	public QCM(Notion notion, String text, int timer, double nbPoint, int difficulte, List<Reponse> lstReponses, String explication)
	{
		super(notion, text, timer, nbPoint, difficulte, explication);

		this.lstReponses = lstReponses;
		this.estQCU      = this.estUnique();
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

	public void setLstReponses (List<Reponse> lstReponses) { this.lstReponses = lstReponses; }

	public List<Reponse> getlstReponses(){ return lstReponses; }
}
