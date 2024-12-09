package metier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ressource
{
	private String nom;
	private List<Notion> notions;

	public Ressource(String nom)
	{
		this.nom = nom;
		this.notions = lireNotions();
	}

	private List<Notion> lireNotions() 
	{
		List<Notion> notions = new ArrayList<>();
		try 
		{
			Scanner scanner = new Scanner(new File("../data/ressources_notions.csv"));

			if( scanner.hasNextLine())
				scanner.nextLine();

			while (scanner.hasNextLine())
			{
				String   line         = scanner.nextLine();
				String[] parts        = line.split(";");
				String   nomRessource = parts[0];
				String   nomNotion    = parts[1];

				if ( nomRessource.equals(this.nom) )
				{
					Notion notion = new Notion(nomNotion, this);
					notions.add(notion);
				}
			}
			scanner.close();
		} 
		catch (FileNotFoundException e) { e.printStackTrace(); }

		return notions;
	}



	public String getNom() { return nom; }
	public List<Notion> getNotions() { return notions; }

	public boolean setNom(String nom) 
	{ 
		this.nom = nom; 
		return true;
	}

	public boolean ajouterNotion(Notion notion)
	{
		if (notion == null)
			return false;
		if (notions.contains(notion))
			return false;
		notions.add(notion);
		return true;
	}

	public boolean supprimerNotion(Notion notion)
	{
		if (notion == null)
			return false;
		if (!notions.contains(notion))
			return false;
		notions.remove(notion);
		return true;
	}

	
}