package src.metier.question;

import java.util.List;
import src.metier.Notion;
import src.metier.reponse.*;

/**
 * La classe Association représente une question d'association dans un QCM.
 * Elle implémente l'interface Question.
 */
public class Association implements Question
{
	private Notion                   notions;
	private String                   text;
	private int                      timer;
	private double                   nbPoint;
	private int                      difficulte;
	private String                   explication;
	private List<ReponseAssociation> lstReponseAsso;
	private String                   urlImage;

	/**
	 * Constructeur de la classe Association.
	 *
	 * @param notion        La notion associée à la question.
	 * @param text          Le texte de la question.
	 * @param timer         Le temps imparti pour répondre à la question.
	 * @param nbPoint       Le nombre de points attribués à la question.
	 * @param difficulte    Le niveau de difficulté de la question.
	 * @param lstReponseAsso La liste des réponses d'association.
	 * @param explication   L'explication de la réponse.
	 */
	public Association(Notion notion, String text, int timer, double nbPoint, int difficulte,
			List<ReponseAssociation> lstReponseAsso, String explication)
	{
		this.notions        = notion;
		this.text           = text;
		this.timer          = timer;
		this.nbPoint        = nbPoint;
		this.difficulte     = difficulte;
		this.lstReponseAsso = lstReponseAsso;
		this.explication    = explication;
		this.urlImage       = "";
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
	 * Retourne le niveau de difficulté de la question.
	 *
	 * @return Le niveau de difficulté de la question.
	 */
	@Override
	public int getDifficulte()
	{
		return this.difficulte;
	}

	/**
	 * Retourne la notion associée à la question.
	 *
	 * @return La notion associée à la question.
	 */
	@Override
	public Notion getNotions()
	{
		return this.notions;
	}

	/**
	 * Retourne l'explication de la réponse.
	 *
	 * @return L'explication de la réponse.
	 */
	@Override
	public String getExplication()
	{
		return this.explication;
	}

	/**
	 * Retourne la liste des réponses d'association.
	 *
	 * @return La liste des réponses d'association.
	 */
	public List<ReponseAssociation> getLstReponses()
	{
		return this.lstReponseAsso;
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
	 * Définit le niveau de difficulté de la question.
	 *
	 * @param difficulte Le niveau de difficulté de la question.
	 */
	@Override
	public void setDifficulte(int difficulte)
	{
		this.difficulte = difficulte;
	}

	/**
	 * Définit la notion associée à la question.
	 *
	 * @param notions La notion associée à la question.
	 */
	@Override
	public void setNotions(Notion notions)
	{
		this.notions = notions;
	}

	/**
	 * Définit l'explication de la réponse.
	 *
	 * @param explication L'explication de la réponse.
	 */
	@Override
	public void setExplication(String explication)
	{
		this.explication = explication;
	}

	/**
	 * Retourne la liste des réponses d'association.
	 *
	 * @return La liste des réponses d'association.
	 */
	public List<ReponseAssociation> getReponses()
	{
		return this.lstReponseAsso;
	}

	/**
	 * Définit la liste des réponses d'association.
	 *
	 * @param lstReponseAsso La liste des réponses d'association.
	 */
	public void setReponses(List<ReponseAssociation> lstReponseAsso)
	{
		this.lstReponseAsso = lstReponseAsso;
	}

	/**
	 * Retourne le niveau de difficulté sous forme de chaîne de caractères.
	 *
	 * @return Le niveau de difficulté sous forme de chaîne de caractères.
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

	/**
	 * Définit l'URL de l'image associée à la question.
	 *
	 * @param urlImage L'URL de l'image associée à la question.
	 */
	public void setUrlImage(String urlImage)
	{
		this.urlImage = urlImage;
	}

	/**
	 * Retourne l'URL de l'image associée à la question.
	 *
	 * @return L'URL de l'image associée à la question.
	 */
	public String getUrlImage()
	{
		return this.urlImage;
	}

}
