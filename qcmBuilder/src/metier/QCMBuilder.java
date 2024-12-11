package src.metier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import src.metier.question.*;
import src.metier.reponse.*;

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
			Scanner scanner = new Scanner(new File("./data/ressources_notions.csv"));
			scanner.nextLine();
			while (scanner.hasNextLine()) 
			{
				String   line         = scanner.nextLine();
				String[] parts        = line.split(";");
				String   nomRessource = parts[0];

				Ressource ressource = new Ressource(nomRessource);

				for (Ressource r : ressources)
				{
					if (r.getNom().equals(nomRessource))
						ressource = r;
				}

				if (!ressources.contains(ressource))
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
		if (ressource == null)
			return false;
		if (ressources.contains(ressource))
			return false;
		ressources.add(ressource);
		return true;
	}

	public boolean supprimerRessource(Ressource ressource)
	{
		if (ressource == null)
			return false;
		if (!ressources.contains(ressource))
			return false;
		ressources.remove(ressource);
		return true;
	}

	public Ressource rechercherRessource(String nom)
	{
		Ressource ressourceTrouvee = null;
		for (Ressource ressource : ressources)
		{
			if (ressource.getNom().equals(nom))
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
	
	public boolean creerQuestion(String type, String nomRessource, String nomNotion, String text, int timer, double nbPoint, int difficulte, List<String> sLstReponses, String explication)
	{

		Notion notion = rechercherRessource(nomRessource).rechercherNotion(nomNotion);
		
		if ( type.equals("Elimination") )
			//notion.ajouterQuestion(new Elimination());

		if ( type.equals("QCM") )
		{
			List<ReponseQCM> lstReponses = new ArrayList<>();
			for (String sReponse : sLstReponses)
			{
				String[] parts = sReponse.split("_");
				ReponseQCM reponse = new ReponseQCM(parts[1], parts[0]);
				lstReponses.add(reponse);
			}

			notion.ajouterQuestion(new QCM(notion, text, timer, nbPoint, difficulte, lstReponses, explication));
			return true;
		}

		if ( type.equals("Association"))
		{
			List<ReponseAssociation> lstReponse = new ArrayList<>();
			int ind = 1;
			for (String sRep : sLstReponses)
			{
				String textRep       = "";
				List<Integer> lstInd = new ArrayList<>();
				String textRep2      = "";
				
				if (!sRep.startsWith("[null]"))
				{
					if (sRep.contains("->"))
					{
						textRep  = sRep.substring(0, sRep.indexOf("->") - 2);

						String allInd = sRep.substring(sRep.indexOf("->" + 2), sRep.indexOf("///") - -1);
						String[] parts = allInd.split("_");
						for (String part : parts)
							lstInd.add(Integer.parseInt(part));
					}
					else
					{
						textRep = sRep.substring(0, sRep.indexOf("///") -1);
						lstInd = null;
					}
					
					lstReponse.add(new ReponseAssociation( textRep, lstInd, ind, true));
				}
				if ( !sRep.endsWith("[null]"))
				{
					textRep2 = sRep.substring(sRep.indexOf("///") + 3, sRep.length());
					lstReponse.add(new ReponseAssociation( textRep2, null, ind, false));
				}

				ind++;
			}
			notion.ajouterQuestion(new Association(notion, text, timer, nbPoint, difficulte, lstReponse, explication));
			return true;
		}
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

			String headerQuestion = "<!DOCTYPE html>" +
									"<html lang=\"fr\">" +
									"<head>" +
									"<meta charset=\"UTF-8\">" +
									"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
									"<link rel=\"stylesheet\" href=\"../css/styles.css\">" +
									"<title>Accueil Questionnaire</title>" +
									"</head>\n";

			String header = "<!DOCTYPE html>" +
							"<html lang=\"fr\">" +
							"<head>" +
							"<meta charset=\"UTF-8\">" +
							"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
							"<link rel=\"stylesheet\" href=\"styles.css\">" +
							"<title>Accueil Questionnaire</title>" +
							"</head>\n";

			PrintWriter indexWriter = new PrintWriter("../questionnaire/index.html");
			String sIndex =  header +
								"<body>" +
								"" +
								"<h1 class=\"evaluation-title\">Questionnaire "+ nomRessource +"</h1>" +
								"<div class=\"informations\">" +
								"<p class=\"estimated-time\">Temps estimé : <span class=\"estimated-time-data\">20h</span></p>" +
								"<p class=\"resource\">Ressources concernée : <span class=\"resource-data\">"+nomRessource+"</span></p>" +
								"<p class=\"notions-title\">Notion abordées :</p>" +
								"<ul class=\"notions-list\">\n";

								for (String nom : nomsNotions)
									sIndex+="<li class=\"notion-item\"><span class=\"notions-item-data\">"+ nom +"</span></li>\n";

								sIndex+="</ul>" +

								"<p class=\"questions-count\">Nombre de questions : <span class=\"questions-count-data\">10</span></p>" +
								"<ul class=\"difficulty-list\">" +
								"<li class=\"difficulty-item very-easy\">Très facile : <span class=\"difficulty-item-data\">0</span></li>" +
								"<li class=\"difficulty-item easy\">     Facile :      <span class=\"difficulty-item-data\">3</span></li>" +
								"<li class=\"difficulty-item medium\">   Moyenne :     <span class=\"difficulty-item-data\">4</span></li>" +
								"<li class=\"difficulty-item hard\">     Difficile :   <span class=\"difficulty-item-data\">3</span></li>" +
								"</ul>" +
								"<button type=\"button\" class=\"start-button\">Commencer</button>" +
								"</div>" +
								"<script src=\"script/index.js\"></script>" +
								"</body>" +
								"</html>";
			indexWriter.println(sIndex);
			indexWriter.close();

			PrintWriter finWriter = new PrintWriter("../questionnaire/fin.html");
			String sFin = header +
							"<body>" +
							"" +
							"<h1 class=\"evaluation-title\">Fin du questionnaire</h1>" +
							"<div class=\"informations\">" +
							"<p class=\"estimated-time\">Temps estimé : <span class=\"estimated-time-data\">20h</span></p>" +
							"<p class=\"resource\">Ressources concernée : <span class=\"resource-data\">r1.01</span></p>" +
							"<p class=\"notions-title\">Notion abordées :</p>" +
							"<ul class=\"notions-list\">" +
							"<li class=\"notion-item\"><span class=\"notions-item-data\">Notion 1</span></li>" +
							"<li class=\"notion-item\"><span class=\"notions-item-data\">Notion 2</span></li>" +
							"<li class=\"notion-item\"><span class=\"notions-item-data\">Notion 3</span></li>" +
							"</ul>" +
							"<p class=\"questions-count\">Nombre de questions : <span class=\"questions-count-data\">10</span></p>" +
							"<ul class=\"difficulty-list\">" +
							"<li class=\"difficulty-item very-easy\">Très facile : <span class=\"difficulty-item-data\">0</span></li>" +
							"<li class=\"difficulty-item easy\">     Facile :      <span class=\"difficulty-item-data\">3</span></li>" +
							"<li class=\"difficulty-item medium\">   Moyenne :     <span class=\"difficulty-item-data\">4</span></li>" +
							"<li class=\"difficulty-item hard\">     Difficile :   <span class=\"difficulty-item-data\">3</span></li>" +
							"</ul>" +
							"<!-- Score -->" +
							"<p class=\"score\">Votre score : <span class=\"score-data\">8/10</span></p>" +
							"<!-- Bouton recommencer -->" +
							"<button type=\"button\" class=\"restart-button\" onclick=\"window.location.href=\'index.html\'\">Recommencer</button>" +
							"</div>" +
							"</body>" +
							"</html>";
			finWriter.println(sFin);
			finWriter.close();


			PrintWriter stylesCss = new PrintWriter("../questionnaire/styles.css");
			String sStyles = "body {" +
							"font-family: Arial, sans-serif;" +
							"background-color: #f4f4f9;" +
							"color: #333;" +
							"margin: 0;" +
							"padding: 20px;" +
							"}" +
							"h1.evaluation-title {" +
							"color: #395cce;" +
							"text-align: center;" +
							"margin-bottom: 20px;" +
							"font-size: 3em;" +
							"}" +
							".informations {" +
							"background-color: #fff;" +
							"border: 1px solid #ddd;" +
							"border-radius: 10px;" +
							"padding: 20px;" +
							"box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);" +
							"max-width: 600px;" +
							"margin: 20px auto;" +
							"}" +
							"p.estimated-time," +
							"p.resource," +
							"p.notions-title," +
							"p.questions-count," +
							"p.score {" +
							"font-size: 1.2em;" +
							"margin: 10px 0;" +
							"}" +
							"ul.notions-list," +
							"ul.difficulty-list {" +
							"list-style-type: none;" +
							"padding: 0;" +
							"margin-left: 5%;" +
							"margin-right: 10%;" +
							"word-wrap: break-word;" +
							"}" +
							"ul.notions-list li.notion-item," +
							"ul.difficulty-list li.difficulty-item {" +
							"background-color: #fff;" +
							"border: 1px solid #ddd;" +
							"border-radius: 5px;" +
							"padding: 10px;" +
							"margin: 5px 0;" +
							"}" +
							"ul.difficulty-list li.difficulty-item.very-easy {" +
							"border-left: 5px solid #8BC34A;" +
							"}" +
							"ul.difficulty-list li.difficulty-item.easy {" +
							"border-left: 5px solid #4CAF50;" +
							"}" +
							"ul.difficulty-list li.difficulty-item.medium {" +
							"border-left: 5px solid #FFEB3B;" +
							"}" +
							"ul.difficulty-list li.difficulty-item.hard {" +
							"border-left: 5px solid #FF5722;" +
							"}" +
							"span.estimated-time-data," +
							"span.resource-data," +
							"span.notions-item-data," +
							"span.questions-count-data," +
							"span.difficulty-item-data," +
							"span.score-data {" +
							"font-weight: bold;" +
							"}" +
							"p.score {" +
							"font-size: 2em;" +
							"text-align: center;" +
							"margin-top: 20px;" +
							"}" +
							".start-button," +
							".restart-button {" +
							"display: block;" +
							"width: 200px;" +
							"margin: 20px auto;" +
							"padding: 10px 20px;" +
							"background-color: #395cce;" +
							"color: white;" +
							"text-align: center;" +
							"border: none;" +
							"border-radius: 5px;" +
							"cursor: pointer;" +
							"font-size: 1.2em;" +
							"box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);" +
							"transition: background-color 0.3s ease;" +
							"}" +
							".start-button:hover," +
							".restart-button:hover {" +
							"background-color: #4772ff;" +
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
			String sQuestion1 = headerQuestion +
								"<body value=\"1\">" +
								"<div class=\"container question-multiple\">" +
								"<div class=\"informations\">" +
								"<p class=\"question-number\">Question numéro : <span class=\"question-number-data\">1</span></p>" +
								"<p class=\"notion\">Notion : <span class=\"notion-data\">Notion 1</span></p>" +
								"<p class=\"difficulty\">Difficulté : <span class=\"difficulty-data\">Facile</span></p>" +
								"<p class=\"estimated-time\">Temps estimé : <span class=\"estimated-time-data\">30sec</span></p>" +
								"</div>" +
								"<!-- barre de progression des questions -->" +
								"<div class=\"progress-bar\" data-total=\"4\" data-current=\"1\">" +
								"<div class=\"progress-bar-fill\"></div>" +
								"</div>" +
								"<!-- Pas tous le temps -->" +
								"<div class=\"countdown\">" +
								"<p class=\"countdown-title\">Temps restant :</p>" +
								"<p class=\"countdown-data\">50000</p>" +
								"</div>" +
								"<!-- Image pour la question -->" +
								"<img src=\"../images/drapeau_france.png\" alt=\"rgpd\" class=\"question-image\">" +
								"<button type=\"button\" class=\"complementary-file-button\">Voir le fichier complémentaire</button>" +
								"<di class=\"question\">" +
								"<p class=\"question-text\">Quels sont les couleurs du drapeau français ?</p>" +
								"<div class=\"answers\">" +
								"<div class=\"answer\">" +
								"<input type=\"checkbox\" name=\"answer\" id=\"answer1\" value=\"answer3\">" +
								"<label for=\"answer1\">Rouge</label>" +
								"</div>" +
								"<div class=\"answer\">" +
								"<input type=\"checkbox\" name=\"answer\" id=\"answer2\" value=\"answer5\">" +
								"<label for=\"answer2\">Jaune</label>" +
								"</div>" +
								"<div class=\"answer\">" +
								"<input type=\"checkbox\" name=\"answer\" id=\"answer3\" value=\"answer1\">" +
								"<label for=\"answer3\">Bleu</label>" +
								"</div>" +
								"<div class=\"answer\">" +
								"<input type=\"checkbox\" name=\"answer\" id=\"answer4\" value=\"answer4\">" +
								"<label for=\"answer4\">Vert</label>" +
								"</div>" +
								"<div class=\"answer\">" +
								"<input type=\"checkbox\" name=\"answer\" id=\"answer5\" value=\"answer2\">" +
								"<label for=\"answer5\">Blanc</label>" +
								"</div>" +
								"</div>" +
								"</div>" +
								"<!-- Attention si l\'évaluation est chronométrée il ne peut pas revenir en arrière et" +
								"il peut choisir de ne pas répondre à certaines d\'entre elles -->" +
								"<div class=\"nav\">" +
								"<button type=\"button\" class=\"nav-button previous-button\">Précédent</button>" +
								"<button type=\"button\" class=\"nav-button validate-button\">Valider</button>" +
								"<button type=\"button\" class=\"nav-button feedback-button\">Feedback</button>" +
								"<button type=\"button\" class=\"nav-button next-button\">Suivant</button>" +
								"</div>" +
								"</div>" +
								"<!-- PopUp pour le feedback -->" +
								"<div class=\"popup\">" +
								"<div class=\"popup-content\">" +
								"<button type=\"button\" class=\"close-button\">Fermer</button>" +
								"<p class=\"feedback-title\">Réponse</p>" +
								"<p class=\"feedback-text\">Et oui ! Le drapeau français est BLEU BLANC et ROUGE.</p>" +
								"</div>" +
								"</div>" +
								"<script src=\"../script/popup.js\"></script>" +
								"<script src=\"../script/index.js\"></script>" +
								"<script>" +
								"document.addEventListener(\"DOMContentLoaded\", function () {" +
								"const progressBars = document.querySelectorAll(\".progress-bar\");" +
								"progressBars.forEach(progressBar => {" +
								"const total = parseInt(progressBar.dataset.total, 10);" +
								"const current = parseInt(progressBar.dataset.current, 10);" +
								"const progressFill = progressBar.querySelector(\".progress-bar-fill\");" +
								"if (total && current >= 0) {" +
								"const widthPercentage = (current / total) * 100;" +
								"progressFill.style.width = `${widthPercentage}%`;" +
								"}" +
								"});" +
								"});" +
								"</script>" +
								"</body>" +
								"</html>";
			question1Writer.println(sQuestion1);
			question1Writer.close();

			PrintWriter question2Writer = new PrintWriter("../questionnaire/pages/question2.html");
			String sQuestion2 = headerQuestion +
								"<body value=\"2\">" +
								"<div class=\"container eliminate-question\">" +
								"<div class=\"informations\">" +
								"<p class=\"question-number\">Question numéro : <span class=\"question-number-data\">2</span></p>" +
								"<p class=\"notion\">Notion : <span class=\"notion-data\">Notion 1</span></p>" +
								"<p class=\"difficulty\">Difficulté : <span class=\"difficulty-data\">Facile</span></p>" +
								"<p class=\"estimated-time\">Temps estimé : <span class=\"estimated-time-data\">30sec</span></p>" +
								"</div>" +
								"<!-- barre de progression des questions -->" +
								"<div class=\"progress-bar\" data-total=\"4\" data-current=\"2\">" +
								"<div class=\"progress-bar-fill\"></div>" +
								"</div>" +
								"<!-- Pas tous le temps -->" +
								"<div class=\"countdown\">" +
								"<p class=\"countdown-title\">Temps restant :</p>" +
								"<p class=\"countdown-data\">50</p>" +
								"</div>" +
								"<!-- Image pour la question -->" +
								"<img src=\"../images/cheval.jpg\" alt=\"Cheval blanc d\'Henri IV\" class=\"question-image\">" +
								"<button type=\"button\" class=\"complementary-file-button\">Voir le fichier complémentaire</button>" +
								"<div class=\"question\">" +
								"<p class=\"question-text\">Quelle est la couleur du cheval blanc d\'Henri IV ?</p>" +
								"<div class=\"answers\">" +
								"<div class=\"answer\">" +
								"<input type=\"radio\" name=\"answer\" id=\"answer1\" value=\"answer1\">" +
								"<label for=\"answer1\">Blanc</label>" +
								"</div>" +
								"<div class=\"answer\">" +
								"<input type=\"radio\" name=\"answer\" id=\"answer2\" value=\"answer2\">" +
								"<label for=\"answer2\">Noir</label>" +
								"</div>" +
								"<div class=\"answer\">" +
								"<input type=\"radio\" name=\"answer\" id=\"answer3\" value=\"answer3\">" +
								"<label for=\"answer3\">Rouge</label>" +
								"</div>" +
								"<div class=\"answer\">" +
								"<input type=\"radio\" name=\"answer\" id=\"answer4\" value=\"answer4\">" +
								"<label for=\"answer4\">Vert</label>" +
								"</div>" +
								"</div>" +
								"</div>" +
								"<!-- Bouton indice -->" +
								"<div class=\"hint\">" +
								"<button type=\"button\" class=\"hint-button\">Indice</button>" +
								"<div class=\"hint-count\">" +
								"<p>Indice : <span class=\"hint-current-data\">0</span>/<span class=\"hint-total-data\">2</span></p>" +
								"</div>" +
								"</div>" +
								"<!-- Attention si l\'évaluation est chronométrée il ne peut pas revenir en arrière et" +
								"il peut choisir de ne pas répondre à certaines d\'entre elles -->" +
								"<div class=\"nav\">" +
								"<button type=\"button\" class=\"nav-button previous-button\">Précédent</button>" +
								"<button type=\"button\" class=\"nav-button validate-button\">Valider</button>" +
								"<button type=\"button\" class=\"nav-button feedback-button\">Feedback</button>" +
								"<button type=\"button\" class=\"nav-button next-button\">Suivant</button>" +
								"</div>" +
								"</div>" +
								"<!-- PopUp pour le feedback -->" +
								"<div class=\"popup\">" +
								"<div class=\"popup-content\">" +
								"<button type=\"button\" class=\"close-button\">Fermer</button>" +
								"<p class=\"feedback-title\">Réponse</p>" +
								"<p class=\"feedback-text\">La réponse à la question est la suivante : \"Blanc\".</p>" +
								"</div>" +
								"</div>" +
								"<script src=\"../script/popup.js\"></script>" +
								"<script src=\"../script/index.js\"></script>" +
								"<script>" +
								"document.addEventListener(\"DOMContentLoaded\", function () {" +
								"const progressBars = document.querySelectorAll(\".progress-bar\");" +
								"progressBars.forEach(progressBar => {" +
								"const total = parseInt(progressBar.dataset.total, 10);" +
								"const current = parseInt(progressBar.dataset.current, 10);" +
								"const progressFill = progressBar.querySelector(\".progress-bar-fill\");" +
								"if (total && current >= 0) {" +
								"const widthPercentage = (current / total) * 100;" +
								"progressFill.style.width = `${widthPercentage}%`;" +
								"}" +
								"});" +
								"});" +
								"</script>" +
								"</body>" +
								"</html>";
			question2Writer.println(sQuestion2);
			question2Writer.close();

			PrintWriter question3Writer = new PrintWriter("../questionnaire/pages/question3.html");
			String sQuestion3 = headerQuestion +
								"<body value=\"3\">" +
								"<div class=\"container\">" +
								"<div class=\"informations\">" +
								"<p class=\"question-number\">Question numéro : <span class=\"question-number-data\">3</span></p>" +
								"<p class=\"notion\">Notion : <span class=\"notion-data\">Notion 2</span></p>" +
								"<p class=\"difficulty\">Difficulté : <span class=\"difficulty-data\">Moyenne</span></p>" +
								"<p class=\"estimated-time\">Temps estimé : <span class=\"estimated-time-data\">1min</span></p>" +
								"</div>" +
								"<!-- barre de progression des questions -->" +
								"<div class=\"progress-bar\" data-total=\"4\" data-current=\"3\">" +
								"<div class=\"progress-bar-fill\"></div>" +
								"</div>" +
								"<!-- Pas tous le temps -->" +
								"<div class=\"countdown\">" +
								"<p class=\"countdown-title\">Temps restant :</p>" +
								"<p class=\"countdown-data\">60</p>" +
								"</div>" +
								"<!-- Image pour la question -->" +
								"<img src=\"../images/drapeau_france.png\" alt=\"Exemple d\'association\" class=\"question-image\">" +
								"<button type=\"button\" class=\"complementary-file-button\">Voir le fichier complémentaire</button>" +
								"<div class=\"question\">" +
								"<p class=\"question-text\">Associez les éléments de la colonne de gauche avec ceux de la colonne de droite :</p>" +
								"<div class=\"association-container\">" +
								"<div class=\"association-column left-column\">" +
								"<div class=\"association-item\" data-id=\"1\">Cheval</div>" +
								"<div class=\"association-item\" data-id=\"2\"><img src=\"../images/cheval.jpg\" alt=\"Image 1\"></div>" +
								"<div class=\"association-item\" data-id=\"3\">Vache</div>" +
								"</div>" +
								"<div class=\"association-column right-column\">" +
								"<div class=\"association-item\" data-id=\"A\">Horse</div>" +
								"<div class=\"association-item\" data-id=\"B\">Caballo</div>" +
								"<div class=\"association-item\" data-id=\"C\"><img src=\"../images/cheval1.jpg\" alt=\"Image 2\"></div>" +
								"<div class=\"association-item\" data-id=\"D\">Pferd</div>" +
								"</div>" +
								"</div>" +
								"</div>" +
								"<!-- Attention si l\'évaluation est chronométrée il ne peut pas revenir en arrière et" +
								"il peut choisir de ne pas répondre à certaines d\'entre elles -->" +
								"<div class=\"nav\">" +
								"<button type=\"button\" class=\"nav-button previous-button\">Précédent</button>" +
								"<button type=\"button\" class=\"nav-button validate-button\">Valider</button>" +
								"<button type=\"button\" class=\"nav-button feedback-button\">Feedback</button>" +
								"<button type=\"button\" class=\"nav-button next-button\">Suivant</button>" +
								"</div>" +
								"</div>" +
								"<!-- PopUp pour le feedback -->" +
								"<div class=\"popup\">" +
								"<div class=\"popup-content\">" +
								"<button type=\"button\" class=\"close-button\">Fermer</button>" +
								"<p class=\"feedback-title\">Réponse</p>" +
								"<p class=\"feedback-text\">La réponse à la question est la suivante : \"Blanc\".</p>" +
								"</div>" +
								"</div>" +
								"<script src=\"../script/popup.js\"></script>" +
								"<script src=\"../script/index.js\"></script>" +
								"<script>" +
								"document.addEventListener(\"DOMContentLoaded\", function () {" +
								"const progressBars = document.querySelectorAll(\".progress-bar\");" +
								"progressBars.forEach(progressBar => {" +
								"const total = parseInt(progressBar.dataset.total, 10);" +
								"const current = parseInt(progressBar.dataset.current, 10);" +
								"const progressFill = progressBar.querySelector(\".progress-bar-fill\");" +
								"if (total && current >= 0) {" +
								"const widthPercentage = (current / total) * 100;" +
								"progressFill.style.width = `${widthPercentage}%`;" +
								"}" +
								"});" +
								"});" +
								"</script>" +
								"</body>" +
								"</html>";
			question3Writer.println(sQuestion3);
			question3Writer.close();

			PrintWriter question4Writer = new PrintWriter("../questionnaire/pages/question4.html");
			String sQuestion4 = headerQuestion +
								"<body value=\"4\">" +
								"<div class=\"container question-unique\">" +
								"<div class=\"informations\">" +
								"<p class=\"question-number\">Question numéro : <span class=\"question-number-data\">4</span></p>" +
								"<p class=\"notion\">Notion : <span class=\"notion-data\">Notion 1</span></p>" +
								"<p class=\"difficulty\">Difficulté : <span class=\"difficulty-data\">Facile</span></p>" +
								"<p class=\"estimated-time\">Temps estimé : <span class=\"estimated-time-data\">30sec</span></p>" +
								"</div>" +
								"<!-- barre de progression des questions -->" +
								"<div class=\"progress-bar\" data-total=\"4\" data-current=\"4\">" +
								"<div class=\"progress-bar-fill\"></div>" +
								"</div>" +
								"<!-- Pas tous le temps -->" +
								"<div class=\"countdown\">" +
								"<p class=\"countdown-title\">Temps restant :</p>" +
								"<p class=\"countdown-data\">30</p>" +
								"</div>" +
								"<!-- Image pour la question -->" +
								"<img src=\"../images/cheval.jpg\" alt=\"Cheval blanc d\'Henri IV\" class=\"question-image\">" +
								"<button type=\"button\" class=\"complementary-file-button\">Voir le fichier complémentaire</button>" +
								"<div class=\"question\">" +
								"<p class=\"question-text\">Quelle est la couleur du cheval blanc d\'Henri IV ?</p>" +
								"<div class=\"answers\">" +
								"<div class=\"answer\">" +
								"<input type=\"radio\" name=\"answer\" id=\"answer1\" value=\"answer1\">" +
								"<label for=\"answer1\">Blanc</label>" +
								"</div>" +
								"<div class=\"answer\">" +
								"<input type=\"radio\" name=\"answer\" id=\"answer2\" value=\"answer2\">" +
								"<label for=\"answer2\">Noir</label>" +
								"</div>" +
								"<div class=\"answer\">" +
								"<input type=\"radio\" name=\"answer\" id=\"answer3\" value=\"answer3\">" +
								"<label for=\"answer3\">Rouge</label>" +
								"</div>" +
								"<div class=\"answer\">" +
								"<input type=\"radio\" name=\"answer\" id=\"answer4\" value=\"answer4\">" +
								"<label for=\"answer4\">Vert</label>" +
								"</div>" +
								"</div>" +
								"</div>" +
								"<!-- Attention si l\'évaluation est chronométrée il ne peut pas revenir en arrière et" +
								"il peut choisir de ne pas répondre à certaines d\'entre elles -->" +
								"<div class=\"nav\">" +
								"<button type=\"button\" class=\"nav-button previous-button\">Précédent</button>" +
								"<button type=\"button\" class=\"nav-button validate-button\">Valider</button>" +
								"<button type=\"button\" class=\"nav-button feedback-button\">Feedback</button>" +
								"<button type=\"button\" class=\"nav-button next-button\">Suivant</button>" +
								"</div>" +
								"</div>" +
								"<!-- PopUp pour le feedback -->" +
								"<div class=\"popup\">" +
								"<div class=\"popup-content\">" +
								"<button type=\"button\" class=\"close-button\">Fermer</button>" +
								"<p class=\"feedback-title\">Réponse</p>" +
								"<p class=\"feedback-text\">La réponse à la question est la suivante : \"Blanc\".</p>" +
								"</div>" +
								"</div>" +
								"<script src=\"../script/popup.js\"></script>" +
								"<script src=\"../script/index.js\"></script>" +
								"<script>" +
								"document.addEventListener(\"DOMContentLoaded\", function () {" +
								"const progressBars = document.querySelectorAll(\".progress-bar\");" +
								"progressBars.forEach(progressBar => {" +
								"const total = parseInt(progressBar.dataset.total, 10);" +
								"const current = parseInt(progressBar.dataset.current, 10);" +
								"const progressFill = progressBar.querySelector(\".progress-bar-fill\");" +
								"if (total && current >= 0) {" +
								"const widthPercentage = (current / total) * 100;" +
								"progressFill.style.width = `${widthPercentage}%`;" +
								"}" +
								"});" +
								"});" +
								"</script>" +
								"</body>" +
								"</html>";
			question4Writer.println(sQuestion4);
			question4Writer.close();

			// Create and write to script files

			PrintWriter indexJsWriter = new PrintWriter("../questionnaire/script/index.js");
			indexJsWriter.println("// TODO");
			indexJsWriter.close();

			PrintWriter popupJsWriter = new PrintWriter("../questionnaire/script/popup.js");
			String sPopup = "// Ouvrir et fermer la popup" +
							"document.addEventListener(\"DOMContentLoaded\", function () {" +
							"const feedbackButton = document.querySelector(\".feedback-button\");" +
							"const popup = document.querySelector(\".popup\");" +
							"const closeButton = document.querySelector(\".close-button\");" +
							"feedbackButton.addEventListener(\"click\", function () {" +
							"popup.style.display = \'flex\';" +
							"});" +
							"closeButton.addEventListener(\"click\", function () {" +
							"popup.style.display = \'none\';" +
							"});" +
							"// Fermer la popup en cliquant en dehors de la popup" +
							"window.addEventListener(\"click\", function (event) {" +
							"if (event.target === popup) {" +
							"popup.style.display = \'none\';" +
							"}" +
							"});" +
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
