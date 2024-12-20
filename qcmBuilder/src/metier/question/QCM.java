package src.metier.question;

import java.util.Collections;
import java.util.List;
import src.metier.Notion;
import src.metier.reponse.ReponseQCM;

/**
 * La classe QCM représente une question de type QCM.
 * Elle contient un texte de question, un timer, un nombre de points, une difficulté,
 * une explication, une liste de réponses et un indicateur si c'est une question à choix unique.
 */
public class QCM implements Question
{
	private Notion           notions;
	private String           text;
	private int              timer;
	private double           nbPoint;
	private int              difficulte;
	private String           explication;
	private boolean          estQCU;
	private List<ReponseQCM> lstReponses;
	private int 			 nbReponseVrai;

	/**
	 * Constructeur de la classe QCM.
	 * 
	 * @param notion Les notions associées à la question.
	 * @param text Le texte de la question.
	 * @param timer Le temps imparti pour répondre à la question.
	 * @param nbPoint Le nombre de points attribués à la question.
	 * @param difficulte La difficulté de la question.
	 * @param lstReponses La liste des réponses de type QCM.
	 * @param explication L'explication de la question.
	 */
	public QCM (Notion notion, String text, int timer, double nbPoint, int difficulte, List<ReponseQCM> lstReponses, String explication)
	{
		this.notions     = notion;
		this.text        = text;
		this.timer       = timer;
		this.nbPoint     = nbPoint;
		this.difficulte  = difficulte;
		this.lstReponses = lstReponses;
		this.explication = explication;
		this.estQCU      = this.estUnique();

		this.nbReponseVrai = 0;
		for (ReponseQCM reponse : lstReponses)
		{
			if (reponse.estVrai())
			{
				this.nbReponseVrai++;
			}
		}

		if (this.nbReponseVrai > 1)
			this.estQCU = false;
		else
			this.estQCU = true;
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
	 * Retourne la liste des réponses de type QCM.
	 * 
	 * @return La liste des réponses de type QCM.
	 */
	public List<ReponseQCM> getLstReponses()
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
	 * Retourne si la question est à choix unique.
	 * 
	 * @return true si la question est à choix unique, false sinon.
	 */
	public boolean estUnique()
	{
		return estQCU;
	}

	/**
	 * Définit la liste des réponses de type QCM.
	 * 
	 * @param lstReponses La liste des réponses de type QCM.
	 */
	public void setLstReponses(List<ReponseQCM> lstReponses)
	{
		this.lstReponses = lstReponses;
	}

	/**
	 * Retourne la liste des réponses de type QCM.
	 * 
	 * @return La liste des réponses de type QCM.
	 */
	public List<ReponseQCM> getlstReponses()
	{
		return lstReponses;
	}

	/**
	 * Retourne le nombre de réponses vraies.
	 * 
	 * @return Le nombre de réponses vraies.
	 */
	public int getNbReponseVrai()
	{
		return nbReponseVrai;
	}

	public void melanger()
	{
		Collections.shuffle(lstReponses);
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
