/**
 * Cette classe représente une association de couple dans l'application QCM Builder.
 * Elle contient une paire de réponses associées.
 * 
 * @author Groupe 2
 */
package src.metier;

import src.metier.reponse.ReponseAssociation;

/**
 * La classe CoupleAssociation représente une association entre deux réponses.
 * Elle contient deux objets ReponseAssociation, un à gauche et un à droite.
 */
public class CoupleAssociation
{
	private ReponseAssociation gauche;
	private ReponseAssociation droite;
 
	/**
	 * Constructeur de la classe CoupleAssociation.
	 * 
	 * @param gauche La réponse associée à gauche.
	 * @param droite La réponse associée à droite.
	 */
	public CoupleAssociation(ReponseAssociation gauche, ReponseAssociation droite)
	{
		this.gauche = gauche;
		this.droite = droite;
	}
 
	/**
	 * Retourne la réponse associée à gauche.
	 * 
	 * @return La réponse associée à gauche.
	 */
	public ReponseAssociation getGauche()
	{
		return this.gauche;
	}
 
	/**
	 * Retourne la réponse associée à droite.
	 * 
	 * @return La réponse associée à droite.
	 */
	public ReponseAssociation getDroite()
	{
		return this.droite;
	}
}

