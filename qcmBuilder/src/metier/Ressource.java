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
		this.code       = code;
		this.nom        = nom;
		this.lstNotions = lireNotions();
	}

	private List<Notion> lireNotions()
	{
		List<Notion> notions = new ArrayList<>();
		try 
		{
			Scanner scanner = new Scanner(new File("../data/notions.csv"));

			if( scanner.hasNextLine()) scanner.nextLine();

			while (scanner.hasNextLine())
			{
				String   line          = scanner.nextLine();
				String[] parts         = line.split(";");
				String   codeRessource = parts[0];
				String   nomNotion     = parts[1];

				if ( codeRessource.equals(this.code) )
				{
					// Créer le fichier d'informations de toutes les questions
					File fileInformations  = new File( "../data/questions_NOUVEAU/" + this.getCode() + "/" + nomNotion + "/" + nomNotion + ".csv" );

					// Créer les répertoires non existants (ou ce trouve le csv)
					fileInformations.getParentFile().mkdirs();

					if ( !fileInformations.exists() )
					{
						try ( PrintWriter writerData = new PrintWriter( new FileWriter(fileInformations) ) )
						{
							writerData.println("N_QUESTION;NOMBRE_REPONSES;POINT;TYPE;NIVEAU;TEMPS;EXPLICATION");
						}
						catch (IOException e) { e.printStackTrace(); }

						System.out.println("FICHIER " + nomNotion + ".csv CREE");
					}

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
			try
			{
				PrintWriter writer = new PrintWriter( new FileWriter("../data/notions.csv", true) );

				Scanner scanner = new Scanner(new File("../data/notions.csv"));
				scanner.nextLine();
				while ( scanner.hasNextLine() )
				{
					String line = scanner.nextLine();
					if ( line.equals( this.getCode() + notion.getNom() ) )
					{
						System.out.println("La ligne existe déjà");
						scanner.close();
						writer .close();
						return false;
					}

					System.out.println("Ligne : " + line);
					System.out.println("Ajout : " + this.getCode() + ";" + notion.getNom() + "\n");
				}

				lstNotions.add(notion);

				writer.println( this.getCode() + ";" + notion.getNom() );

				scanner.close();
				writer .close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			// Créer le fichier d'informations de toutes les questions
			File fileInformations  = new File( "../data/questions_NOUVEAU/" + this.getCode() + "/" + notion.getNom() + "/" + notion.getNom() + ".csv" );

			// Créer les répertoires non existants (ou ce trouve le csv)
			fileInformations.getParentFile().mkdirs();

			if ( !fileInformations.exists() )
			{
				try ( PrintWriter writerData = new PrintWriter( new FileWriter(fileInformations) ) )
				{
					writerData.println("N_QUESTION;NOMBRE_REPONSES;POINT;TYPE;NIVEAU;TEMPS;EXPLICATION");
				}
				catch (IOException e) { e.printStackTrace(); }
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

		Notion    n = new Notion("George", r);

		r.ajouterNotion( n );

		List<ReponseQCM> lstQCM = new ArrayList<>();
		lstQCM.add( new ReponseQCM("Vrai", "REPONSE 1") );
		lstQCM.add( new ReponseQCM("Faux", "REPONSE 2") );
		List<ReponseElimination> lstEli = new ArrayList<>();
		lstEli.add( new ReponseElimination("Vrai", "REPONSE 1", 0, 0) );
		lstEli.add( new ReponseElimination("Faux", "REPONSE 2", 1, 0.5) );
		List<ReponseAssociation> lstAss = new ArrayList<>();
		lstAss.add( new ReponseAssociation("REPONSE 1", new ReponseAssociation("REPONSE 11", null, false), true) );
		lstAss.add( new ReponseAssociation("REPONSE 2", new ReponseAssociation("REPONSE 22", null, false), true) );

		n.ajouterQuestion( new QCM        (n, "Question hohoo", 0, 2.23, 1, lstQCM, "yeehaw") );
		n.ajouterQuestion( new Elimination(n, "Question hohoooo", 10, 5.5, 3, lstEli, 2, "mmmmmmmmmm") );
		n.ajouterQuestion( new Association(n, "Question hohoooooo", 30, 10.87, 4, lstAss, "la plus longue") );

	//	n.ajouterQuestion( new Elimination(n, "Question TESTTTTTTTTTT", 10, 5.5, 3, lstEli, 2, "") ); // à ajouter apres les 3 autres
	}
}