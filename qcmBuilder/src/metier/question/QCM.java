package src.metier.question;

import java.util.List;
import src.metier.Notion;
import src.metier.reponse.ReponseQCM;

public class QCM implements Question
{
	private Notion notions;
	private String text;
	private int timer;
	private double nbPoint;
	private int difficulte;
	private String explication;
	private boolean estQCU;
	private List<ReponseQCM> lstReponses;

	public QCM(Notion notion, String text, int timer, double nbPoint, int difficulte, List<ReponseQCM> lstReponses,
			String explication)
	{
		this.notions = notion;
		this.text = text;
		this.timer = timer;
		this.nbPoint = nbPoint;
		this.difficulte = difficulte;
		this.lstReponses = lstReponses;
		this.explication = explication;
		this.estQCU = this.estUnique();

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

	@Override
	public String getText()
	{
		return this.text;
	}

	@Override
	public int getTimer()
	{
		return this.timer;
	}

	@Override
	public double getNbPoint()
	{
		return this.nbPoint;
	}

	@Override
	public int getDifficulte()
	{
		return this.difficulte;
	}

	@Override
	public Notion getNotions()
	{
		return this.notions;
	}

	@Override
	public String getExplication()
	{
		return this.explication;
	}

	public List<ReponseQCM> getLstReponses()
	{
		return this.lstReponses;
	}

	@Override
	public void setText(String text)
	{
		this.text = text;
	}

	@Override
	public void setTimer(int timer)
	{
		this.timer = timer;
	}

	@Override
	public void setNbPoint(double nbPoint)
	{
		this.nbPoint = nbPoint;
	}

	@Override
	public void setDifficulte(int difficulte)
	{
		this.difficulte = difficulte;
	}

	@Override
	public void setNotions(Notion notions)
	{
		this.notions = notions;
	}

	@Override
	public void setExplication(String explication)
	{
		this.explication = explication;
	}

	public boolean estUnique()
	{
		return estQCU;
	}

	public void setLstReponses(List<ReponseQCM> lstReponses)
	{
		this.lstReponses = lstReponses;
	}

	public List<ReponseQCM> getlstReponses()
	{
		return lstReponses;
	}
}
