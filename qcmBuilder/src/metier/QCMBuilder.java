package metier;

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
			Scanner scanner = new Scanner(new File("../data/ressources_notions.csv"));
			while (scanner.hasNextLine()) 
			{
				String   line         = scanner.nextLine();
				String[] parts        = line.split(";");
				String   nomRessource = parts[0];

				Ressource ressource = new Ressource(nomRessource);

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

	public void genererQuestionnaire()
	{
		try {
			File index = new File("../output/index.html");
			if (!index.exists()) {
				index.createNewFile();
			}

			String sIndex = "<!DOCTYPE html>" + //
							"<html lang=\"fr\">" + //
							"<head>" + //
							"<meta charset=\"UTF-8\">" + //
							"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" + //
							"<link rel=\"stylesheet\" href=\"index.css\">" + //
							"<title>Accueil Questionnaire</title>" + //
							"</head>" + //
							"<body>" + //
							"" + //
							"<h1 class=\"evaluation-title\">Titre du questionnaire</h1>" + //
							"" + //
							"<div class=\"informations\">" + //
							"<p class=\"estimated-time\">Temps estimé : <span class=\"estimated-time-data\">20h</span></p>" + //
							"" + //
							"<p class=\"resource\">Ressources concernée : <span class=\"resource-data\">r1.01</span></p>" + //
							"" + //
							"<p class=\"notions-title\">Notion abordées :</p>" + //
							"<ul class=\"notions-list\">" + //
							"<li class=\"notion-item\"><span class=\"notions-item-data\">Notion 1</span></li>" + //
							"<li class=\"notion-item\"><span class=\"notions-item-data\">Notion 2</span></li>" + //
							"<li class=\"notion-item\"><span class=\"notions-item-data\">Notion 3</span></li>" + //
							"</ul>" + //
							"" + //
							"<p class=\"questions-count\">Nombre de questions : <span class=\"questions-count-data\">10</span></p>" + //
							"<ul class=\"difficulty-list\">" + //
							"<li class=\"difficulty-item very-easy\">Très facile : <span class=\"difficulty-item-data\">0</span></li>" + //
							"<li class=\"difficulty-item easy\">     Facile :      <span class=\"difficulty-item-data\">3</span></li>" + //
							"<li class=\"difficulty-item medium\">   Moyenne :     <span class=\"difficulty-item-data\">4</span></li>" + //
							"<li class=\"difficulty-item hard\">     Difficile :   <span class=\"difficulty-item-data\">3</span></li>" + //
							"</ul>" + //
							"<button type=\"button\" class=\"start-button\">Commencer</button>" + //
							"</div>" + //
							"<script src=\"script/index.js\"></script>" + //
							"</body>" + //
							"</html>";

			PrintWriter writer = new PrintWriter(index);
			writer.println(sIndex);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}