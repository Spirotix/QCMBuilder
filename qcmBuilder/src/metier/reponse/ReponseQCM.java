package src.metier.reponse;

/**
 * La classe ReponseQCM représente une réponse de type QCM.
 * Elle contient un texte de réponse et un indicateur si la réponse est vraie ou fausse.
 */
public class ReponseQCM implements Reponse
{
	private boolean estVrai;
	private String  textReponse;

	/**
	 * Constructeur de la classe ReponseQCM.
	 * 
	 * @param textReponse Le texte de la réponse.
	 * @param estVrai Indicateur si la réponse est vraie ("Vrai") ou fausse.
	 */
	public ReponseQCM(String textReponse, String estVrai)
	{
		this.textReponse = textReponse;
		if(estVrai.equals("Vrai")){ this.estVrai=true; }
		else{ this.estVrai = false;}
	}

	/**
	 * Retourne si la réponse est vraie.
	 * 
	 * @return true si la réponse est vraie, false sinon.
	 */
	public boolean estVrai() { return estVrai; }

	/**
	 * Définit si la réponse est vraie.
	 * 
	 * @param estVrai true si la réponse est vraie, false sinon.
	 */
	public void setEstVrai(boolean estVrai) { this.estVrai = estVrai; }

	@Override
	public String getText()
	{
		return textReponse;
	}

	@Override
	public void setText(String text)
	{
		this.textReponse = text;
	}
}
