package src.metier;

import src.metier.reponse.ReponseAssociation;

public class CoupleAssociation
{
	private ReponseAssociation gauche;
	private ReponseAssociation droite;
 
	public CoupleAssociation(ReponseAssociation gauche, ReponseAssociation droite)
	{
		this.gauche = gauche;
		this.droite = droite;
	}
 
	public ReponseAssociation getGauche()
	{
		return this.gauche;
	}
 
	public ReponseAssociation getDroite()
	{
		return this.droite;
	}
 }
 
