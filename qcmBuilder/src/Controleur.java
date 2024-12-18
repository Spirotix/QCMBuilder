package src;

import java.util.ArrayList;
import src.ihm.*;
import src.metier.*;
import src.metier.question.*;

/**
 * La classe Controleur gère les interactions entre l'interface utilisateur et le modèle QCMBuilder.
 * Elle permet de récupérer des notions, des ressources, des questions, d'ajouter des ressources et des notions, de créer des questions et de générer des questionnaires.
 */
public class Controleur
{
	private QCMBuilder qcmBuilder;
	private FrameMenu frameMenu;

	/**
	 * Constructeur de la classe Controleur.
	 * Initialise un nouvel objet QCMBuilder et un nouvel objet FrameMenu.
	 */
	public Controleur()
	{
		this.qcmBuilder = new QCMBuilder();
		this.frameMenu = new FrameMenu(this);
	}

	/**
	 * Récupère les notions associées à une ressource donnée.
	 *
	 * @param s Le nom de la ressource.
	 * @return Une liste des noms des notions associées à la ressource.
	 */
	public ArrayList<String> getChoixNotion(String s)
	{
		ArrayList<String> notions = new ArrayList<>();
		Ressource ressource = qcmBuilder.rechercherRessource(s);
		if (ressource != null) {
			for (Notion notion : ressource.getNotions()) {
				notions.add(notion.getNom());
			}
		}
		return notions;
	}

	/**
	 * Récupère toutes les ressources disponibles.
	 *
	 * @return Une liste des codes et noms des ressources disponibles.
	 */
	public ArrayList<String> getChoixRessources()
	{
		ArrayList<String> ressources = new ArrayList<>();
		for (Ressource ressource : qcmBuilder.getRessources()) {
			ressources.add(ressource.getCode() + "_" + ressource.getNom());
		}
		return ressources;
	}

	/**
	 * Récupère les questions associées à une ressource et une notion données.
	 *
	 * @param ressource Le nom de la ressource.
	 * @param notion Le nom de la notion.
	 * @return Une liste des textes des questions associées à la ressource et à la notion.
	 */
	public ArrayList<String> getQuestions(String ressource, String notion)
	{
		ArrayList<String> str = new ArrayList<>();
		String temp;

		for (Ressource r : this.qcmBuilder.getRessources())
		{
			temp = r.getCode() + "_" + r.getNom();
			if (temp.equals(ressource))
				for (Notion n : r.getNotions())
					if (n.getNom().equals(notion))
						for (Question q : n.getQuestions())
							str.add(q.getText());
		}

		return str;
	}

	/**
	 * Supprime une question donnée associée à une ressource et une notion.
	 *
	 * @param nomQuestion Le nom de la question à supprimer.
	 * @param ressource Le nom de la ressource.
	 * @param notion Le nom de la notion.
	 */
	public void supprimerQuestion(String nomQuestion, String ressource, String notion)
	{
		for (Ressource r : this.qcmBuilder.getRessources())
		{
			if ((r.getCode() + "_" + r.getNom()).equals(ressource))
				for (Notion n : r.getNotions())
					if (n.getNom().equals(notion))
						for (Question q : n.getQuestions())
							if (q.getText().equals(nomQuestion))
							{
								System.out.println("SupprimerC");
								n.supprimerQuestion(q);
							}
		}
	}

	/**
	 * Supprime une notion existante à une ressource donnée.
	 *
	 * @param nomRessource Le nom de la ressource.
	 * @param nomNotion Le nom de la notion.
	 * @return true si la notion a été supprimée avec succès, false sinon.
	 */
	public boolean supprimerNotion(String nomRessource, String nomNotion)
	{
		Ressource r = qcmBuilder.rechercherRessource(nomRessource);
		return r.supprimerNotion( r.rechercherNotion(nomNotion) );
	}

	/**
	 * Supprime une ressource donnée.
	 *
	 * @param codeRessource Le code de la ressource.
	 * @return true si la ressource a été supprimée avec succès, false sinon.
	 */
	public boolean supprimerRessource(String codeRessource)
	{
		return qcmBuilder.supprimerRessource( qcmBuilder.rechercherRessource(codeRessource) );
	}

	/**
	 * Ajoute une nouvelle notion à une ressource donnée.
	 *
	 * @param nomRessource Le nom de la ressource.
	 * @param nomNotion Le nom de la notion.
	 * @return true si la notion a été ajoutée avec succès, false sinon.
	 */
	public boolean ajouterNotion(String nomRessource, String nomNotion)
	{
		Ressource r = qcmBuilder.rechercherRessource(nomRessource);
		return r.ajouterNotion(new Notion(nomNotion, r));
	}

	/**
	 * Ajoute une nouvelle ressource.
	 *
	 * @param code Le code de la ressource.
	 * @param nom Le nom de la ressource.
	 * @return true si la ressource a été ajoutée avec succès, false sinon.
	 */
	public boolean ajouterRessource(String code, String nom)
	{
		return qcmBuilder.ajouterRessource(new Ressource(code, nom));
	}

	/**
	 * Crée une nouvelle question.
	 *
	 * @param type Le type de la question.
	 * @param code_nomRessource Le code et le nom de la ressource.
	 * @param nomNotion Le nom de la notion.
	 * @param text Le texte de la question.
	 * @param explication L'explication de la question.
	 * @param timer Le temps limite pour répondre à la question.
	 * @param nbPoint Le nombre de points attribués à la question.
	 * @param lstReponse La liste des réponses possibles.
	 * @param difficulte La difficulté de la question.
	 * @return true si la question a été créée avec succès, false sinon.
	 */
	public boolean creerQuestion(String type, String code_nomRessource, String nomNotion, String text, String explication, int timer, double nbPoint, ArrayList<TypeReponse> lstReponse, int difficulte)
	{
		return qcmBuilder.creerQuestion(type, code_nomRessource, nomNotion, text, timer, nbPoint, difficulte, lstReponse, explication);
	}

	/**
	 * Génère un questionnaire.
	 *
	 * @param ressource Le nom de la ressource.
	 * @param chrono Indique si le questionnaire est chronométré.
	 * @param questions La liste des types de questions à inclure dans le questionnaire.
	 */
	public void genererQuestionnaire(String ressource, boolean chrono, ArrayList<TypeQuestionnaire> questions)
	{
		// qcmBuilder.genererQuestionnaire(ressource, chrono, questions);
	}

	/**
	 * Point d'entrée de l'application.
	 *
	 * @param args Les arguments de la ligne de commande.
	 */
	public static void main(String[] args)
	{
		new Controleur();
	}
}
