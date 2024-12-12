package src.metier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ressource
{
	private String       code;
	private String       nom;
	private List<Notion> lstNotions;

	public Ressource(String code, String nom)
	{
		this.code    = code;
		this.nom     = nom;
		this.lstNotions = lireNotions();
	}

	private List<Notion> lireNotions() 
	{
		List<Notion> notions = new ArrayList<>();
		try 
		{
			Scanner scanner = new Scanner(new File("./data/ressources_notions.csv"));

			if( scanner.hasNextLine()) scanner.nextLine();

			while (scanner.hasNextLine())
			{
				String   line          = scanner.nextLine();
				String[] parts         = line.split(";");
				String   codeRessource = parts[0];
				String   nomNotion     = parts[2];

				if ( codeRessource.equals(this.code) )
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

	public String       getCode()    { return this.code;       }
	public String       getNom()     { return this.nom;        }
	public List<Notion> getNotions() { return this.lstNotions; }

	public boolean setNom(String nom) 
	{ 
		this.nom = nom; 
		return true;
	}

	public boolean ajouterNotion(Notion notion)
	{
		if (notion == null)
			return false;
		if (lstNotions.contains(notion))
			return false;
		lstNotions.add(notion);
		return true;
	}

	public boolean supprimerNotion(Notion notion)
	{
		if (notion == null)
			return false;
		if (!lstNotions.contains(notion))
			return false;
		lstNotions.remove(notion);
		return true;
	}

	public Notion rechercherNotion(String nom)
	{
		Notion notionTrouvee = null;
		for (Notion notion : lstNotions)
		{
			if (notion.getNom().equals(nom))
				notionTrouvee = notion;
		}
		return notionTrouvee;
	}

	
}