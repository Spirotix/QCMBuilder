package src.metier.question;

import java.util.List;
import src.metier.Notion;
import src.metier.reponse.ReponseElimination;

/**
 * La classe Elimination représente une question de type élimination.
 * Elle contient un texte de question, un timer, un nombre de points, une difficulté,
 * une explication, un nombre d'indices, un nombre d'indices utilisés et une liste de réponses.
 */
public class Elimination implements Question
{
	private Notion                   notions;
	private String                   text;
	private int                      timer;
	private double                   nbPoint;
	private int                      difficulte;
	private String                   explication;
	private int                      nbIndice;
	private int                      nbIndiceUtilise;
	private List<ReponseElimination> lstReponses;

	/**
	 * Constructeur de la classe Elimination.
	 * 
	 * @param notion Les notions associées à la question.
	 * @param text Le texte de la question.
	 * @param timer Le temps imparti pour répondre à la question.
	 * @param nbPoint Le nombre de points attribués à la question.
	 * @param difficulte La difficulté de la question.
	 * @param lstReponses La liste des réponses de type élimination.
	 * @param nbIndice Le nombre d'indices disponibles.
	 * @param explication L'explication de la question.
	 */
	public Elimination(Notion notion, String text, int timer, double nbPoint, int difficulte, 
	List<ReponseElimination> lstReponses, int nbIndice, String explication)
	{
		this.notions         = notion;
		this.text            = text;
		this.timer           = timer;
		this.nbPoint         = nbPoint;
		this.difficulte      = difficulte;
		this.lstReponses     = lstReponses;
		this.nbIndice        = nbIndice;
		this.explication     = explication;
		this.nbIndiceUtilise = 0;
	}

	/**
	 * Retourne le texte de la question.
	 * 
	 * @return Le texte de la question.
	 */
	@Override
	public String getText()
	{
		return this.text;
	}

	/**
	 * Retourne le temps imparti pour répondre à la question.
	 * 
	 * @return Le temps imparti pour répondre à la question.
	 */
	@Override
	public int getTimer()
	{
		return this.timer;
	}

	/**
	 * Retourne le nombre de points attribués à la question.
	 * 
	 * @return Le nombre de points attribués à la question.
	 */
	@Override
	public double getNbPoint()
	{
		return this.nbPoint;
	}

	/**
	 * Retourne la difficulté de la question.
	 * 
	 * @return La difficulté de la question.
	 */
	@Override
	public int getDifficulte()
	{
		return this.difficulte;
	}

	/**
	 * Retourne les notions associées à la question.
	 * 
	 * @return Les notions associées à la question.
	 */
	@Override
	public Notion getNotions()
	{
		return this.notions;
	}

	/**
	 * Retourne l'explication de la question.
	 * 
	 * @return L'explication de la question.
	 */
	@Override
	public String getExplication()
	{
		return this.explication;
	}

	/**
	 * Retourne la liste des réponses de type élimination.
	 * 
	 * @return La liste des réponses de type élimination.
	 */
	public List<ReponseElimination> getLstReponses()
	{
		return this.lstReponses;
	}

	/**
	 * Définit le texte de la question.
	 * 
	 * @param text Le texte de la question.
	 */
	@Override
	public void setText(String text)
	{
		this.text = text;
	}

	/**
	 * Définit le temps imparti pour répondre à la question.
	 * 
	 * @param timer Le temps imparti pour répondre à la question.
	 */
	@Override
	public void setTimer(int timer)
	{
		this.timer = timer;
	}

	/**
	 * Définit le nombre de points attribués à la question.
	 * 
	 * @param nbPoint Le nombre de points attribués à la question.
	 */
	@Override
	public void setNbPoint(double nbPoint)
	{
		this.nbPoint = nbPoint;
	}

	/**
	 * Définit la difficulté de la question.
	 * 
	 * @param difficulte La difficulté de la question.
	 */
	@Override
	public void setDifficulte(int difficulte)
	{
		this.difficulte = difficulte;
	}

	/**
	 * Définit les notions associées à la question.
	 * 
	 * @param notions Les notions associées à la question.
	 */
	@Override
	public void setNotions(Notion notions)
	{
		this.notions = notions;
	}

	/**
	 * Définit l'explication de la question.
	 * 
	 * @param explication L'explication de la question.
	 */
	@Override
	public void setExplication(String explication)
	{
		this.explication = explication;
	}

	/**
	 * Retourne le nombre d'indices utilisés.
	 * 
	 * @return Le nombre d'indices utilisés.
	 */
	public int getNbIndiceUtilise()
	{
		return this.nbIndiceUtilise;
	}

	/**
	 * Définit le nombre d'indices utilisés.
	 * 
	 * @param nbIndiceUtilise Le nombre d'indices utilisés.
	 */
	public void setNbIndiceUtilise(int nbIndiceUtilise)
	{
		this.nbIndiceUtilise = nbIndiceUtilise;
	}

	/**
	 * Utilise un indice pour la question.
	 */
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

	/**
	 * Retourne le nombre d'indices disponibles.
	 * 
	 * @return Le nombre d'indices disponibles.
	 */
	public int getNbIndice()
	{
		return this.nbIndice;
	}

	/**
	 * Retourne la liste des réponses de type élimination.
	 * 
	 * @return La liste des réponses de type élimination.
	 */
	public List<ReponseElimination> getReponses()
	{
		return this.lstReponses;
	}

	/**
	 * Retourne la difficulté de la question sous forme de chaîne de caractères.
	 * 
	 * @return La difficulté de la question sous forme de chaîne de caractères.
	 */
	public String getStringDifficulte()
	{
		String niveau;
		switch(this.difficulte)
		{
			case 1 -> { niveau = "TF"; }
			case 2 -> { niveau = "F" ; }
			case 3 -> { niveau = "M" ; }
			case 4 -> { niveau = "D" ; }
			default   ->
			{
				niveau = "INVALIDE";
			}
		}
		return niveau;
	}
}
