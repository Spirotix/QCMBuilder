package src.metier;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import src.ihm.*;
import src.metier.question.*;
import src.metier.reponse.*;

/**
 * La classe QCMBuilder est responsable de la gestion des ressources et de la
 * création de questions pour un questionnaire.
 */
public class QCMBuilder
{

	/**
	 * Liste des ressources disponibles.
	 */
	private List<Ressource> lstRessources;

	/**
	 * Constructeur de la classe QCMBuilder. Initialise la liste des ressources
	 * en les lisant à partir d'un fichier CSV.
	 */
	public QCMBuilder()
	{
		this.lstRessources = lireRessources();
	}

	/**
	 * Lit les ressources à partir d'un fichier CSV et les ajoute à la liste des
	 * ressources.
	 * 
	 * @return La liste des ressources lues.
	 */
	private List<Ressource> lireRessources()
	{
		List<Ressource> lstRessources = new ArrayList<>();
		try
		{
			Scanner scannerRessource = new Scanner(new File("../data/ressources.csv"));
			scannerRessource.nextLine();
			while (scannerRessource.hasNextLine())
			{
				String lineRessource = scannerRessource.nextLine();

				System.out.println();
				System.out.println(lineRessource);

				String codeRessource = lineRessource.substring(0, lineRessource.indexOf(";"));
				String nomRessource = lineRessource.substring(lineRessource.indexOf(";") + 1);

				Ressource ressource = new Ressource(codeRessource, nomRessource);

				for (Ressource r : lstRessources)
				{
					if (r.getCode().equals(codeRessource))
						ressource = r;
				}

				if (!lstRessources.contains(ressource))
					lstRessources.add(ressource);
			}
			scannerRessource.close();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

		return lstRessources;
	}

	/**
	 * Retourne la liste des ressources.
	 * 
	 * @return La liste des ressources.
	 */
	public List<Ressource> getRessources()
	{
		return lstRessources;
	}

	/**
	 * Ajoute une ressource à la liste des ressources.
	 * 
	 * @param ressource
	 *            La ressource à ajouter.
	 * @return true si la ressource a été ajoutée, false si elle existait déjà.
	 */
	public boolean ajouterRessource(Ressource ressource)
	{
		for (Ressource r : lstRessources)
		{
			if (r.getCode().equals(ressource.getCode()))
				ressource = r;
		}

		if ( ! lstRessources.contains(ressource) )
		{
			try
			{
				PrintWriter writer = new PrintWriter( new FileWriter("../data/ressources.csv", true) );

				Scanner scanner = new Scanner(new File("../data/ressources.csv"));
				scanner.nextLine();
				while ( scanner.hasNextLine() )
				{
					String line = scanner.nextLine();
					if ( line.equals( ressource.getCode() + ";" + ressource.getNom() ) )
					{
						System.out.println("La ligne existe déjà");
						scanner.close();
						writer .close();
						return false;
					}

					System.out.println("Ligne : " + line);
					System.out.println("Ajout : " + ressource.getCode() + ";" + ressource.getNom() + "\n");
				}

				File fileRep = new File( "../data/questions_NOUVEAU/" + ressource.getCode() );
				fileRep.mkdirs();

				lstRessources.add(ressource);

				writer.println( ressource.getCode() + ";" + ressource.getNom() );

				scanner.close();
				writer .close();
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

	/**
	 * Supprime une ressource de la liste des ressources.
	 * 
	 * @param ressource
	 *            La ressource à supprimer.
	 * @return true si la ressource a été supprimée, false si elle n'existait
	 *         pas.
	 */
	public boolean supprimerRessource(Ressource ressource)
	{
		if (ressource == null)
			return false;

		if (!lstRessources.contains(ressource))
			return false;

		System.out.println("SupprimerQCMBuilder");

		Iterator<Notion> iterator = ressource.getNotions().iterator();
		while (iterator.hasNext())
		{
			Notion n = iterator.next();
			iterator.remove(); // Supprime en passant par l'itérateur
			ressource.supprimerNotion(n);
		}

		File fileCSV = new File("../data/ressources.csv");
		File fileRep = new File("../data/questions_NOUVEAU/" + ressource.getCode());

		// Supprimer la ligne
		QCMBuilder.supprimerLigneEtRepertoire(ressource, fileCSV, fileRep);

		lstRessources.remove(ressource);
		return true;
	}

	public static void supprimerLigneEtRepertoire(Ressource ressource, File fichier, File repertoireRessource)
	{
		File fichierTemp = new File(fichier.getParent(), "fichier_temp.csv");
	
		try (BufferedReader br = new BufferedReader(new FileReader(fichier));
			 BufferedWriter bw = new BufferedWriter(new FileWriter(fichierTemp)))
		{
			String  ligne;
			boolean ligneSupprimee = false;

			// Parcourir le fichier et écrire toutes les lignes sauf celle à supprimer
			while ((ligne = br.readLine()) != null)
			{
				String[] parts = ligne.split(";");
				if (parts.length > 1)
				{
					String codeRessource = parts[0];
					String nomRessource  = parts[1];
					if ( codeRessource.equals( ressource.getCode() ) && nomRessource.equals( ressource.getNom() ) && ! ligneSupprimee )
					{
						System.out.println("Ligne supprimée : " + ligne);
						ligneSupprimee = true;
						continue; // Ne pas écrire cette ligne
					}
				}
				bw.write(ligne);
				bw.newLine();
			}

		}
		catch (IOException e)
		{
			e.printStackTrace();
			return;
		}

		// Remplacer le fichier original par le fichier temporaire
		if (fichier.delete())
			if (!fichierTemp.renameTo(fichier))
				System.out.println("Erreur lors du renommage du fichier temporaire.");
			else
				System.out.println("Fichier mis à jour avec succès.");
		else
			System.out.println("Impossible de supprimer le fichier original.");

			// Supprimer le répertoire
			supprimerRepertoireRecursif(repertoireRessource);
		}

	private static void supprimerRepertoireRecursif(File dossier)
	{
		if (dossier.exists())
		{
			if (dossier.isDirectory())
			{
				File[] fichiers = dossier.listFiles();
				if (fichiers != null)
					for (File fichier : fichiers)
						supprimerRepertoireRecursif(fichier);
			}
			if (dossier.delete())
				System.out.println("Supprimé : " + dossier.getAbsolutePath());
			else
				System.out.println("Impossible de supprimer : " + dossier.getAbsolutePath());
		}
	}

	/**
	 * Recherche une ressource par son code ou son nom.
	 * 
	 * @param code_nom
	 *            Le code ou le nom de la ressource à rechercher.
	 * @return La ressource trouvée, ou null si elle n'existe pas.
	 */
	public Ressource rechercherRessource(String code_nom)
	{
		Ressource ressourceTrouvee = null;
		for (Ressource ressource : lstRessources)
		{
			if ((ressource.getCode() + "_" + ressource.getNom()).equals(code_nom) || (ressource.getCode()).equals(code_nom))
				ressourceTrouvee = ressource;
		}
		return ressourceTrouvee;
	}

	/**
	 * Modifie le nom d'une ressource.
	 * 
	 * @param ressource
	 *            La ressource à modifier.
	 * @param nouveauNom
	 *            Le nouveau nom de la ressource.
	 * @return true si la ressource a été modifiée, false sinon.
	 */
	public boolean modifierRessource(Ressource ressource, String nouveauNom)
	{
		if (ressource == null)
			return false;

	//	if (!lstRessources.contains(ressource))
	//		return false;

		File fichier     = new File("../data/ressources.csv");
		File fichierTemp = new File(fichier.getParent(), "fichier_temp.csv");
	
		try (BufferedReader br = new BufferedReader(new FileReader(fichier));
			 BufferedWriter bw = new BufferedWriter(new FileWriter(fichierTemp)))
		{
			String  ligne;
			boolean ligneModifiee = false;

			// Parcourir le fichier et écrire toutes les lignes
			while ((ligne = br.readLine()) != null)
			{
				String[] parts = ligne.split(";");
				if (parts.length > 1)
				{
					String codeRessource = parts[0];
					String nomRessource  = parts[1];
					if ( codeRessource.equals( ressource.getCode() ) && nomRessource.equals( ressource.getNom() ) && ! ligneModifiee )
					{
						bw.write( codeRessource + ";" + nouveauNom );
						System.out.println("Ligne modifiee : " + ligne + "\n" +
						                   "                 " + codeRessource + ";" + nouveauNom);
						ligneModifiee = true;
					}
				}
				bw.write(ligne);
				bw.newLine();
			}

		}
		catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}

		// Remplacer le fichier original par le fichier temporaire
		if (fichier.delete())
			if (!fichierTemp.renameTo(fichier))
				System.out.println("Erreur lors du renommage du fichier temporaire.");
			else
				System.out.println("Fichier mis à jour avec succès.");
		else
			System.out.println("Impossible de supprimer le fichier original.");

		ressource.setNom(nouveauNom);

		return true;
	}

	/**
	 * Crée une question pour une notion spécifique d'une ressource.
	 * 
	 * @param type
	 *            Le type de la question (Elimination, QCM, Association).
	 * @param code_nomRessource
	 *            Le code ou le nom de la ressource.
	 * @param nomNotion
	 *            Le nom de la notion.
	 * @param text
	 *            Le texte de la question.
	 * @param timer
	 *            Le temps imparti pour répondre à la question.
	 * @param nbPoint
	 *            Le nombre de points attribués à la question.
	 * @param difficulte
	 *            La difficulté de la question.
	 * @param sLstReponses
	 *            La liste des réponses possibles.
	 * @param explication
	 *            L'explication de la réponse.
	 * @return true si la question a été créée et ajoutée, false sinon.
	 */
	public boolean creerQuestion(String type, String code_nomRessource, String nomNotion, String text, int timer,
			double nbPoint, int difficulte, ArrayList<TypeReponse> sLstReponses, String explication)
	{
		Notion notion = rechercherRessource(code_nomRessource.substring(0, code_nomRessource.indexOf("_")))
				.rechercherNotion(nomNotion);

		if (type.equals("Elimination"))
		{
			List<ReponseElimination> lstReponses = new ArrayList<>();
			int nbIndice = 0;
			for (TypeReponse typeReponse : sLstReponses)
			{
				String stringVrai;
				if (typeReponse.getEstBonneReponse())
					stringVrai = "Vrai";
				else
					stringVrai = "Faux";

				ReponseElimination reponse = new ReponseElimination(stringVrai, typeReponse.getContenu(),
						typeReponse.getOrdre(), typeReponse.getCout());

				if (typeReponse.getOrdre() > nbIndice)
					nbIndice = typeReponse.getOrdre();

				if (!lstReponses.contains(reponse))
					lstReponses.add(reponse);
			}

			return notion.ajouterQuestion(
					new Elimination(notion, text, timer, nbPoint, difficulte, lstReponses, nbIndice, explication));
		}
		else if (type.equals("QCM"))
		{
			List<ReponseQCM> lstReponses = new ArrayList<>();
			for (TypeReponse typeReponse : sLstReponses)
			{
				String stringVrai;
				if (typeReponse.getEstBonneReponse())
					stringVrai = "Vrai";
				else
					stringVrai = "Faux";

				ReponseQCM reponse = new ReponseQCM( typeReponse.getContenu(), stringVrai );

				if (!lstReponses.contains(reponse))
					lstReponses.add(reponse);
			}

			return notion.ajouterQuestion(new QCM(notion, text, timer, nbPoint, difficulte, lstReponses, explication));

		}
		else if (type.equals("Association"))
		{
			List<ReponseAssociation> lstReponses = new ArrayList<>();

			for (TypeReponse typeReponse : sLstReponses)
			{
				ReponseAssociation reponseG = new ReponseAssociation(typeReponse.getRepGauche().getContenu(), null,
						true);

				ReponseAssociation reponseD = new ReponseAssociation(typeReponse.getRepDroite().getContenu(), reponseG,
						false);

				reponseG.setReponseAssocie(reponseD);

				if (!lstReponses.contains(reponseD))
					lstReponses.add(reponseD);

				if (!lstReponses.contains(reponseG))
					lstReponses.add(reponseG);
			}

			return notion.ajouterQuestion(
					new Association(notion, text, timer, nbPoint, difficulte, lstReponses, explication));

		}
		else
		{
			System.out.println("Le type de la question crée est invalide, ou n'est pas pris en charge.");
			return false;
		}
	}

	/**
	 * Point d'entrée principal de l'application.
	 * 
	 * @param args
	 *            Les arguments de la ligne de commande.
	 */
	public static void main(String[] args)
	{
		QCMBuilder qcmBuilder = new QCMBuilder();
		List<String> nomsNotions = new ArrayList<>();
		nomsNotions.add("Les types de bases");
		nomsNotions.add("Les tableaux à 1 dimension");
		nomsNotions.add("Les tableaux à deux dimensions");
	}
	
	public void genererQuestionnaire(String nomRessource, boolean chrono, List<TypeQuestionnaire> lstTypeQuestionnaire, String nomFichier)
	{
		// TODO
	}

}                                                          
