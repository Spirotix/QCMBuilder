package src.metier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import src.metier.question.Association;
import src.metier.question.Elimination;
import src.metier.question.QCM;
import src.metier.reponse.*;

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
			Scanner scanner = new Scanner(new File("../data/ressources_notions.csv"));

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
		for ( Notion n : lstNotions )
		{
			if ( n.getNom().equals(notion.getNom()) )
				notion = n;
		}

		if ( ! lstNotions.contains(notion) )
		{
			lstNotions.add(notion);

			try
			{
				PrintWriter writer = new PrintWriter( new FileWriter("./data/ressources_notions.csv", true) );

				writer.println( this.getCode() + ";" + this.getNom() + ";" + notion.getNom() );

				writer.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			return true;
		}
		else
		{
			return false;
		}
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

	public static void main(String[] args)
	{
		Ressource r = new Ressource("R1.11", "Bases de la communication");

		Notion    n = new Notion("NOTION 1", r);

		r.ajouterNotion( n );

	//	List<ReponseQCM> lstQCM = new ArrayList<>();
	//	lstQCM.add( new ReponseQCM("Vrai", "REPONSE 1") );
	//	lstQCM.add( new ReponseQCM("Faux", "REPONSE 2") );
		List<ReponseElimination> lstEli = new ArrayList<>();
		lstEli.add( new ReponseElimination("Vrai", "REPONSE 1", 0, 0) );
		lstEli.add( new ReponseElimination("Faux", "REPONSE 2", 1, 0.5) );
	//	List<ReponseAssociation> lstAss = new ArrayList<>();
	//	lstAss.add( new ReponseAssociation("REPONSE 1", new ReponseAssociation("REPONSE 11", null, 1, false), 1, true) );
	//	lstAss.add( new ReponseAssociation("REPONSE 2", new ReponseAssociation("REPONSE 22", null, 1, false), 1, true) );

	//	n.ajouterQuestion( new QCM        (n, "Question hohoo", 0, 2.23, 1, lstQCM, "") );
	//	n.ajouterQuestion( new Elimination(n, "Question hohoooo", 10, 5.5, 3, lstEli, 2, "") );
	//	n.ajouterQuestion( new Association(n, "Question hohoooooo", 30, 10.87, 4, lstAss, "") );

		n.ajouterQuestion( new Elimination(n, "Question TESTTTTTTTTTT", 10, 5.5, 3, lstEli, 2, "") );
	}
}