package src.metier.reponse;

/**
 * Classe ReponseAssociation représentant une réponse associée dans un QCM.
 * Hérite de la classe Reponse.
 */
public class ReponseAssociation extends Reponse
{
	private ReponseAssociation reponseAssocie;
	private boolean aGauche;

	/**
	 * Constructeur de la classe ReponseAssociation.
	 *
	 * @param textReponse      Le texte de la réponse.
	 * @param reponseAssocie   La réponse associée.
	 * @param aGauche          Indique si la réponse est à gauche.
	 */
	public ReponseAssociation(String textReponse, ReponseAssociation reponseAssocie, boolean aGauche)
	{
		super(textReponse);

		this.reponseAssocie = reponseAssocie;
		this.aGauche = aGauche;
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
}
