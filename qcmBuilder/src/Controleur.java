package src;
import  java.util.ArrayList;
import  src.metier.*;
import  src.ihm.*;

public class Controleur
{
	QCMBuilder qcmBuilder;
	

	public Controleur() 
	{
		qcmBuilder = new QCMBuilder();
		new FrameCreerQuestion(this);
	}

	public ArrayList<String> getChoixNotion(String s)
	{
		ArrayList<String> notions = new ArrayList<>();

		Ressource r = qcmBuilder.rechercherRessource(s);
		System.out.println(r.getNom());
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

	/*public boolean ajouterRessource(String nom)
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
	}*/

	/*public boolean ajouterQuestion(String type, String nomRessource, String nomNotion, String text, int timer, int nbPoint, int nbIndiceUtilise, int difficulte)
	{
		Notion n = qcmBuilder.rechercherRessource(nomRessource).rechercherNotion(nomNotion);
		Question q = null;


		switch (type) {
			case "QCM" -> {
				q = new QCM(n, 0, text, timer, nbPoint, nbIndiceUtilise, difficulte);
			}

			case "Association" -> {
				q = new Association(n, 0, text, timer, nbPoint, nbIndiceUtilise, difficulte);
			}

			case "Elimination" -> {
				q = new Elimination(n, 0, text, timer, nbPoint, nbIndiceUtilise, difficulte);
			}
		}

		return n.ajouterQuestion(q);
	}*/

	public void creerQuestion(String s1, String s2, String s3, String s4, String s5, int i1, int i2, ArrayList<String> lst, int i3)
	{
		
	}


	public void genererQuestionnaire(String nomRessource, String nomNotion)
	{
		qcmBuilder.genererQuestionnaire(nomRessource, nomNotion);
	}

	public static void main(String[] args) {
		new Controleur();
	}

}
