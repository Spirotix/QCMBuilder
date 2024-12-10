package src.metier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
			List<Reponse> lstReponses = new ArrayList<>();
			for (String sReponse : sLstReponses)
			{
				String[] parts = sReponse.split("_");
				Reponse reponse = new Reponse(parts[1], parts[0]);
				lstReponses.add(reponse);
			}

			notion.ajouterQuestion(new QCM(notion, text, timer, nbPoint, difficulte, lstReponses, explication));
			return true;
		}

		return false;
	}

	public void genererQuestionnaire(String nomRessource, String nomNotion)
	{
		try {

			// Create directories
			new File("../questionnaire/css").mkdirs();
			new File("../questionnaire/images").mkdirs();
			new File("../questionnaire/pages").mkdirs();
			new File("../questionnaire/script").mkdirs();

			String sIndex = "<!DOCTYPE html>\n" + //
								"<html lang=\"fr\">\n" + //
								"<head>\n" + //
								"\t<meta charset=\"UTF-8\">\n" + //
								"\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" + //
								"\t<link rel=\"stylesheet\" href=\"styles.css\">\n" + //
								"\t<title>Accueil Questionnaire</title>\n" + //
								"</head>\n" + //
								"<body>\n" + //
								"\t\n" + //
								"\t<h1 class=\"evaluation-title\">Titre du questionnaire</h1>\n" + //
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
								"\t\t<button type=\"button\" class=\"start-button\">Commencer</button>\n" + //
								"\t</div>\n" + //
								"\t<script src=\"script/index.js\"></script>\n" + //
								"</body>\n" + //
								"</html>";

			PrintWriter indexWriter = new PrintWriter("../questionnaire/index.html");
			indexWriter.println(sIndex);
			indexWriter.close();

			PrintWriter finIndex = new PrintWriter("../questionnaire/index.css");
			finIndex.println("/* TODO */");
			finIndex.close();

			PrintWriter stylesCss = new PrintWriter("../questionnaire/styles.css");
			stylesCss.println("/* TODO */");
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
			question1Writer.println("<!-- TODO -->\n");
			question1Writer.close();

			PrintWriter question2Writer = new PrintWriter("../questionnaire/pages/question2.html");
			question2Writer.println("<!-- TODO -->\n");
			question2Writer.close();

			PrintWriter question3Writer = new PrintWriter("../questionnaire/pages/question3.html");
			question3Writer.println("<!-- TODO -->\n");
			question3Writer.close();

			PrintWriter question4Writer = new PrintWriter("../questionnaire/pages/question4.html");
			question4Writer.println("<!-- TODO -->\n");
			question4Writer.close();

			// Create and write to script files
			PrintWriter globalJsWriter = new PrintWriter("../questionnaire/script/global.js");
			globalJsWriter.println("// TODO");
			globalJsWriter.close();

			PrintWriter indexJsWriter = new PrintWriter("../questionnaire/script/index.js");
			indexJsWriter.println("// TODO");
			indexJsWriter.close();

			PrintWriter popupJsWriter = new PrintWriter("../questionnaire/script/popup.js");
			popupJsWriter.println("// TODO");
			popupJsWriter.close();

			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		QCMBuilder qcmBuilder = new QCMBuilder();
		qcmBuilder.genererQuestionnaire("Bases de données", "SQL");
	}
}