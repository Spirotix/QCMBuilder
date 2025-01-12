import java.util.ArrayList;
import metier.*;



public class Controleur
{
	QCMBuilder qcmBuilder;
	// Mettre la frame du questionnaire

	public Controleur() 
	{
		qcmBuilder = new QCMBuilder();
	}

	public ArrayList<String> getChoixNotion(String s)
	{
		ArrayList<String> notions = new ArrayList<>();

		Ressource r = qcmBuilder.rechercherRessource(s);
		for (Notion n : r.getNotions())
		{
			notions.add(n.getNom());
		}
		
		return notions;
	}

	public ArrayList<String> getChoixRessources()
	{
		ArrayList<String> ressources = new ArrayList<>();
		for (Ressource r : qcmBuilder.getRessources())
		{
			ressources.add(r.getNom());
		}
		return ressources;
	}

	public boolean ajouterRessource(String nom)
	{
		return qcmBuilder.ajouterRessource(new Ressource(nom));
	}

	public boolean supprimerRessource(String nom)
	{
		return qcmBuilder.supprimerRessource(qcmBuilder.rechercherRessource(nom));
	}

	public boolean modifierRessource(String nom, String newNom)
	{
		Ressource r = qcmBuilder.rechercherRessource(nom);
		return r.setNom(newNom);
	}

	public boolean ajouterNotion(String nomRessource, String nomNotion)
	{
		Ressource r = qcmBuilder.rechercherRessource(nomRessource);
		return r.ajouterNotion(new Notion(nomNotion, r));
	}

	public boolean supprimerNotion(String nomRessource, String nomNotion)
	{
		Ressource r = qcmBuilder.rechercherRessource(nomRessource);
		return r.supprimerNotion(r.rechercherNotion(nomNotion));
	}

	public boolean modifierNotion(String nomRessource, String nomNotion, String newNom)
	{
		Notion n = qcmBuilder.rechercherRessource(nomRessource).rechercherNotion(nomNotion);
		return n.setNom(newNom);
	}

	public boolean ajouterQuestion(String type, String nomRessource, String nomNotion, String text, int timer, int nbPoint, int nbIndiceUtilisé, int difficulte)
	{
		Notion n = qcmBuilder.rechercherRessource(nomRessource).rechercherNotion(nomNotion);
		Question q = null;


		switch (type) {
			case "QCM" -> {
				q = new QCM(n, 0, text, timer, nbPoint, nbIndiceUtilisé, difficulte);
			}

			case "Association" -> {
				q = new Association(n, 0, text, timer, nbPoint, nbIndiceUtilisé, difficulte);
			}

			case "Elimination" -> {
				q = new Elimination(n, 0, text, timer, nbPoint, nbIndiceUtilisé, difficulte);
			}
		}

		return n.ajouterQuestion(q);
	}

	public boolean supprimerQuestion(String nomRessource, String nomNotion, String text)
	{
		Notion n = qcmBuilder.rechercherRessource(nomRessource).rechercherNotion(nomNotion);
		return n.supprimerQuestion(n.rechercherQuestion(text));
	}

	public boolean modifierQuestion(String nomRessource, String nomNotion, String text, String newText, int newTimer, int newNbPoint, int newNbIndiceUtilisé, int newDifficulte)
	{
		Question q = qcmBuilder.rechercherRessource(nomRessource).rechercherNotion(nomNotion).rechercherQuestion(text);
		q.setText(newText);
		q.setTimer(newTimer);
		q.setNbPoint(newNbPoint);
		q.setNbIndiceUtilisé(newNbIndiceUtilisé);
		q.setDifficulte(newDifficulte);
		return true;
	}
}
