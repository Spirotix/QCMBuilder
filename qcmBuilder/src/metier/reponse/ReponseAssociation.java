package src.metier.reponse;

/**
 * Classe ReponseAssociation représentant une réponse associée dans un QCM.
 * Hérite de la classe Reponse.
 */
public class ReponseAssociation implements Reponse
{
	private ReponseAssociation reponseAssocie;
	private boolean aGauche;
	private String textReponse;
	private String urlImage;

	/**
	 * Constructeur de la classe ReponseAssociation.
	 *
	 * @param textReponse      Le texte de la réponse.
	 * @param reponseAssocie   La réponse associée.
	 * @param aGauche          Indique si la réponse est à gauche.
	 */
	public ReponseAssociation(String textReponse, ReponseAssociation reponseAssocie, boolean aGauche)
	{
		this.textReponse = textReponse;

		this.reponseAssocie = reponseAssocie;
		this.aGauche = aGauche;
		this.urlImage = "";
	}

	public ReponseAssociation(String textReponse, ReponseAssociation reponseAssocie, boolean aGauche, String urlImage)
	{
		this.textReponse = textReponse;

		this.reponseAssocie = reponseAssocie;
		this.aGauche = aGauche;
		this.urlImage = urlImage;
	}

	/**
	 * Retourne la réponse associée.
	 *
	 * @return La réponse associée.
	 */
	public ReponseAssociation getReponseAssocie() { return reponseAssocie; }

	/**
	 * Définit la réponse associée.
	 *
	 * @param reponseAssocie La nouvelle réponse associée.
	 */
	public void setReponseAssocie(ReponseAssociation reponseAssocie) { this.reponseAssocie = reponseAssocie; }

	/**
	 * Indique si la réponse est à gauche.
	 *
	 * @return true si la réponse est à gauche, false sinon.
	 */
	public boolean estAGauche() { return aGauche; }

	/**
	 * Définit si la réponse est à gauche.
	 *
	 * @param aGauche true si la réponse est à gauche, false sinon.
	 */
	public void setAGauche(boolean aGauche) { this.aGauche = aGauche; }
	
	/**
	 * Retourne le texte de la réponse.
	 * 
	 * @return Le texte de la réponse.
	 */
	@Override
	public String getText()
	{
		return textReponse;
	}

	/**
	 * Définit si le texte de la réponse.
	 * 
	 * @param textReponse Le texte de la réponse.
	 */
	@Override
	public void setText(String textReponse)
	{
		this.textReponse = textReponse;
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
