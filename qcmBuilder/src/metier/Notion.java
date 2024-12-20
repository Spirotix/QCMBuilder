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
import java.util.List;
import java.util.Scanner;
import src.metier.question.*;
import src.metier.reponse.*;
import java.nio.file.*;

/**
 * Classe représentant une notion avec un nom, une ressource et une liste de
 * questions.
 */
public class Notion
{
	private String         nom         ;
	private Ressource      ressource   ;
	private List<Question> lstQuestions;


	/**
	 * Constructeur de la classe Notion.
	 * 
	 * @param nom
	 *            Le nom de la notion.
	 * @param ressource
	 *            La ressource associée à la notion.
	 */
	public Notion(String nom, Ressource ressource)
	{
		this.nom          = nom;
		this.ressource    = ressource;
		this.lstQuestions = lireQuestions();
	}

	/**
	 * Lit les questions associées à la notion à partir de fichiers.
	 * 
	 * @return La liste des questions lues.
	 */
	private List<Question> lireQuestions()
	{
		List<Question> lstQuestions = new ArrayList<>();
		try
		{
			File    fileInformations = new File("../data/ressources_notions_questions/" + this.ressource.getCode() + "/" + this.nom + "/" + this.nom + ".csv");
			Scanner scInformations   = new Scanner(fileInformations);

			if (!scInformations.hasNextLine())
			{
				scInformations.close();
				return new ArrayList<>();
			}
			else
			{
				scInformations.nextLine();
			}

			while (scInformations.hasNextLine())
			{
				File dossierComplement = new File("../data/ressources_notions_questions/" + this.ressource.getCode() + "/" + this.nom + "/question_" + (lstQuestions.size() + 1) + "/complement");
				File fileTextQuestion  = new File("../data/ressources_notions_questions/" + this.ressource.getCode() + "/" + this.nom + "/question_" + (lstQuestions.size() + 1) + "/text_question.rtf");

				if (!fileTextQuestion.getPath().contains("question_0"))
				{
					dossierComplement.mkdirs();
					fileTextQuestion.getParentFile().mkdirs();

					if (!fileTextQuestion.exists())
					{
						try
						{
							PrintWriter tmp = new PrintWriter(new FileWriter(fileTextQuestion));
							tmp.close();
						}
						catch (IOException e)
						{
							e.printStackTrace();
						}
						System.out.println("\tFICHIER text_question CREE");
					}
				}
				else
				{
					break;
				}

				Scanner scTextQuestion = new Scanner(fileTextQuestion);

				if (!scTextQuestion.hasNextLine() || !scInformations.hasNextLine())
				{
					scInformations.close();
					scTextQuestion.close();
					return new ArrayList<>();
				}

				String lineTextQuestion = scTextQuestion.nextLine();
				String lineInformations = scInformations.nextLine();

				while (scTextQuestion.hasNextLine())
				{
					lineTextQuestion += scTextQuestion.nextLine();
				}
				String textQuestion   = lineTextQuestion;

				String[] informations = lineInformations.split(";");

				int    nbReponses  = Integer.parseInt  (informations[1]);
				double nbPoint     = Double.parseDouble(informations[2]);
				String type        =                    informations[3];
				String sNiveau     =                    informations[4];
				int    temps       = Integer.parseInt  (informations[5]);
				String explication =                    informations[6];

				int niveau;
				switch (sNiveau)
				{
					case "TF" -> niveau = 1;
					case "F"  -> niveau = 2;
					case "M"  -> niveau = 3;
					case "D"  -> niveau = 4;
					default -> {
						scTextQuestion.close();
						scInformations.close();
						throw new IllegalArgumentException( "Le niveau doit appartenir aux options suivantes : 'TF','F','M','D'");
					}
				}

				if (type.equals("Association"))
				{
					List<ReponseAssociation> lstReponse = new ArrayList<>();

					for (int numReponse = 1; numReponse <= nbReponses; numReponse++)
					{
						Scanner scReponse = new Scanner(new File("../data/ressources_notions_questions/" + this.ressource.getCode() + "/" + this.nom + "/question_" + (lstQuestions.size() + 1) + "/text_reponse_" + numReponse + ".rtf"));

						String lineTextReponse = scReponse.nextLine();

						ReponseAssociation reponseA;
						String textReponseA = lineTextReponse.substring(0, lineTextReponse.indexOf("::"));

						ReponseAssociation reponseB;
						String textReponseB = lineTextReponse.substring(lineTextReponse.indexOf("::") + 2);

						reponseA = new ReponseAssociation(textReponseA, null, true);
						lstReponse.add(reponseA);

						reponseB = new ReponseAssociation(textReponseB, reponseA, false);
						reponseA.setReponseAssocie(reponseB);

						lstReponse.add(reponseB);

						scReponse.close();
					}

					Association question = new Association(this, textQuestion, temps, nbPoint, niveau, lstReponse, explication);
					lstQuestions.add(question);
				}
				else if (type.equals("Elimination"))
				{
					List<ReponseElimination> lstReponse = new ArrayList<>();

					int nbIndice = 0;

					for (int numReponse = 1; numReponse <= nbReponses; numReponse++)
					{
						Scanner scReponse = new Scanner(new File("../data/ressources_notions_questions/" + this.ressource.getCode() + "/" + this.nom + "/question_" + (lstQuestions.size() + 1) + "/text_reponse_" + numReponse + ".rtf"));

						String lineTextReponse = scReponse.nextLine();

						lstReponse.add(new ReponseElimination(
						                                      lineTextReponse.substring( lineTextReponse.indexOf("} ") + 1, lineTextReponse.indexOf( "|") ),
						                                      lineTextReponse.substring( lineTextReponse.indexOf( "|") + 1, lineTextReponse.indexOf("||") ),
						                  Integer.parseInt   (lineTextReponse.substring( lineTextReponse.indexOf("||") + 2, lineTextReponse.indexOf("/")) ),
						                  Double .parseDouble(lineTextReponse.substring( lineTextReponse.indexOf( "/") + 1 )                                  )
						                                     )
						              );

						if ( nbIndice < Integer.parseInt(lineTextReponse.substring( lineTextReponse.indexOf("||") + 2, lineTextReponse.indexOf("/") )) )
						{
							nbIndice = Integer.parseInt( lineTextReponse.substring( lineTextReponse.indexOf("||") + 2, lineTextReponse.indexOf("/") ) );
						}

						scReponse.close();
					}

					Elimination question = new Elimination(this, textQuestion, temps, nbPoint, niveau, lstReponse, nbIndice, explication);
					lstQuestions.add(question);
				}
				else if (type.equals("QCM"))
				{
					List<ReponseQCM> lstReponse = new ArrayList<>();

					for (int numReponse = 1; numReponse <= nbReponses; numReponse++)
					{
						Scanner scReponse = new Scanner(new File("../data/ressources_notions_questions/" + this.ressource.getCode() + "/" + this.nom + "/question_" + (lstQuestions.size() + 1) + "/text_reponse_" + numReponse + ".rtf"));

						String lineTextReponse = scReponse.nextLine();

						lstReponse.add(new ReponseQCM(
						                              lineTextReponse.substring(lineTextReponse.indexOf("} ") + 2, lineTextReponse.indexOf("|")),
						                              lineTextReponse.substring(lineTextReponse.indexOf( "|") + 1                                  )
						                             )
						              );
						scReponse.close();
					}

					QCM question = new QCM(this, textQuestion, temps, nbPoint, niveau, lstReponse, explication);
					lstQuestions.add(question);
				}
				else
				{
					scTextQuestion.close();
					scInformations.close();
					throw new IllegalArgumentException( "Le type doit appartenir aux options suivantes : 'Association','Elimination','QCM'");
				}
				scTextQuestion.close();
			}
			scInformations.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

		return lstQuestions;
	}

	/**
	 * Retourne le nom de la notion.
	 * 
	 * @return Le nom de la notion.
	 */
	public String getNom()
	{
		return this.nom;
	}

	/**
	 * Retourne la ressource associée à la notion.
	 * 
	 * @return La ressource associée.
	 */
	public Ressource getRessource()
	{
		return new Ressource(this.ressource.getCode(), this.ressource.getNom());
	}

	/**
	 * Retourne la liste des questions associées à la notion.
	 * 
	 * @return La liste des questions.
	 */
	public List<Question> getQuestions()
	{
		return new ArrayList<>(this.lstQuestions);
	}

	/**
	 * Modifie le nom de la notion.
	 * 
	 * @param nom
	 *            Le nouveau nom de la notion.
	 * @return true si le nom a été modifié avec succès, false sinon.
	 */
	public boolean setNom(String nom)
	{
		this.nom = nom;
		return true;
	}

	/**
	 * Modifie la ressource associée à la notion.
	 * 
	 * @param ressource
	 *            La nouvelle ressource.
	 * @return true si la ressource a été modifiée avec succès, false sinon.
	 */
	public boolean setRessource(Ressource ressource)
	{
		this.ressource = ressource;
		return true;
	}

	/**
	 * Ajoute une question à la liste des questions de la notion.
	 * 
	 * @param question
	 *            La question à ajouter.
	 * @return true si la question a été ajoutée avec succès, false sinon.
	 */
	public boolean ajouterQuestion(Question question)
	{
		for (Question q : lstQuestions)
		{
			if (q.getText().equals(question.getText()))
			{
				System.out.println("Cette question existe déjà, veuillez changer la question.");
				return false;
			}
		}

		if (!lstQuestions.contains(question))
		{
			try
			{
				File fileTextQuestion = new File("../data/ressources_notions_questions/" + this.ressource.getCode() + "/" + this.nom + "/question_" + (this.lstQuestions.size() + 1) + "/text_question.rtf");
				fileTextQuestion.getParentFile().mkdirs();

				if (!fileTextQuestion.exists())
				{
					try
					{
						PrintWriter writerQues = new PrintWriter(new FileWriter(fileTextQuestion));
						writerQues.close();
					} catch (IOException e)
					{
						e.printStackTrace();
					}
				}

				File dossierComplement = new File("../data/ressources_notions_questions/" + this.ressource.getCode() + "/" + this.nom + "/question_" + (this.lstQuestions.size() + 1) + "/complement");
				dossierComplement.mkdirs();

				File fileData = new File("../data/ressources_notions_questions/" + this.ressource.getCode() + "/" + this.nom + "/" + this.nom + ".csv");

				try ( PrintWriter writerTextQuestion = new PrintWriter(new FileWriter(fileTextQuestion, false));
				      PrintWriter writerData         = new PrintWriter(new FileWriter(fileData        ,  true)))
				{
					writerTextQuestion.println(question.getText());

					int indRep = 1;

					if (question instanceof QCM)
					{
						QCM qcm = (QCM) question;

						for (ReponseQCM r : qcm.getlstReponses())
						{
							try
							{
								PrintWriter writerTextReponse = new PrintWriter(new FileWriter( new File("../data/ressources_notions_questions/" + this.ressource.getCode() + "/" + this.nom + "/question_" + (this.lstQuestions.size() + 1) + "/text_reponse_" + indRep++ + ".rtf"), false));
								writerTextReponse.println( r.getText() + "|" + (r.estVrai() ? "Vrai" : "Faux") );
								writerTextReponse.close();
							}
							catch (Exception e)
							{
								e.printStackTrace();
							}
						}
						try
						{
							Path sourceDir = Paths.get( "../data/ressources_notions_questions/temp" );
							Path destDir   = Paths.get( "../data/ressources_notions_questions/" + this.ressource.getCode() + "/" + this.nom + "/question_" + (this.lstQuestions.size() + 1)+"/complement" );

							if (!Files.exists(sourceDir) || !Files.isDirectory(sourceDir)) 
								throw new IllegalArgumentException("Le répertoire source n'existe pas ou n'est pas un répertoire.");

							Files.list(sourceDir).forEach(sourceFile ->
							{
								try
								{
									Path destFile = destDir.resolve(sourceFile.getFileName());
									Files.copy(sourceFile, destFile, StandardCopyOption.REPLACE_EXISTING);
									System.out.println( "Fichier copié : " + sourceFile + " -> " + destFile );
								}
								catch (IOException e)
								{
									System.out.println( "Erreur lors de la copie du fichier : " + sourceFile + " - " + e.getMessage() );
								}
							});
						}
						catch (IOException e)
						{
							System.out.println( "Erreur lors de la copie des fichiers : " + e.getMessage() );
						}

						lstQuestions.add(qcm);
					}
					else if (question instanceof Elimination)
					{
						Elimination elimination = (Elimination) question;

						for (ReponseElimination r : elimination.getLstReponses())
						{
							try
							{
								PrintWriter writerTextReponse = new PrintWriter(new FileWriter( new File("../data/ressources_notions_questions/" + this.ressource.getCode() + "/" + this.nom + "/question_" + (this.lstQuestions.size() + 1) + "/text_reponse_" + indRep++ + ".rtf"), false));
								writerTextReponse.println( (r.estVrai() ? "Vrai" : "Faux") + "|" + r.getText() + "||" + r.getOrdreIndice() + "/" + r.getNbPointPerdu() );
								writerTextReponse.close();
							}
							catch (Exception e)
							{
								e.printStackTrace();
							}
						}
						try
						{
							Path sourceDir = Paths.get( "../data/ressources_notions_questions/temp" );
							Path destDir   = Paths.get( "../data/ressources_notions_questions/" + this.ressource.getCode() + "/" + this.nom + "/question_" + (this.lstQuestions.size() + 1)+"/complement" );

							if (!Files.exists(sourceDir) || !Files.isDirectory(sourceDir))
								throw new IllegalArgumentException("Le répertoire source n'existe pas ou n'est pas un répertoire.");

							Files.list(sourceDir).forEach(sourceFile ->
							{
								try
								{
									Path destFile = destDir.resolve(sourceFile.getFileName());
									Files.copy(sourceFile, destFile, StandardCopyOption.REPLACE_EXISTING);
									System.out.println( "Fichier copié : " + sourceFile + " -> " + destFile );
								}
								catch (IOException e)
								{
									System.out.println( "Erreur lors de la copie du fichier : " + sourceFile + " - " + e.getMessage() );
								}
							});
						}
						catch (IOException e)
						{
							System.out.println( "Erreur lors de la copie des fichiers : " + e.getMessage() );
						}

						lstQuestions.add(elimination);
					}
					else if (question instanceof Association)
					{
						Association association = (Association) question;

						for (ReponseAssociation r : association.getLstReponses())
						{
							if (r.estAGauche())
							{
								try
								{
									PrintWriter writerTextReponse = new PrintWriter(new FileWriter( new File("../data/ressources_notions_questions/" + this.ressource.getCode() + "/" + this.nom + "/question_" + (this.lstQuestions.size() + 1) + "/text_reponse_" + indRep++ + ".rtf"), false));
									writerTextReponse.println(r.getText() + "::" + r.getReponseAssocie().getText());
									writerTextReponse.close();
								}
								catch (Exception e)
								{
									e.printStackTrace();
								}
							}
						}

						try
						{
							Path sourceDir = Paths.get( "../data/ressources_notions_questions/temp" );
							Path destDir   = Paths.get( "../data/ressources_notions_questions/" + this.ressource.getCode() + "/" + this.nom + "/question_" + (this.lstQuestions.size() + 1)+"/complement" );

							if (!Files.exists(sourceDir) || !Files.isDirectory(sourceDir)) 
								throw new IllegalArgumentException("Le répertoire source n'existe pas ou n'est pas un répertoire.");

							Files.list(sourceDir).forEach(sourceFile ->
							{
								try
								{
									Path destFile = destDir.resolve(sourceFile.getFileName());
									Files.copy(sourceFile, destFile, StandardCopyOption.REPLACE_EXISTING);
									System.out.println( "Fichier copié : " + sourceFile + " -> " + destFile );
								}
								catch (IOException e)
								{
									System.out.println( "Erreur lors de la copie du fichier : " + sourceFile + " - " + e.getMessage() );
								}
							});
						}
						catch (IOException e)
						{
							System.out.println( "Erreur lors de la copie des fichiers : " + e.getMessage() );
						}

						lstQuestions.add(association);
					}

					writerData.println(this.lstQuestions.size() + ";" +
							(indRep - 1)                        + ";" +
							question.getNbPoint()               + ";" +
							question.getClass().getSimpleName() + ";" +
							question.getStringDifficulte()      + ";" +
							question.getTimer()                 + ";" +
							question.getExplication()
					);
				}
			} catch (IOException e)
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
	 * Supprime une question de la liste des questions de la notion.
	 * 
	 * @param question
	 *            La question à supprimer.
	 * @return true si la question a été supprimée avec succès, false sinon.
	 */
	public boolean supprimerQuestion(Question question)
	{
		if (question == null)
		{
			return false;
		}

		if (!lstQuestions.contains(question))
		{
			return false;
		}

		File fileCSV = new File("../data/ressources_notions_questions/" + this.ressource.getCode() + "/" + this.nom + "/" + this.nom + ".csv");
		File fileRep = new File("../data/ressources_notions_questions/" + this.ressource.getCode() + "/" + this.nom + "/question_" + (lstQuestions.indexOf(question) + 1));

		// Supprimer la ligne et le répertoire
		Notion.supprimerLigneEtRepertoire(lstQuestions.indexOf(question) + 1, fileCSV, fileRep);

		lstQuestions.remove(question);
		return true;
	}

	public static void supprimerLigneEtRepertoire(int valeur, File fichier, File repertoireQuestion)
	{
		// Supprimer le répertoire
		supprimerRepertoireRecursif(repertoireQuestion);

		File fichierTemp = new File(fichier.getParent(), "fichier_temp.txt");
	
		try (BufferedReader br = new BufferedReader(new FileReader(fichier));
			 BufferedWriter bw = new BufferedWriter(new FileWriter(fichierTemp)))
		{
	
			String ligne;
			boolean ligneSupprimee = false;
	
			// Parcourir le fichier et écrire toutes les lignes sauf celle à supprimer
			while ((ligne = br.readLine()) != null)
			{
				String[] parts = ligne.split(";");
				if (parts.length > 0 && parts[0].matches("\\d+")) // verifie que parts[1] contient uniquement des chiffres
				{
					int numeroQuestion = Integer.parseInt(parts[0]);
					if (ligneSupprimee)
					{
						File oldDir = new File( repertoireQuestion.getParent() + "/question_" + Integer.toString(numeroQuestion)   );
						File newDir = new File( repertoireQuestion.getParent() + "/question_" + Integer.toString(numeroQuestion-1) );

						// Renommer le répertoire si besoin
						if (oldDir.exists() && !oldDir.equals(newDir))
						{
							if (newDir.exists())
								System.out.println("Erreur : le répertoire cible existe déjà : " + newDir.getPath());
							else
								if (oldDir.renameTo(newDir))
									System.out.println("Renommé : " + oldDir.getPath() + " en " + newDir.getPath());
								else
									System.out.println("Erreur lors du renommage de : " + oldDir.getPath());
						}

						bw.write((numeroQuestion - 1) + ligne.substring(ligne.indexOf(";")));
						bw.newLine();
						continue;
					}
					else if (numeroQuestion == valeur)
					{
						System.out.println("Ligne supprimée : " + ligne);
						ligneSupprimee = true;
						continue; // Directement nouvelle itération du while, sans faire le reste
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
	 * Supprime toutes les questions associées à la notion.
	 * 
	 * @return true si toutes les questions ont été supprimées avec succès,
	 *         false sinon.
	 */
	public boolean supprimerAllQuestions()
	{
		for (Question q : lstQuestions)
		{
			this.supprimerQuestion(q);
		}

		return true;
	}

	/**
	 * Recherche une question dans la liste des questions de la notion par
	 * son texte.
	 * 
	 * @param text
	 *            Le texte de la question à rechercher.
	 * @return La question trouvée, ou null si aucune question ne
	 *         correspond.
	 */
	public Question rechercherQuestion(String text)
	{
		for (Question question : lstQuestions)
		{
			if (question.getText().equals(text))
			{
				return question;
			}
		}
		return null;
	}

	public int rechercherNbQuestionDifficulte(int difficulte)
	{
		int nb = 0;
		for ( Question q : lstQuestions)
		{
			if ( q.getDifficulte() == difficulte) nb++;
		}

		return nb;
	}

	public List<Question> rechercherQuestionsDifficulte(int difficulte)
	{
		List<Question> lst = new ArrayList<>();
		for ( Question q : lstQuestions)
		{
			if ( q.getDifficulte() == difficulte) lst.add(q);
		}

		return lst;
	}
}
