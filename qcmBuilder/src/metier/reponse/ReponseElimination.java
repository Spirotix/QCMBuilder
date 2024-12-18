package src.metier.reponse;

/**
 * La classe ReponseElimination représente une réponse de type élimination.
 * Elle contient un texte de réponse, un ordre d'indice, un indicateur de visibilité,
 * un indicateur si la réponse est vraie ou fausse, et un nombre de points perdus.
 */
public class ReponseElimination implements Reponse
{
	private int     ordreIndice;
	private boolean estVisible;
	private boolean estVrai;
	private double  nbPointPerdu;
	private String  textReponse;

	/**
	 * Constructeur de la classe ReponseElimination.
	 * 
	 * @param estVrai Indicateur si la réponse est vraie ("Vrai") ou fausse.
	 * @param textReponse Le texte de la réponse.
	 * @param ordreIndice L'ordre de l'indice.
	 * @param nbPointPerdu Le nombre de points perdus.
	 */
	public ReponseElimination(String estVrai, String textReponse, int ordreIndice, double nbPointPerdu )
	{
		this.textReponse  = textReponse;
		this.ordreIndice  = ordreIndice;
		this.estVisible   = true;
		this.estVrai      = estVrai.equals("Vrai");
		this.nbPointPerdu = nbPointPerdu;
	}

	/**
	 * Retourne l'ordre de l'indice.
	 * 
	 * @return L'ordre de l'indice.
	 */
	public int getOrdreIndice() { return ordreIndice; }

	/**
	 * Définit l'ordre de l'indice.
	 * 
	 * @param ordreIndice L'ordre de l'indice.
	 */
	public void setOrdreIndice(int ordreIndice) { this.ordreIndice = ordreIndice; }

	/**
	 * Retourne si la réponse est visible.
	 * 
	 * @return true si la réponse est visible, false sinon.
	 */
	public boolean estVisible() { return estVisible; }

	/**
	 * Définit si la réponse est visible.
	 * 
	 * @param estVisible true si la réponse est visible, false sinon.
	 */
	public void setEstVisible(boolean estVisible) { this.estVisible = estVisible; }

	/**
	 * Retourne si la réponse est vraie.
	 * 
	 * @return true si la réponse est vraie, false sinon.
	 */
	public boolean estVrai() { return estVrai; }

	/**
	 * Retourne le nombre de points perdus.
	 * 
	 * @return Le nombre de points perdus.
	 */
	public double getNbPointPerdu() { return nbPointPerdu; }

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
}
