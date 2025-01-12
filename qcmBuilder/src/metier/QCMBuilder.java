package metier;

import java.io.File;
import java.io.FileNotFoundException;
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
}