package src.metier.question;

import java.util.List;

import src.metier.Notion;
import src.metier.reponse.*;


public class QCM extends Question
{
	private boolean       estQCU;
	private List<ReponseQCM> lstReponses;

	public QCM(Notion notion, String text, int timer, double nbPoint, int difficulte, List<ReponseQCM> lstReponses, String explication)
	{
		super(notion, text, timer, nbPoint, difficulte, explication);

		this.lstReponses = lstReponses;
		this.estQCU      = this.estUnique();

		int nbReponseVrai = 0;

		for (ReponseQCM reponse : lstReponses)
		{
			if (reponse.estVrai())
			{
				nbReponseVrai++;
			}
		}

		if (nbReponseVrai > 1)
			this.estQCU = false;
		else
			this.estQCU = true;
	}

	public boolean estUnique() { return estQCU;	}

	public void setLstReponses (List<ReponseQCM> lstReponses) { this.lstReponses = lstReponses; }

	public List<ReponseQCM> getlstReponses(){ return lstReponses; }
}
