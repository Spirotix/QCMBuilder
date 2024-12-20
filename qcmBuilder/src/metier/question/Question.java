package src.metier.question;

import src.metier.Notion;

/**
 * Interface représentant une question dans l'application QCM Builder.
 */
public interface Question 
{
	/**
	 * Retourne le texte de la question.
	 * 
	 * @return Le texte de la question.
	 */
	String getText();

	/**
	 * Retourne le temps imparti pour répondre à la question.
	 * 
	 * @return Le temps imparti pour répondre à la question.
	 */
	int getTimer();

	/**
	 * Retourne le nombre de points attribués à la question.
	 * 
	 * @return Le nombre de points attribués à la question.
	 */
	double getNbPoint();

	/**
	 * Retourne la difficulté de la question.
	 * 
	 * @return La difficulté de la question.
	 */
	int getDifficulte();

	/**
	 * Retourne la difficulté de la question sous forme de chaîne de caractères.
	 * 
	 * @return La difficulté de la question sous forme de chaîne de caractères.
	 */
	String getStringDifficulte();

	/**
	 * Retourne les notions associées à la question.
	 * 
	 * @return Les notions associées à la question.
	 */
	Notion getNotions();

	/**
	 * Retourne l'explication de la question.
	 * 
	 * @return L'explication de la question.
	 */
	String getExplication();

	/**
	 * Définit le texte de la question.
	 * 
	 * @param text Le texte de la question.
	 */
	void setText(String text);

	/**
	 * Définit le temps imparti pour répondre à la question.
	 * 
	 * @param timer Le temps imparti pour répondre à la question.
	 */
	void setTimer(int timer);

	/**
	 * Définit le nombre de points attribués à la question.
	 * 
	 * @param nbPoint Le nombre de points attribués à la question.
	 */
	void setNbPoint(double nbPoint);

	/**
	 * Définit la difficulté de la question.
	 * 
	 * @param difficulte La difficulté de la question.
	 */
	void setDifficulte(int difficulte);

	/**
	 * Définit les notions associées à la question.
	 * 
	 * @param notions Les notions associées à la question.
	 */
	void setNotions(Notion notions);

	/**
	 * Définit l'explication de la question.
	 * 
	 * @param explication L'explication de la question.
	 */
	void setExplication(String explication);

	/**
	 * Retourne l'URL de l'image associée à la question.
	 * 
	 * @return L'URL de l'image associée à la question.
	 */
	String getUrlImage();

	/**
	 * Définit l'URL de l'image associée à la question.
	 * 
	 * @param urlImage L'URL de l'image associée à la question.
	 */
	void setUrlImage(String urlImage);
}
