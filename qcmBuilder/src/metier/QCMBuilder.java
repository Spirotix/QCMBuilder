package src.metier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import src.ihm.*;
import src.metier.reponse.*;
import src.metier.question.*;

public class QCMBuilder
{
	private List<Ressource> ressources;

	public QCMBuilder()
	{
		this.ressources = lireRessources();
	}

	private List<Ressource> lireRessources()
	{
		List<Ressource> ressources = new ArrayList<>();
		try
		{
			Scanner scanner = new Scanner(new File("../data/ressources_notions.csv"));
			scanner.nextLine();
			while (scanner.hasNextLine())
			{
				String   line         = scanner.nextLine();
				String[] parts        = line.split(";");
				String   codeRessource = parts[0];
				String   nomRessource  = parts[1];

				Ressource ressource = new Ressource(codeRessource, nomRessource);

				for (Ressource r : ressources)
				{
					if (r.getCode().equals(codeRessource))
						ressource = r;
				}

				if ( ! ressources.contains(ressource) )
					ressources.add(ressource);
			}
			scanner.close();
		} 
		catch (FileNotFoundException e) { e.printStackTrace(); }

		return ressources;
	}

	public List<Ressource> getRessources() { return ressources; }

	public boolean ajouterRessource(Ressource ressource)
	{
		for (Ressource r : ressources)
		{
			if ( r.getCode().equals(ressource.getCode()) )
				ressource = r;
		}

		if ( ! ressources.contains(ressource))
		{
			ressources.add(ressource);
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean supprimerRessource(Ressource ressource)
	{
		for (Ressource r : ressources)
		{
			if ( r.getCode().equals(ressource.getCode()) )
				ressource = r;
		}

		if ( ressources.contains(ressource))
		{
			//ressource.supprimerAllNotion();
			ressources.remove(ressource);
			return true;
		}
		else
		{
			return false;
		}
	}

	public Ressource rechercherRessource(String code_nom)
	{
		Ressource ressourceTrouvee = null;
		for (Ressource ressource : ressources)
		{
			if ((ressource.getCode()+"_"+ressource.getNom()).equals(code_nom))
				ressourceTrouvee = ressource;
		}
		return ressourceTrouvee;
	}

	public boolean modifierRessource(Ressource ressource, String nouveauNom)
	{
		if (ressource == null)
			return false;
		if (!ressources.contains(ressource))
			return false;
		ressource.setNom(nouveauNom);
		return true;
	}

	public boolean creerQuestion(String type, String code_nomRessource, String nomNotion, String text, int timer, double nbPoint, int difficulte, /*List<String>*/ ArrayList<TypeReponse> sLstReponses, String explication)
	{
		Notion notion = rechercherRessource( code_nomRessource.substring(0, code_nomRessource.indexOf("_")) ).rechercherNotion(nomNotion);

		if ( type.equals("Elimination") )
		{
			List<ReponseElimination> lstReponses = new ArrayList<>();
			for (TypeReponse typeReponse : sLstReponses)
			{
				String StringVrai;
				if ( typeReponse.getEstBonneReponse())
					StringVrai = "Vrai";
				else
					StringVrai = "Faux";

				ReponseElimination reponse = new ReponseElimination( StringVrai, typeReponse.getContenu(), typeReponse.getOrdre(), typeReponse.getCout() );
				lstReponses.add(reponse);
			}

			// Question question = new Elimination(this, text, timer, nbPoint, difficulte, lstReponses, nbIndice, "");
			return true;
		}
		else if ( type.equals("QCM") )
		{
			List<ReponseQCM> lstReponses = new ArrayList<>();
			for (TypeReponse typeReponse : sLstReponses)
			{
				String StringVrai;
				if ( typeReponse.getEstBonneReponse())
					StringVrai = "Vrai";
				else
					StringVrai = "Faux";

				ReponseQCM reponse = new ReponseQCM(StringVrai, typeReponse.getContenu());
				lstReponses.add(reponse);
			}

			notion.ajouterQuestion( new QCM(notion, text, timer, nbPoint, difficulte, lstReponses, explication) );
			return true;
		}
		else if ( type.equals("Association") )
		{
			List<ReponseAssociation> lstReponses = new ArrayList<>();
			// Question question = new Association(this, text, timer, nbPoint, difficulte, lstReponses, "");
			return true;
		}
		else
		{
			throw new Exception("Le type de la question crée est invalide, ou n'est pas pris en charge.")
		}*/

		return false;
	}

	public void genererQuestionnaire(String nomRessource, List<String> nomsNotions)
	{
		try {

			// Create directories
			new File("../questionnaire/css").mkdirs();
			new File("../questionnaire/images").mkdirs();
			new File("../questionnaire/pages").mkdirs();
			new File("../questionnaire/script").mkdirs();

			String headerQuestion = "<!DOCTYPE html>\n" + //
									"<html lang=\"fr\">\n" + //
									"<head>\n" + //
									"\t<meta charset=\"UTF-8\">\n" + //
									"\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" + //
									"\t<link rel=\"stylesheet\" href=\"../css/styles.css\">\n" + //
									"\t<title>Accueil Questionnaire</title>\n" + //
									"</head>\n";

			String header = "<!DOCTYPE html>\n" + //
							"<html lang=\"fr\">\n" + //
							"<head>\n" + //
							"\t<meta charset=\"UTF-8\">\n" + //
							"\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" + //
							"\t<link rel=\"stylesheet\" href=\"styles.css\">\n" + //
							"\t<title>Accueil Questionnaire</title>\n" + //
							"</head>\n";

			PrintWriter indexWriter = new PrintWriter("../questionnaire/index.html");
			String sIndex =  header + //
								"<body>\n" + //
								"\t\n" + //
								"\t<h1 class=\"evaluation-title\">Questionnaire "+ nomRessource +"</h1>\n" + //
								"\n" + //
								"\t<div class=\"informations\">\n" + //
								"\t\t<p class=\"estimated-time\">Temps estimé : <span class=\"estimated-time-data\">20h</span></p>\n" + //
								"\n" + //
								"\t\t<p class=\"resource\">Ressources concernée : <span class=\"resource-data\">"+nomRessource+"</span></p>\n" + //
								"\n" + //
								"\t\t<p class=\"notions-title\">Notion abordées :</p>\n" + //
								"\t\t<ul class=\"notions-list\">\n"; //

								for (String nom : nomsNotions)
									sIndex+="\t\t\t<li class=\"notion-item\"><span class=\"notions-item-data\">"+ nom +"</span></li>\n"; //

								sIndex+="\t\t</ul>\n" + //
								"\n" + //
								"\t\t<p class=\"questions-count\">Nombre de questions : <span class=\"questions-count-data\">10</span></p>\n" + //
								"\t\t<ul class=\"difficulty-list\">\n" + //
								"\t\t\t<li class=\"difficulty-item very-easy\">Très facile : <span class=\"difficulty-item-data\">0</span></li>\n" + //
								"\t\t\t<li class=\"difficulty-item easy\">     Facile :      <span class=\"difficulty-item-data\">3</span></li>\n" + //
								"\t\t\t<li class=\"difficulty-item medium\">   Moyenne :     <span class=\"difficulty-item-data\">4</span></li>\n" + //
								"\t\t\t<li class=\"difficulty-item hard\">     Difficile :   <span class=\"difficulty-item-data\">3</span></li>\n" + //
								"\t\t</ul>\n" + //
								"\t\t<button type=\"button\" class=\"start-button\">Commencer</button>\n" + //
								"\t</div>\n" + //
								"\t<script src=\"script/index.js\"></script>\n" + //
								"</body>\n" + //
								"</html>";
			indexWriter.println(sIndex);
			indexWriter.close();

			PrintWriter finWriter = new PrintWriter("../questionnaire/fin.html");
			String sFin = header + //
							"<body>\n" + //
							"\t\n" + //
							"\t<h1 class=\"evaluation-title\">Fin du questionnaire</h1>\n" + //
							"\n" + //
							"\t<div class=\"informations\">\n" + //
							"\t\t<p class=\"estimated-time\">Temps estimé : <span class=\"estimated-time-data\">20h</span></p>\n" + //
							"\n" + //
							"\t\t<p class=\"resource\">Ressources concernée : <span class=\"resource-data\">r1.01</span></p>\n" + //
							"\n" + //
							"\t\t<p class=\"notions-title\">Notion abordées :</p>\n" + //
							"\t\t<ul class=\"notions-list\">\n" + //
							"\t\t\t<li class=\"notion-item\"><span class=\"notions-item-data\">Notion 1</span></li>\n" + //
							"\t\t\t<li class=\"notion-item\"><span class=\"notions-item-data\">Notion 2</span></li>\n" + //
							"\t\t\t<li class=\"notion-item\"><span class=\"notions-item-data\">Notion 3</span></li>\n" + //
							"\t\t</ul>\n" + //
							"\n" + //
							"\t\t<p class=\"questions-count\">Nombre de questions : <span class=\"questions-count-data\">10</span></p>\n" + //
							"\t\t<ul class=\"difficulty-list\">\n" + //
							"\t\t\t<li class=\"difficulty-item very-easy\">Très facile : <span class=\"difficulty-item-data\">0</span></li>\n" + //
							"\t\t\t<li class=\"difficulty-item easy\">     Facile :      <span class=\"difficulty-item-data\">3</span></li>\n" + //
							"\t\t\t<li class=\"difficulty-item medium\">   Moyenne :     <span class=\"difficulty-item-data\">4</span></li>\n" + //
							"\t\t\t<li class=\"difficulty-item hard\">     Difficile :   <span class=\"difficulty-item-data\">3</span></li>\n" + //
							"\t\t</ul>\n" + //
							"\n" + //
							"\t\t<!-- Score -->\n" + //
							"\t\t<p class=\"score\">Votre score : <span class=\"score-data\">8/10</span></p>\n" + //
							"\n" + //
							"\t\t<!-- Bouton recommencer -->\n" + //
							"\t\t<button type=\"button\" class=\"restart-button\" onclick=\"window.location.href='index.html'\">Recommencer</button>\n" + //
							"\t</div>\n" + //
							"</body>\n" + //
							"</html>";
			finWriter.println(sFin);
			finWriter.close();


			PrintWriter stylesCss = new PrintWriter("../questionnaire/styles.css");
			String sStyles = "body {\n" + //
							"\tfont-family: Arial, sans-serif;\n" + //
							"\tbackground-color: #f4f4f9;\n" + //
							"\tcolor: #333;\n" + //
							"\tmargin: 0;\n" + //
							"\tpadding: 20px;\n" + //
							"}\n" + //
							"\n" + //
							"h1.evaluation-title {\n" + //
							"\tcolor: #395cce;\n" + //
							"\ttext-align: center;\n" + //
							"\tmargin-bottom: 20px;\n" + //
							"\tfont-size: 3em;\n" + //
							"}\n" + //
							"\n" + //
							".informations {\n" + //
							"\tbackground-color: #fff;\n" + //
							"\tborder: 1px solid #ddd;\n" + //
							"\tborder-radius: 10px;\n" + //
							"\tpadding: 20px;\n" + //
							"\tbox-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);\n" + //
							"\tmax-width: 600px;\n" + //
							"\tmargin: 20px auto;\n" + //
							"}\n" + //
							"\n" + //
							"p.estimated-time,\n" + //
							"p.resource,\n" + //
							"p.notions-title,\n" + //
							"p.questions-count,\n" + //
							"p.score {\n" + //
							"\tfont-size: 1.2em;\n" + //
							"\tmargin: 10px 0;\n" + //
							"}\n" + //
							"\n" + //
							"ul.notions-list,\n" + //
							"ul.difficulty-list {\n" + //
							"\tlist-style-type: none;\n" + //
							"\tpadding: 0;\n" + //
							"\tmargin-left: 5%;\n" + //
							"\tmargin-right: 10%;\n" + //
							"\tword-wrap: break-word;\n" + //
							"}\n" + //
							"\n" + //
							"ul.notions-list li.notion-item,\n" + //
							"ul.difficulty-list li.difficulty-item {\n" + //
							"\tbackground-color: #fff;\n" + //
							"\tborder: 1px solid #ddd;\n" + //
							"\tborder-radius: 5px;\n" + //
							"\tpadding: 10px;\n" + //
							"\tmargin: 5px 0;\n" + //
							"}\n" + //
							"\n" + //
							"ul.difficulty-list li.difficulty-item.very-easy {\n" + //
							"\tborder-left: 5px solid #8BC34A;\n" + //
							"}\n" + //
							"\n" + //
							"ul.difficulty-list li.difficulty-item.easy {\n" + //
							"\tborder-left: 5px solid #4CAF50;\n" + //
							"}\n" + //
							"\n" + //
							"ul.difficulty-list li.difficulty-item.medium {\n" + //
							"\tborder-left: 5px solid #FFEB3B;\n" + //
							"}\n" + //
							"\n" + //
							"ul.difficulty-list li.difficulty-item.hard {\n" + //
							"\tborder-left: 5px solid #FF5722;\n" + //
							"}\n" + //
							"\n" + //
							"span.estimated-time-data,\n" + //
							"span.resource-data,\n" + //
							"span.notions-item-data,\n" + //
							"span.questions-count-data,\n" + //
							"span.difficulty-item-data,\n" + //
							"span.score-data {\n" + //
							"\tfont-weight: bold;\n" + //
							"}\n" + //
							"\n" + //
							"p.score {\n" + //
							"\tfont-size: 2em;\n" + //
							"\ttext-align: center;\n" + //
							"\tmargin-top: 20px;\n" + //
							"}\n" + //
							"\n" + //
							".start-button,\n" + //
							".restart-button {\n" + //
							"\tdisplay: block;\n" + //
							"\twidth: 200px;\n" + //
							"\tmargin: 20px auto;\n" + //
							"\tpadding: 10px 20px;\n" + //
							"\tbackground-color: #395cce;\n" + //
							"\tcolor: white;\n" + //
							"\ttext-align: center;\n" + //
							"\tborder: none;\n" + //
							"\tborder-radius: 5px;\n" + //
							"\tcursor: pointer;\n" + //
							"\tfont-size: 1.2em;\n" + //
							"\tbox-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);\n" + //
							"\ttransition: background-color 0.3s ease;\n" + //
							"\t}\n" + //
							"\n" + //
							".start-button:hover,\n" + //
							".restart-button:hover {\n" + //
							"\tbackground-color: #4772ff;\n" + //
							"}";
			stylesCss.println(sStyles);
			stylesCss.close();

			

			// Create and write to CSS files
			PrintWriter cssWriter = new PrintWriter("../questionnaire/css/styles.css");
			cssWriter.println("/* TODO */");
			cssWriter.close();

			// Create and write to image files
			new File("../questionnaire/images/cheval1.jpg").createNewFile();
			new File("../questionnaire/images/cheval.jpg").createNewFile();
			new File("../questionnaire/images/drapeau_france.png").createNewFile();

			// Create and write to HTML files
			PrintWriter question1Writer = new PrintWriter("../questionnaire/pages/question1.html");
			String sQuestion1 = headerQuestion + //
								"<body value=\"1\">\n" + //
								"\t<div class=\"container question-multiple\">\n" + //
								"\t\t<div class=\"informations\">\n" + //
								"\t\t\t<p class=\"question-number\">Question numéro : <span class=\"question-number-data\">1</span></p>\n" + //
								"\t\t\t<p class=\"notion\">Notion : <span class=\"notion-data\">Notion 1</span></p>\n" + //
								"\t\t\t<p class=\"difficulty\">Difficulté : <span class=\"difficulty-data\">Facile</span></p>\n" + //
								"\t\t\t<p class=\"estimated-time\">Temps estimé : <span class=\"estimated-time-data\">30sec</span></p>\n" + //
								"\t\t</div>\n" + //
								"\n" + //
								"\t\t<!-- barre de progression des questions -->\n" + //
								"\t\t<div class=\"progress-bar\" data-total=\"4\" data-current=\"1\">\n" + //
								"\t\t\t<div class=\"progress-bar-fill\"></div>\n" + //
								"\t\t</div>\n" + //
								"\n" + //
								"\t\t<!-- Pas tous le temps -->\n" + //
								"\t\t<div class=\"countdown\">\n" + //
								"\t\t\t<p class=\"countdown-title\">Temps restant :</p>\n" + //
								"\t\t\t<p class=\"countdown-data\">50000</p>\n" + //
								"\t\t</div>\n" + //
								"\n" + //
								"\t\t<!-- Image pour la question -->\n" + //
								"\t\t<img src=\"../images/drapeau_france.png\" alt=\"rgpd\" class=\"question-image\">\n" + //
								"\n" + //
								"\t\t<button type=\"button\" class=\"complementary-file-button\">Voir le fichier complémentaire</button>\n" + //
								"\n" + //
								"\t\t<div class=\"question\">\n" + //
								"\t\t\t<p class=\"question-text\">Quels sont les couleurs du drapeau français ?</p>\n" + //
								"\t\t\t<div class=\"answers\">\n" + //
								"\t\t\t\t<div class=\"answer\">\n" + //
								"\t\t\t\t\t<input type=\"checkbox\" name=\"answer\" id=\"answer1\" value=\"answer3\">\n" + //
								"\t\t\t\t\t<label for=\"answer1\">Rouge</label>\n" + //
								"\t\t\t\t</div>\n" + //
								"\t\t\t\t<div class=\"answer\">\n" + //
								"\t\t\t\t\t<input type=\"checkbox\" name=\"answer\" id=\"answer2\" value=\"answer5\">\n" + //
								"\t\t\t\t\t<label for=\"answer2\">Jaune</label>\n" + //
								"\t\t\t\t</div>\n" + //
								"\t\t\t\t<div class=\"answer\">\n" + //
								"\t\t\t\t\t<input type=\"checkbox\" name=\"answer\" id=\"answer3\" value=\"answer1\">\n" + //
								"\t\t\t\t\t<label for=\"answer3\">Bleu</label>\n" + //
								"\t\t\t\t</div>\n" + //
								"\t\t\t\t<div class=\"answer\">\n" + //
								"\t\t\t\t\t<input type=\"checkbox\" name=\"answer\" id=\"answer4\" value=\"answer4\">\n" + //
								"\t\t\t\t\t<label for=\"answer4\">Vert</label>\n" + //
								"\t\t\t\t</div>\n" + //
								"\t\t\t\t<div class=\"answer\">\n" + //
								"\t\t\t\t\t<input type=\"checkbox\" name=\"answer\" id=\"answer5\" value=\"answer2\">\n" + //
								"\t\t\t\t\t<label for=\"answer5\">Blanc</label>\n" + //
								"\t\t\t\t</div>\n" + //
								"\t\t\t</div>\n" + //
								"\t\t</div>\n" + //
								"\n" + //
								"\t\t<!-- Attention si l'évaluation est chronométrée il ne peut pas revenir en arrière et\n" + //
								"\t\t\til peut choisir de ne pas répondre à certaines d'entre elles -->\n" + //
								"\t\t<div class=\"nav\">\n" + //
								"\t\t\t<button type=\"button\" class=\"nav-button previous-button\">Précédent</button>\n" + //
								"\t\t\t<button type=\"button\" class=\"nav-button validate-button\">Valider</button>\n" + //
								"\t\t\t<button type=\"button\" class=\"nav-button feedback-button\">Feedback</button>\n" + //
								"\t\t\t<button type=\"button\" class=\"nav-button next-button\">Suivant</button>\n" + //
								"\t\t</div>\n" + //
								"\t</div>\n" + //
								"\t<!-- PopUp pour le feedback -->\n" + //
								"\t<div class=\"popup\">\n" + //
								"\t\t<div class=\"popup-content\">\n" + //
								"\t\t\t<button type=\"button\" class=\"close-button\">Fermer</button>\n" + //
								"\t\t\t<p class=\"feedback-title\">Réponse</p>\n" + //
								"\t\t\t<p class=\"feedback-text\">Et oui ! Le drapeau français est BLEU BLANC et ROUGE.</p>\n" + //
								"\t\t</div>\n" + //
								"\t</div>\n" + //
								"\n" + //
								"\t<script src=\"../script/popup.js\"></script>\n" + //
								"\t<script src=\"../script/index.js\"></script>\n" + //
								"\n" + //
								"\t<script>\n" + //
								"\t\tdocument.addEventListener(\"DOMContentLoaded\", function () {\n" + //
								"\t\t\tconst progressBars = document.querySelectorAll(\".progress-bar\");\n" + //
								"\n" + //
								"\t\t\tprogressBars.forEach(progressBar => {\n" + //
								"\t\t\t\tconst total = parseInt(progressBar.dataset.total, 10);\n" + //
								"\t\t\t\tconst current = parseInt(progressBar.dataset.current, 10);\n" + //
								"\t\t\t\tconst progressFill = progressBar.querySelector(\".progress-bar-fill\");\n" + //
								"\n" + //
								"\t\t\t\tif (total && current >= 0) {\n" + //
								"\t\t\t\tconst widthPercentage = (current / total) * 100;\n" + //
								"\t\t\t\tprogressFill.style.width = `${widthPercentage}%`;\n" + //
								"\t\t\t\t}\n" + //
								"\t\t\t});\n" + //
								"\t\t});\n" + //
								"\t</script>\n" + //
								"</body>\n" + //
								"</html>";
			question1Writer.println(sQuestion1);
			question1Writer.close();

			PrintWriter question2Writer = new PrintWriter("../questionnaire/pages/question2.html");
			String sQuestion2 = headerQuestion + //
								"<body value=\"2\">\n" + //
								"\t<div class=\"container eliminate-question\">\n" + //
								"\t\t<div class=\"informations\">\n" + //
								"\t\t\t<p class=\"question-number\">Question numéro : <span class=\"question-number-data\">2</span></p>\n" + //
								"\t\t\t<p class=\"notion\">Notion : <span class=\"notion-data\">Notion 1</span></p>\n" + //
								"\t\t\t<p class=\"difficulty\">Difficulté : <span class=\"difficulty-data\">Facile</span></p>\n" + //
								"\t\t\t<p class=\"estimated-time\">Temps estimé : <span class=\"estimated-time-data\">30sec</span></p>\n" + //
								"\t\t</div>\n" + //
								"\n" + //
								"\t\t<!-- barre de progression des questions -->\n" + //
								"\t\t<div class=\"progress-bar\" data-total=\"4\" data-current=\"2\">\n" + //
								"\t\t\t<div class=\"progress-bar-fill\"></div>\n" + //
								"\t\t</div>\n" + //
								"\n" + //
								"\t\t<!-- Pas tous le temps -->\n" + //
								"\t\t<div class=\"countdown\">\n" + //
								"\t\t\t<p class=\"countdown-title\">Temps restant :</p>\n" + //
								"\t\t\t<p class=\"countdown-data\">50</p>\n" + //
								"\t\t</div>\n" + //
								"\n" + //
								"\t\t<!-- Image pour la question -->\n" + //
								"\t\t<img src=\"../images/cheval.jpg\" alt=\"Cheval blanc d'Henri IV\" class=\"question-image\">\n" + //
								"\n" + //
								"\t\t<button type=\"button\" class=\"complementary-file-button\">Voir le fichier complémentaire</button>\n" + //
								"\n" + //
								"\t\t<div class=\"question\">\n" + //
								"\t\t\t<p class=\"question-text\">Quelle est la couleur du cheval blanc d'Henri IV ?</p>\n" + //
								"\t\t\t<div class=\"answers\">\n" + //
								"\t\t\t\t<div class=\"answer\">\n" + //
								"\t\t\t\t\t<input type=\"radio\" name=\"answer\" id=\"answer1\" value=\"answer1\">\n" + //
								"\t\t\t\t\t<label for=\"answer1\">Blanc</label>\n" + //
								"\t\t\t\t</div>\n" + //
								"\t\t\t\t<div class=\"answer\">\n" + //
								"\t\t\t\t\t<input type=\"radio\" name=\"answer\" id=\"answer2\" value=\"answer2\">\n" + //
								"\t\t\t\t\t<label for=\"answer2\">Noir</label>\n" + //
								"\t\t\t\t</div>\n" + //
								"\t\t\t\t<div class=\"answer\">\n" + //
								"\t\t\t\t\t<input type=\"radio\" name=\"answer\" id=\"answer3\" value=\"answer3\">\n" + //
								"\t\t\t\t\t<label for=\"answer3\">Rouge</label>\n" + //
								"\t\t\t\t</div>\n" + //
								"\t\t\t\t<div class=\"answer\">\n" + //
								"\t\t\t\t\t<input type=\"radio\" name=\"answer\" id=\"answer4\" value=\"answer4\">\n" + //
								"\t\t\t\t\t<label for=\"answer4\">Vert</label>\n" + //
								"\t\t\t\t</div>\n" + //
								"\t\t\t</div>\n" + //
								"\t\t</div>\n" + //
								"\n" + //
								"\t\t<!-- Bouton indice -->\n" + //
								"\t\t<div class=\"hint\">\n" + //
								"\t\t\t<button type=\"button\" class=\"hint-button\">Indice</button>\n" + //
								"\t\t\t<div class=\"hint-count\">\n" + //
								"\t\t\t\t<p>Indice : <span class=\"hint-current-data\">0</span>/<span class=\"hint-total-data\">2</span></p>\n" + //
								"\t\t\t</div>\n" + //
								"\t\t</div>\n" + //
								"\n" + //
								"\n" + //
								"\t\t<!-- Attention si l'évaluation est chronométrée il ne peut pas revenir en arrière et\n" + //
								"\t\t\til peut choisir de ne pas répondre à certaines d'entre elles -->\n" + //
								"\t\t<div class=\"nav\">\n" + //
								"\t\t\t<button type=\"button\" class=\"nav-button previous-button\">Précédent</button>\n" + //
								"\t\t\t<button type=\"button\" class=\"nav-button validate-button\">Valider</button>\n" + //
								"\t\t\t<button type=\"button\" class=\"nav-button feedback-button\">Feedback</button>\n" + //
								"\t\t\t<button type=\"button\" class=\"nav-button next-button\">Suivant</button>\n" + //
								"\t\t</div>\n" + //
								"\t</div>\n" + //
								"\t<!-- PopUp pour le feedback -->\n" + //
								"\t<div class=\"popup\">\n" + //
								"\t\t<div class=\"popup-content\">\n" + //
								"\t\t\t<button type=\"button\" class=\"close-button\">Fermer</button>\n" + //
								"\t\t\t<p class=\"feedback-title\">Réponse</p>\n" + //
								"\t\t\t<p class=\"feedback-text\">La réponse à la question est la suivante : \"Blanc\".</p>\n" + //
								"\t\t</div>\n" + //
								"\t</div>\n" + //
								"\n" + //
								"\t<script src=\"../script/popup.js\"></script>\n" + //
								"\t<script src=\"../script/index.js\"></script>\n" + //
								"\n" + //
								"\t<script>\n" + //
								"\t\tdocument.addEventListener(\"DOMContentLoaded\", function () {\n" + //
								"\t\t\tconst progressBars = document.querySelectorAll(\".progress-bar\");\n" + //
								"\n" + //
								"\t\t\tprogressBars.forEach(progressBar => {\n" + //
								"\t\t\t\tconst total = parseInt(progressBar.dataset.total, 10);\n" + //
								"\t\t\t\tconst current = parseInt(progressBar.dataset.current, 10);\n" + //
								"\t\t\t\tconst progressFill = progressBar.querySelector(\".progress-bar-fill\");\n" + //
								"\n" + //
								"\t\t\t\tif (total && current >= 0) {\n" + //
								"\t\t\t\t\tconst widthPercentage = (current / total) * 100;\n" + //
								"\t\t\t\t\tprogressFill.style.width = `${widthPercentage}%`;\n" + //
								"\t\t\t\t}\n" + //
								"\t\t\t});\n" + //
								"\t\t});\n" + //
								"\t</script>\n" + //
								"</body>\n" + //
								"</html>";
			question2Writer.println(sQuestion2);
			question2Writer.close();

			PrintWriter question3Writer = new PrintWriter("../questionnaire/pages/question3.html");
			String sQuestion3 = headerQuestion + //
								"<body value=\"3\">\n" + //
								"\t<div class=\"container\">\n" + //
								"\t\t<div class=\"informations\">\n" + //
								"\t\t\t<p class=\"question-number\">Question numéro : <span class=\"question-number-data\">3</span></p>\n" + //
								"\t\t\t<p class=\"notion\">Notion : <span class=\"notion-data\">Notion 2</span></p>\n" + //
								"\t\t\t<p class=\"difficulty\">Difficulté : <span class=\"difficulty-data\">Moyenne</span></p>\n" + //
								"\t\t\t<p class=\"estimated-time\">Temps estimé : <span class=\"estimated-time-data\">1min</span></p>\n" + //
								"\t\t</div>\n" + //
								"\n" + //
								"\t\t<!-- barre de progression des questions -->\n" + //
								"\t\t<div class=\"progress-bar\" data-total=\"4\" data-current=\"3\">\n" + //
								"\t\t\t<div class=\"progress-bar-fill\"></div>\n" + //
								"\t\t</div>\n" + //
								"\n" + //
								"\t\t<!-- Pas tous le temps -->\n" + //
								"\t\t<div class=\"countdown\">\n" + //
								"\t\t\t<p class=\"countdown-title\">Temps restant :</p>\n" + //
								"\t\t\t<p class=\"countdown-data\">60</p>\n" + //
								"\t\t</div>\n" + //
								"\n" + //
								"\t\t<!-- Image pour la question -->\n" + //
								"\t\t<img src=\"../images/drapeau_france.png\" alt=\"Exemple d'association\" class=\"question-image\">\n" + //
								"\n" + //
								"\t\t<button type=\"button\" class=\"complementary-file-button\">Voir le fichier complémentaire</button>\n" + //
								"\n" + //
								"\t\t<div class=\"question\">\n" + //
								"\t\t\t<p class=\"question-text\">Associez les éléments de la colonne de gauche avec ceux de la colonne de droite :</p>\n" + //
								"\t\t\t<div class=\"association-container\">\n" + //
								"\t\t\t\t<div class=\"association-column left-column\">\n" + //
								"\t\t\t\t\t<div class=\"association-item\" data-id=\"1\">Cheval</div>\n" + //
								"\t\t\t\t\t<div class=\"association-item\" data-id=\"2\"><img src=\"../images/cheval.jpg\" alt=\"Image 1\"></div>\n" + //
								"\t\t\t\t\t<div class=\"association-item\" data-id=\"3\">Vache</div>\n" + //
								"\t\t\t\t</div>\n" + //
								"\t\t\t\t<div class=\"association-column right-column\">\n" + //
								"\t\t\t\t\t<div class=\"association-item\" data-id=\"A\">Horse</div>\n" + //
								"\t\t\t\t\t<div class=\"association-item\" data-id=\"B\">Caballo</div>\n" + //
								"\t\t\t\t\t<div class=\"association-item\" data-id=\"C\"><img src=\"../images/cheval1.jpg\" alt=\"Image 2\"></div>\n" + //
								"\t\t\t\t\t<div class=\"association-item\" data-id=\"D\">Pferd</div>\n" + //
								"\t\t\t\t</div>\n" + //
								"\t\t\t</div>\n" + //
								"\t\t</div>\n" + //
								"\n" + //
								"\t\t<!-- Attention si l'évaluation est chronométrée il ne peut pas revenir en arrière et\n" + //
								"\t\t\til peut choisir de ne pas répondre à certaines d'entre elles -->\n" + //
								"\t\t<div class=\"nav\">\n" + //
								"\t\t\t<button type=\"button\" class=\"nav-button previous-button\">Précédent</button>\n" + //
								"\t\t\t<button type=\"button\" class=\"nav-button validate-button\">Valider</button>\n" + //
								"\t\t\t<button type=\"button\" class=\"nav-button feedback-button\">Feedback</button>\n" + //
								"\t\t\t<button type=\"button\" class=\"nav-button next-button\">Suivant</button>\n" + //
								"\t\t</div>\n" + //
								"\t</div>\n" + //
								"\t<!-- PopUp pour le feedback -->\n" + //
								"\t<div class=\"popup\">\n" + //
								"\t\t<div class=\"popup-content\">\n" + //
								"\t\t\t<button type=\"button\" class=\"close-button\">Fermer</button>\n" + //
								"\t\t\t<p class=\"feedback-title\">Réponse</p>\n" + //
								"\t\t\t<p class=\"feedback-text\">La réponse à la question est la suivante : \"Blanc\".</p>\n" + //
								"\t\t</div>\n" + //
								"\t</div>\n" + //
								"\n" + //
								"\t<script src=\"../script/popup.js\"></script>\n" + //
								"\t<script src=\"../script/index.js\"></script>\n" + //
								"\n" + //
								"\t<script>\n" + //
								"\t\tdocument.addEventListener(\"DOMContentLoaded\", function () {\n" + //
								"\t\t\tconst progressBars = document.querySelectorAll(\".progress-bar\");\n" + //
								"\n" + //
								"\t\t\tprogressBars.forEach(progressBar => {\n" + //
								"\t\t\t\tconst total = parseInt(progressBar.dataset.total, 10);\n" + //
								"\t\t\t\tconst current = parseInt(progressBar.dataset.current, 10);\n" + //
								"\t\t\t\tconst progressFill = progressBar.querySelector(\".progress-bar-fill\");\n" + //
								"\n" + //
								"\t\t\t\tif (total && current >= 0) {\n" + //
								"\t\t\t\t\tconst widthPercentage = (current / total) * 100;\n" + //
								"\t\t\t\t\tprogressFill.style.width = `${widthPercentage}%`;\n" + //
								"\t\t\t\t}\n" + //
								"\t\t\t});\n" + //
								"\t\t});\n" + //
								"\t</script>\n" + //
								"</body>\n" + //
								"</html>";
			question3Writer.println(sQuestion3);
			question3Writer.close();

			PrintWriter question4Writer = new PrintWriter("../questionnaire/pages/question4.html");
			String sQuestion4 = headerQuestion + //
								"<body value=\"4\">\n" + //
								"\t<div class=\"container question-unique\">\n" + //
								"\t\t<div class=\"informations\">\n" + //
								"\t\t\t<p class=\"question-number\">Question numéro : <span class=\"question-number-data\">4</span></p>\n" + //
								"\t\t\t<p class=\"notion\">Notion : <span class=\"notion-data\">Notion 1</span></p>\n" + //
								"\t\t\t<p class=\"difficulty\">Difficulté : <span class=\"difficulty-data\">Facile</span></p>\n" + //
								"\t\t\t<p class=\"estimated-time\">Temps estimé : <span class=\"estimated-time-data\">30sec</span></p>\n" + //
								"\t\t</div>\n" + //
								"\n" + //
								"\t\t<!-- barre de progression des questions -->\n" + //
								"\t\t<div class=\"progress-bar\" data-total=\"4\" data-current=\"4\">\n" + //
								"\t\t\t<div class=\"progress-bar-fill\"></div>\n" + //
								"\t\t</div>\n" + //
								"\n" + //
								"\t\t<!-- Pas tous le temps -->\n" + //
								"\t\t<div class=\"countdown\">\n" + //
								"\t\t\t<p class=\"countdown-title\">Temps restant :</p>\n" + //
								"\t\t\t<p class=\"countdown-data\">30</p>\n" + //
								"\t\t</div>\n" + //
								"\n" + //
								"\t\t<!-- Image pour la question -->\n" + //
								"\t\t<img src=\"../images/cheval.jpg\" alt=\"Cheval blanc d'Henri IV\" class=\"question-image\">\n" + //
								"\n" + //
								"\t\t<button type=\"button\" class=\"complementary-file-button\">Voir le fichier complémentaire</button>\n" + //
								"\n" + //
								"\t\t<div class=\"question\">\n" + //
								"\t\t\t<p class=\"question-text\">Quelle est la couleur du cheval blanc d'Henri IV ?</p>\n" + //
								"\t\t\t<div class=\"answers\">\n" + //
								"\t\t\t\t<div class=\"answer\">\n" + //
								"\t\t\t\t\t<input type=\"radio\" name=\"answer\" id=\"answer1\" value=\"answer1\">\n" + //
								"\t\t\t\t\t<label for=\"answer1\">Blanc</label>\n" + //
								"\t\t\t\t</div>\n" + //
								"\t\t\t\t<div class=\"answer\">\n" + //
								"\t\t\t\t\t<input type=\"radio\" name=\"answer\" id=\"answer2\" value=\"answer2\">\n" + //
								"\t\t\t\t\t<label for=\"answer2\">Noir</label>\n" + //
								"\t\t\t\t</div>\n" + //
								"\t\t\t\t<div class=\"answer\">\n" + //
								"\t\t\t\t\t<input type=\"radio\" name=\"answer\" id=\"answer3\" value=\"answer3\">\n" + //
								"\t\t\t\t\t<label for=\"answer3\">Rouge</label>\n" + //
								"\t\t\t\t</div>\n" + //
								"\t\t\t\t<div class=\"answer\">\n" + //
								"\t\t\t\t\t<input type=\"radio\" name=\"answer\" id=\"answer4\" value=\"answer4\">\n" + //
								"\t\t\t\t\t<label for=\"answer4\">Vert</label>\n" + //
								"\t\t\t\t</div>\n" + //
								"\t\t\t</div>\n" + //
								"\t\t</div>\n" + //
								"\n" + //
								"\t\t<!-- Attention si l'évaluation est chronométrée il ne peut pas revenir en arrière et\n" + //
								"\t\t\til peut choisir de ne pas répondre à certaines d'entre elles -->\n" + //
								"\t\t<div class=\"nav\">\n" + //
								"\t\t\t<button type=\"button\" class=\"nav-button previous-button\">Précédent</button>\n" + //
								"\t\t\t<button type=\"button\" class=\"nav-button validate-button\">Valider</button>\n" + //
								"\t\t\t<button type=\"button\" class=\"nav-button feedback-button\">Feedback</button>\n" + //
								"\t\t\t<button type=\"button\" class=\"nav-button next-button\">Suivant</button>\n" + //
								"\t\t</div>\n" + //
								"\t</div>\n" + //
								"\n" + //
								"\t<!-- PopUp pour le feedback -->\n" + //
								"\t<div class=\"popup\">\n" + //
								"\t\t<div class=\"popup-content\">\n" + //
								"\t\t\t<button type=\"button\" class=\"close-button\">Fermer</button>\n" + //
								"\t\t\t<p class=\"feedback-title\">Réponse</p>\n" + //
								"\t\t\t<p class=\"feedback-text\">La réponse à la question est la suivante : \"Blanc\".</p>\n" + //
								"\t\t</div>\n" + //
								"\t</div>\n" + //
								"\n" + //
								"\t<script src=\"../script/popup.js\"></script>\n" + //
								"\t<script src=\"../script/index.js\"></script>\n" + //
								"\n" + //
								"\t<script>\n" + //
								"\t\tdocument.addEventListener(\"DOMContentLoaded\", function () {\n" + //
								"\t\t\tconst progressBars = document.querySelectorAll(\".progress-bar\");\n" + //
								"\n" + //
								"\t\t\tprogressBars.forEach(progressBar => {\n" + //
								"\t\t\t\tconst total = parseInt(progressBar.dataset.total, 10);\n" + //
								"\t\t\t\tconst current = parseInt(progressBar.dataset.current, 10);\n" + //
								"\t\t\t\tconst progressFill = progressBar.querySelector(\".progress-bar-fill\");\n" + //
								"\n" + //
								"\t\t\t\tif (total && current >= 0) {\n" + //
								"\t\t\t\tconst widthPercentage = (current / total) * 100;\n" + //
								"\t\t\t\tprogressFill.style.width = `${widthPercentage}%`;\n" + //
								"\t\t\t\t}\n" + //
								"\t\t\t});\n" + //
								"\t\t});\n" + //
								"\t</script>\n" + //
								"</body>\n" + //
								"</html>";
			question4Writer.println(sQuestion4);
			question4Writer.close();

			// Create and write to script files

			PrintWriter indexJsWriter = new PrintWriter("../questionnaire/script/index.js");
			indexJsWriter.println("// TODO");
			indexJsWriter.close();

			PrintWriter popupJsWriter = new PrintWriter("../questionnaire/script/popup.js");
			String sPopup = "// Ouvrir et fermer la popup\n" + //
							"document.addEventListener(\"DOMContentLoaded\", function () {\n" + //
							"\tconst feedbackButton = document.querySelector(\".feedback-button\");\n" + //
							"\tconst popup = document.querySelector(\".popup\");\n" + //
							"\tconst closeButton = document.querySelector(\".close-button\");\n" + //
							"\n" + //
							"\n" + //
							"\tfeedbackButton.addEventListener(\"click\", function () {\n" + //
							"\t\tpopup.style.display = 'flex';\n" + //
							"\t});\n" + //
							"\n" + //
							"\tcloseButton.addEventListener(\"click\", function () {\n" + //
							"\t\tpopup.style.display = 'none';\n" + //
							"\t});\n" + //
							"\n" + //
							"\t// Fermer la popup en cliquant en dehors de la popup\n" + //
							"\twindow.addEventListener(\"click\", function (event) {\n" + //
							"\t\tif (event.target === popup) {\n" + //
							"\t\t\tpopup.style.display = 'none';\n" + //
							"\t\t}\n" + //
							"\t});\n" + //
							"});";
			popupJsWriter.println(sPopup);
			popupJsWriter.close();

			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		QCMBuilder   qcmBuilder  = new QCMBuilder();
		List<String> nomsNotions = new ArrayList<>();
		nomsNotions.add("Les types de bases");
		nomsNotions.add("Les tableaux à 1 dimension");
		nomsNotions.add("Les tableaux à deux dimensions");

		qcmBuilder.genererQuestionnaire("Initiation au développement", nomsNotions);
	}
}
