package src.metier.question;

import java.util.List;
import src.metier.Notion;
import src.metier.reponse.*;

public class Association implements Question
{
	private Notion notions;
	private String text;
	private int timer;
	private double nbPoint;
	private int difficulte;
	private String explication;
	private List<ReponseAssociation> lstReponseAsso;

	public Association(Notion notion, String text, int timer, double nbPoint, int difficulte,
			List<ReponseAssociation> lstReponseAsso, String explication)
	{
		this.notions = notion;
		this.text = text;
		this.timer = timer;
		this.nbPoint = nbPoint;
		this.difficulte = difficulte;
		this.lstReponseAsso = lstReponseAsso;
		this.explication = explication;
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

	public List<ReponseAssociation> getReponses()
	{
		return this.lstReponseAsso;
	}

	public void setReponses(List<ReponseAssociation> lstReponseAsso)
	{
		this.lstReponseAsso = lstReponseAsso;
	}
}
