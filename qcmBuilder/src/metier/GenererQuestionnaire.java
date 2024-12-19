package src.metier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import src.metier.question.Question;


public class GenererQuestionnaire 
{
	public static void main(String[] args)
	{
		GenererQuestionnaire gq = new GenererQuestionnaire("R1.01_Apagnan",true,"questionnaire");
	}

	private String nomRessource;
	private String script;

	public GenererQuestionnaire (String nomRessource, boolean chrono, String nomQuestionnaire)
	{
		this.nomRessource = nomRessource;
		this.script = "";

		try {
			String tempNomQuestionnaire = nomQuestionnaire;
			boolean exist = Files.exists(Paths.get("../" + tempNomQuestionnaire));
			int i=1;
			while(exist)
			{
				tempNomQuestionnaire = nomQuestionnaire;
				tempNomQuestionnaire += "("+ i++ +")";
				exist = Files.exists(Paths.get("../" + tempNomQuestionnaire));
			}
			

			Files.createDirectories(Paths.get("../"+tempNomQuestionnaire                       ));
			Files.createDirectories(Paths.get("../"+tempNomQuestionnaire+"/css"                   ));
			Files.createDirectories(Paths.get("../"+tempNomQuestionnaire+"/fichier_complementaire"));
			Files.createDirectories(Paths.get("../"+tempNomQuestionnaire+"/pages"                 ));
			Files.createDirectories(Paths.get("../"+tempNomQuestionnaire+"/script"                ));

			Files.copy(Paths.get("../data/web/css/index.css"       ),
					   Paths.get("../"+tempNomQuestionnaire+"/css/index.css"  ),
					   StandardCopyOption.REPLACE_EXISTING);

			Files.copy(Paths.get("../data/web/css/qcm_multiple.css"        ),
					   Paths.get("../"+tempNomQuestionnaire+"/css/qcm_multiple.css"   ),
					   StandardCopyOption.REPLACE_EXISTING);

			Files.copy(Paths.get("../data/web/css/qcm_unique.css"     ),
					   Paths.get("../"+tempNomQuestionnaire+"/css/qcm_unique.css"),
					   StandardCopyOption.REPLACE_EXISTING);

			Files.copy(Paths.get("../data/web/css/question_association.css"     ),
					   Paths.get("../"+tempNomQuestionnaire+"/css/question_association.css"),
					   StandardCopyOption.REPLACE_EXISTING);

			Files.copy(Paths.get("../data/web/css/question_elimination.css"         ),
					   Paths.get("../"+tempNomQuestionnaire+"/css/question_elimination.css"),
					   StandardCopyOption.REPLACE_EXISTING);

			if (chrono) {
				this.script = "<script src=\"./script/indexTimed.js\"></script>";
				Files.copy(Paths.get("../data/web/script/indexTimed.js"       ),
						   Paths.get("../"+nomQuestionnaire+"/script/indexTimed.js"  ),
						   StandardCopyOption.REPLACE_EXISTING);
			} else {
				this.script = "<script src=\"./script/indexNoTimed.js\"></script>";
				Files.copy(Paths.get("../data/web/script/indexNoTimed.js"       ),
						   Paths.get("../"+nomQuestionnaire+"/script/indexNoTimed.js"  ),
						   StandardCopyOption.REPLACE_EXISTING);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getDataJs(int nbQuestion, List<Question> lstQuestions)
	{
		return "// ...existing code...\n" + //
				"// Nombre total de questions dans le questionnaire\n" + //
				"const totalQuestions = " + nbQuestion +";\n" + //
				"\n" + //
				"// Index de la question actuelle\n" + //
				"let currentQuestion = 0;\n" + //
				"\n" + //
				"// Identifiant de l'intervalle de temps\n" + //
				"let intervalId = 0;\n" + //
				"\n" + //
				"// Nombre d'indices utilisés\n" + //
				"let usedHint  = 0;\n" + //
				"\n" + //
				"// Nombre de points perdus en utilisant des indices\n" + //
				"let hintPoint = 0;\n" + //
				"\n" + //
				"// Tableau des éléments associés\n" + //
				"let associateItems = [];\n" + //
				"\n" + //
				"// Points accumulés par l'élève\n" + //
				"let point = 0;\n" + //
				"\n" + //
				"// Somme des points de chaque question\n" + //
				"let totalPoint = 0;\n" + //
				"\n" + //
				"// Tableau pour stocker les réponses de chaques questions\n" + //
				"const correctAnswers = [\n" + //
				"\n" + ""; //
				//"\t// Question 1 à choix multiple\n" + //
				//"\t//[\"Bonne réponse 1\", \"Bonne réponse 2\", \"Bonne réponse 3\"],\n" + //
				//"\n" + //
				//"\t[\"answer1\", \"answer3\", \"answer5\"],\n" + //
				//"\n" + //
				//"\n" + //
				//"\t// Question 2 à élimination\n" + //
				//"\t//[[\"Bonne réponse\"],[\n" + //
				//"\t//\t[[\"indice1\"], point-perdu],\n" + //
				//"\t//\t[[\"indice2\"], point-perdu]]\n" + //
				//"\t//],\n" + //
				//"\n" + //
				//"\t[[\"answer1\"],[ \n" + //
				//"\t\t[[\"answer3\"],1.5],\n" + //
				//"\t\t[[\"answer4\"],4] ] \n" + //
				//"\t],\n" + //
				//"\n" + //
				//"\n" + //
				//"\t// Question 3 avec association\n" + //
				//"\t//[\n" + //
				//"\t//\t[\"Gauche1\",\"DroiteB\"],\n" + //
				//"\t//\t[\"Gauche2\",\"DroiteC\"],\n" + //
				//"\t//\t[\"Gauche3\",\"DroiteA\"],\n" + //
				//"\t//\t[\"Gauche4\",\"DroiteD\"]\n" + //
				//"\t//],\n" + //
				//"\n" + //
				//"\t[\n" + //
				//"\t\t[\"1\",\"B\"],\n" + //
				//"\t\t[\"2\",\"C\"],\n" + //
				//"\t\t[\"3\",\"A\"],\n" + //
				//"\t\t[\"4\",\"D\"]\n" + //
				//"\t],\n" + //
				//"\n" + //
				//"\t\n" + //
				//"\t// Question 4 à choix unique\n" + //
				//"\t//[\"Bonne réponse\"],\n" + //
				//"\n" + //
				//"\t[\"answer1\"]\n" + //
				//"\n" + //
				//"];";
	}

	private String getIndexHtml()
	{
		return "<!DOCTYPE html>\n" + //
				"<html lang=\"fr\">\n" + //
				"\t<head>\n" + //
				"\t\t<meta charset=\"UTF-8\">\n" + //
				"\t\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" + //
				"\t\t<link rel=\"stylesheet\" href=\"./css/index.css\">\n" + //
				"\t\t<title>Accueil Questionnaire</title>\n" + //
				"\t</head>\n" + //
				"\t\t<!-- Précise que c'est l'index -->\n" + //
				"\t\t<body class=\"index\" value=\"0\">\n" + //
				"\n" + //
				"\t\t<!-- Titre du questionnaire -->\n" + //
				"\t\t<h1 class=\"evaluation-title\">Questionnaire QCMBuilder</h1>\n" + //
				"\n" + //
				"\t\t<!-- Informations du quesionnaire -->\n" + //
				"\t\t<section class=\"informations\">\n" + //
				//"\t\t\t<p class=\"estimated-time\">Temps estimé :         <span class=\"estimated-time-data\">20h  </span></p>\n" + //
				"\t\t\t<p class=\"resource\"      >Ressources concernée : <span class=\"resource-data\"      >"+nomRessource+"</span></p>\n" + //
				"\t\t\t<p class=\"chrono\"        >Chronomètre :          <span class=\"chrono-data\"        >     </span></p>\n" + //
				"\n" + //
				"\t\t\t<!-- Tableau du nombre de question par difficulté et notions -->\n" + //
				"\t\t\t<table class=\"difficulty-table\">\n" + //
				"\t\t\t\t<thead>\n" + //
				"\t\t\t\t\t<tr>\n" + //
				"\t\t\t\t\t\t<!-- L'entête du tableau -->\n" + //
				"\t\t\t\t\t\t<th>Notions / Difficultés</th>\n" + //
				"\t\t\t\t\t\t\t<th class=\"very-easy\">Très facile</th>\n" + //
				"\t\t\t\t\t\t\t<th class=\"easy\">Facile</th>\n" + //
				"\t\t\t\t\t\t\t<th class=\"medium\">Moyenne</th>\n" + //
				"\t\t\t\t\t\t\t<th class=\"hard\">Difficile</th>\n" + //
				"\t\t\t\t\t\t\t<th>Total</th>\n" + //
				"\t\t\t\t\t</tr>\n" + //
				"\t\t\t\t</thead>\n" + //
				"\t\t\t\t<tbody> <!-- Les données en fonction des notions -->\n" + //
				//"\t\t\t\t\t<tr>\n" + //
				//"\t\t\t\t\t\t<td>Notion 1</td>\n" + //
				//"\t\t\t\t\t\t<td class=\"difficulty-item-data\">0</td>\n" + //
				//"\t\t\t\t\t\t<td class=\"difficulty-item-data\">1</td>\n" + //
				//"\t\t\t\t\t\t<td class=\"difficulty-item-data\">2</td>\n" + //
				//"\t\t\t\t\t\t<td class=\"difficulty-item-data\">1</td>\n" + //
				//"\t\t\t\t\t\t<td class=\"notion-total\">4</td>\n" + //
				//"\t\t\t\t\t</tr>\n" + //
				//"\t\t\t\t\t<tr>\n" + //
				//"\t\t\t\t\t\t<td>Notion 2</td>\n" + //
				//"\t\t\t\t\t\t<td class=\"difficulty-item-data\">0</td>\n" + //
				//"\t\t\t\t\t\t<td class=\"difficulty-item-data\">1</td>\n" + //
				//"\t\t\t\t\t\t<td class=\"difficulty-item-data\">1</td>\n" + //
				//"\t\t\t\t\t\t<td class=\"difficulty-item-data\">1</td>\n" + //
				//"\t\t\t\t\t\t<td class=\"notion-total\">3</td>\n" + //
				//"\t\t\t\t\t</tr>\n" + //
				//"\t\t\t\t\t<tr>\n" + //
				//"\t\t\t\t\t\t<td>Notion 3</td>\n" + //
				//"\t\t\t\t\t\t<td class=\"difficulty-item-data\">0</td>\n" + //
				//"\t\t\t\t\t\t<td class=\"difficulty-item-data\">1</td>\n" + //
				//"\t\t\t\t\t\t<td class=\"difficulty-item-data\">1</td>\n" + //
				//"\t\t\t\t\t\t<td class=\"difficulty-item-data\">1</td>\n" + //
				//"\t\t\t\t\t\t<td class=\"notion-total\">3</td>\n" + //
				//"\t\t\t\t\t</tr>\n" + //
				"\t\t\t\t\t<!-- Les totaux en fonction des difficultés et global -->\n" + //
				"\t\t\t\t\t<tr>\n" + //
				"\t\t\t\t\t\t<td>Total</td>\n" + //
				//"\t\t\t\t\t\t<td class=\"difficulty-total\">0</td>\n" + //
				//"\t\t\t\t\t\t<td class=\"difficulty-total\">3</td>\n" + //
				//"\t\t\t\t\t\t<td class=\"difficulty-total\">4</td>\n" + //
				//"\t\t\t\t\t\t<td class=\"difficulty-total\">3</td>\n" + //
				//"\t\t\t\t\t\t<td class=\"grand-total\">10</td>\n" + //
				"\t\t\t\t\t</tr>\n" + //
				"\t\t\t\t</tbody>\n" + //
				"\t\t\t</table>\n" + //
				"\t\t\t<!-- Boutton pour commencer le questionnaire -->\n" + //
				"\t\t\t<button type=\"button\" class=\"start-button\">Commencer</button>\n" + //
				"\t\t</section>\n" + //
				"\n" + //
				"\t\t<!-- Footer -->\n" + //
				"\t\t<footer>\n" + //
				"\t\t\t<p>&copy; 2024 Groupe 2. Tous droits réservés.</p>\n" + //
				"\t\t</footer>\n" + //
				"\n" + //
				"\t\t<!-- Script -->\n" + //
				"\t\t<script src=\"./script/data.js\"></script>\n" + //
				"\t\t" + this.script + "\n" + //
				"\t</body>\n" + //
				"</html>\n";
	}

	private String getFinHtml()
	{
		return "<!DOCTYPE html>\n" + //
				"<html lang=\"fr\">\n" + //
				"<head>\n" + //
				"\t<meta charset=\"UTF-8\">\n" + //
				"\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" + //
				"\t<link rel=\"stylesheet\" href=\"./css/index.css\">\n" + //
				"\t<title>Fin du Questionnaire</title>\n" + //
				"</head>\n" + //
				"<body>\n" + //
				"\t<!--Titre de fin-->\n" + //
				"\t<h1 class=\"evaluation-title\">Fin du questionnaire</h1>\n" + //
				"\n" + //
				"\t<!-- Informations du questionnaire -->\n" + //
				"\t<section class=\"informations\">\n" + //
				"\t\t<p class=\"estimated-time\">Temps estimé : <span class=\"estimated-time-data\">20h</span></p>\n" + //
				"\t\t<p class=\"resource\">Ressources concernée : <span class=\"resource-data\">r1.01</span></p>\n" + //
				"\n" + //
				"\t\t<!-- Tableau du nombre de question par difficulté et notions -->\n" + //
				"\t\t<table class=\"difficulty-table\">\n" + //
				"\t\t\t<thead>\n" + //
				"\t\t\t\t<tr>\n" + //
				"\t\t\t\t\t<!-- L'entête du tableau -->\n" + //
				"\t\t\t\t\t<th>Notions / Difficultés</th>\n" + //
				"\t\t\t\t\t\t<th class=\"very-easy\">Très facile</th>\n" + //
				"\t\t\t\t\t\t<th class=\"easy\">Facile</th>\n" + //
				"\t\t\t\t\t\t<th class=\"medium\">Moyenne</th>\n" + //
				"\t\t\t\t\t\t<th class=\"hard\">Difficile</th>\n" + //
				"\t\t\t\t\t\t<th>Total</th>\n" + //
				"\t\t\t\t</tr>\n" + //
				"\t\t\t</thead>\n" + //
				"\t\t\t<tbody> <!-- Les données en fonction des notions -->\n" +//
				//"\t\t\t\t<tr>\n" + //
				//"\t\t\t\t\t<td>Notion 1</td>\n" + //
				//"\t\t\t\t\t<td class=\"difficulty-item-data very-easy\">1</td>\n" + //
				//"\t\t\t\t\t<td class=\"difficulty-item-data easy\">1</td>\n" + //
				//"\t\t\t\t\t<td class=\"difficulty-item-data medium\">0</td>\n" + //
				//"\t\t\t\t\t<td class=\"difficulty-item-data hard\">0</td>\n" + //
				//"\t\t\t\t\t<td class=\"notion-total\">2</td>\n" + //
				//"\t\t\t\t</tr>\n" + //
				//"\t\t\t\t<tr>\n" + //
				//"\t\t\t\t\t<td>Notion 2</td>\n" + //
				//"\t\t\t\t\t<td class=\"difficulty-item-data very-easy\">0</td>\n" + //
				//"\t\t\t\t\t<td class=\"difficulty-item-data easy\">0</td>\n" + //
				//"\t\t\t\t\t<td class=\"difficulty-item-data medium\">1</td>\n" + //
				//"\t\t\t\t\t<td class=\"difficulty-item-data hard\">0</td>\n" + //
				//"\t\t\t\t\t<td class=\"notion-total\">1</td>\n" + //
				//"\t\t\t\t</tr>\n" + //
				//"\t\t\t\t<tr>\n" + //
				//"\t\t\t\t\t<td>Notion 3</td>\n" + //
				//"\t\t\t\t\t<td class=\"difficulty-item-data very-easy\">0</td>\n" + //
				//"\t\t\t\t\t<td class=\"difficulty-item-data easy\">0</td>\n" + //
				//"\t\t\t\t\t<td class=\"difficulty-item-data medium\">0</td>\n" + //
				//"\t\t\t\t\t<td class=\"difficulty-item-data hard\">1</td>\n" + //
				//"\t\t\t\t\t<td class=\"notion-total\">1</td>\n" + //
				//"\t\t\t\t</tr>\n" + //
				//"\t\t\t\t<!-- Les totaux en fonction des difficultés et global -->\n" + //
				//"\t\t\t\t<tr> \n" + //
				//"\t\t\t\t\t<td>Total</td>\n" + //
				//"\t\t\t\t\t<td class=\"difficulty-total very-easy\">1</td>\n" + //
				//"\t\t\t\t\t<td class=\"difficulty-total easy\">1</td>\n" + //
				//"\t\t\t\t\t<td class=\"difficulty-total medium\">1</td>\n" + //
				//"\t\t\t\t\t<td class=\"difficulty-total hard\">1</td>\n" + //
				//"\t\t\t\t\t<td class=\"grand-total\">4</td>\n" + //
				//"\t\t\t\t</tr>\n" + //
				"\t\t\t</tbody>\n" + //
				"\t\t</table>\n" + //
				"\n" + //
				"\t\t<!-- Score -->\n" + //
				"\t\t<p class=\"score\">Votre score : <span class=\"score-data\"></span></p>\n" + //
				"\n" + //
				"\t\t<!-- Bouton recommencer -->\n" + //
				"\t\t<button type=\"button\" class=\"restart-button\" onclick=\"window.location.href='index.html'\">Recommencer</button>\n" + //
				"\n" + //
				"\t\t<!-- Footer -->\n" + //
				"\t\t<footer>\n" + //
				"\t\t\t<p>&copy; 2024 Groupe 2. Tous droits réservés.</p>\n" + //
				"\t\t</footer>\n" + //
				"\t\t\n" + //
				"\t\t<!-- Script -->\n" + //
				"\t\t<script src=\"./script/data.js\"></script>\n" + //
				"\t\t" + script + "\n" + //
				"\t</section>\n" + //
				"</body>\n" + //
				"</html>";
	}



	
}
