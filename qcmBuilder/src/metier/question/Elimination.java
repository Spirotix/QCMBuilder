package src.metier.question;

import java.util.List;
import src.metier.Notion;
import src.metier.reponse.ReponseElimination;
import src.metier.reponse.ReponseQCM;

public class Elimination implements Question
{
	private Notion notions;
	private String text;
	private int timer;
	private double nbPoint;
	private int difficulte;
	private String explication;
	private int nbIndice;
	private int nbIndiceUtilise;
	private List<ReponseElimination> lstReponses;

	public Elimination(Notion notion, String text, int timer, double nbPoint, int difficulte, List<ReponseElimination> lstReponses, int nbIndice, String explication)
	{
		this.notions = notion;
		this.text = text;
		this.timer = timer;
		this.nbPoint = nbPoint;
		this.difficulte = difficulte;
		this.lstReponses = lstReponses;
		this.nbIndice = nbIndice;
		this.explication = explication;
		this.nbIndiceUtilise = 0;
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

	public List<ReponseElimination> getLstReponses()
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

	public int getNbIndiceUtilise()
	{
		return this.nbIndiceUtilise;
	}

	public void setNbIndiceUtilise(int nbIndiceUtilise)
	{
		this.nbIndiceUtilise = nbIndiceUtilise;
	}

	public void utiliseIndice()
	{
		if (nbIndiceUtilise >= nbIndice)
		{
			return;
		}

		nbIndiceUtilise++;
		for (ReponseElimination reponse : lstReponses)
		{
			if (reponse.getOrdreIndice() == nbIndiceUtilise)
			{
				reponse.setEstVisible(false);
			}
		}
	}

	public List<ReponseElimination> getReponses()
	{
		return this.lstReponses;
	}
}
