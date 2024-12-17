package src.metier;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import src.metier.question.*;
import src.metier.reponse.*;

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
		this.nom = nom;
		this.ressource = ressource;
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
			File fileInformations = new File("../data/questions_NOUVEAU/" + this.ressource.getCode() + "/"
					+ this.nom + "/" + this.nom + ".csv");
			Scanner scInformations = new Scanner(fileInformations);

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
				File dossierComplement = new File("../data/questions_NOUVEAU/" + this.ressource.getCode() + "/"
						+ this.nom + "/question_" + (lstQuestions.size() + 1) + "/complement");
				File fileTextQuestion = new File("../data/questions_NOUVEAU/" + this.ressource.getCode() + "/"
						+ this.nom + "/question_" + (lstQuestions.size() + 1) + "/text_question.rtf");

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
						} catch (IOException e)
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
				String textQuestion = lineTextQuestion;

				String[] informations = lineInformations.split(";");

				int nReponse = Integer.parseInt(informations[0]);
				int nbReponses = Integer.parseInt(informations[1]);
				double nbPoint = Double.parseDouble(informations[2]);
				String type = informations[3];
				String sNiveau = informations[4];
				int temps = Integer.parseInt(informations[5]);
				String explication = informations[6];

				int niveau;
				switch (sNiveau)
				{
				case "TF" -> niveau = 1;
				case "F" -> niveau = 2;
				case "M" -> niveau = 3;
				case "D" -> niveau = 4;
				default -> {
					scTextQuestion.close();
					scInformations.close();
					throw new IllegalArgumentException(
							"Le niveau doit être appartenir aux options suivantes : 'TF','F','M','D'");
				}
				}

				if (type.equals("Association"))
				{
					List<ReponseAssociation> lstReponse = new ArrayList<>();

					for (int numReponse = 1; numReponse < nbReponses; numReponse++)
					{
						Scanner scReponse = new Scanner(new File("../data/questions_NOUVEAU/"
								+ this.ressource.getCode() + "/" + this.nom + "/question_"
								+ (lstQuestions.size() + 1) + "/text_reponse_" + numReponse + ".rtf"));

						String lineTextReponse = scReponse.nextLine();

						ReponseAssociation reponseA;
						String textReponseA = lineTextReponse.substring(0, lineTextReponse.indexOf("::"));

						ReponseAssociation reponseB;
						String textReponseB = lineTextReponse.substring(lineTextReponse.indexOf("::") + 2);

						reponseA = new ReponseAssociation(textReponseA, null, false);
						lstReponse.add(reponseA);

						reponseB = new ReponseAssociation(textReponseB, reponseA, false);
						reponseA.setReponseAssocie(reponseB);

						lstReponse.add(reponseB);

						scReponse.close();
					}

					Association question = new Association(this, textQuestion, temps, nbPoint, niveau, lstReponse,
							explication);
					lstQuestions.add(question);
				}
				else if (type.equals("Elimination"))
				{
					List<ReponseElimination> lstReponse = new ArrayList<>();

					int nbIndice = 0;

					for (int numReponse = 1; numReponse < nbReponses; numReponse++)
					{
						Scanner scReponse = new Scanner(new File("../data/questions_NOUVEAU/"
								+ this.ressource.getCode() + "/" + this.nom + "/question_"
								+ (lstQuestions.size() + 1) + "/text_reponse_" + numReponse + ".rtf"));

						String lineTextReponse = scReponse.nextLine();

						lstReponse.add(new ReponseElimination(
								lineTextReponse.substring(lineTextReponse.indexOf("} ") + 2,
										lineTextReponse.indexOf("|")),
								lineTextReponse.substring(lineTextReponse.indexOf("|") + 1,
										lineTextReponse.indexOf("||")),
								Integer.parseInt(lineTextReponse.substring(lineTextReponse.indexOf("||") + 2,
										lineTextReponse.indexOf("/"))),
								Double.parseDouble(lineTextReponse.substring(lineTextReponse.indexOf("/") + 1))));

						if (nbIndice < Integer.parseInt(lineTextReponse.substring(lineTextReponse.indexOf("||") + 2,
								lineTextReponse.indexOf("/"))))
						{
							nbIndice = Integer.parseInt(lineTextReponse.substring(lineTextReponse.indexOf("||") + 2,
									lineTextReponse.indexOf("/")));
						}

						scReponse.close();
					}

					Elimination question = new Elimination(this, textQuestion, temps, nbPoint, niveau, lstReponse,
							nbIndice, explication);
					lstQuestions.add(question);
				}
				else if (type.equals("QCM"))
				{
					List<ReponseQCM> lstReponse = new ArrayList<>();

					for (int numReponse = 1; numReponse < nbReponses; numReponse++)
					{
						Scanner scReponse = new Scanner(new File("../data/questions_NOUVEAU/"
								+ this.ressource.getCode() + "/" + this.nom + "/question_"
								+ (lstQuestions.size() + 1) + "/text_reponse_" + numReponse + ".rtf"));

						String lineTextReponse = scReponse.nextLine();

						lstReponse.add(new ReponseQCM(
								lineTextReponse.substring(lineTextReponse.indexOf("} ") + 2,
										lineTextReponse.indexOf("|")),
								lineTextReponse.substring(lineTextReponse.indexOf("|") + 1)));
						scReponse.close();
					}

					QCM question = new QCM(this, textQuestion, temps, nbPoint, niveau, lstReponse, explication);
					lstQuestions.add(question);
				}
				else
				{
					scTextQuestion.close();
					scInformations.close();
					throw new IllegalArgumentException(
							"Le type doit être appartenir aux options suivantes : 'Association','Elimination','QCM'");
				}
				scTextQuestion.close();
			}
			scInformations.close();
		} catch (FileNotFoundException e)
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
				File fileTextQuestion = new File("../data/questions_NOUVEAU/" + this.ressource.getCode() + "/"
						+ this.nom + "/question_" + (this.lstQuestions.size() + 1) + "/text_question.rtf");
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

				File dossierComplement = new File("../data/questions_NOUVEAU/" + this.ressource.getCode() + "/"
						+ this.nom + "/question_" + (this.lstQuestions.size() + 1) + "/complement");
				dossierComplement.mkdirs();

				File fileData = new File("../data/questions_NOUVEAU/" + this.ressource.getCode() + "/" + this.nom
						+ "/" + this.nom + ".csv");

				try (PrintWriter writerTextQuestion = new PrintWriter(new FileWriter(fileTextQuestion, false));
						PrintWriter writerData = new PrintWriter(new FileWriter(fileData, true)))
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
								PrintWriter writerTextReponse = new PrintWriter(new FileWriter(
										new File("../data/questions_NOUVEAU/" + this.ressource.getCode() + "/"
												+ this.nom + "/question_" + (this.lstQuestions.size() + 1)
												+ "/text_reponse_" + indRep++ + ".rtf"),
										false));
								writerTextReponse.println(r.getText() + "|" + (r.estVrai() ? "Vrai" : "Faux"));
								writerTextReponse.close();
							} catch (Exception e)
							{
								e.printStackTrace();
							}
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
								PrintWriter writerTextReponse = new PrintWriter(new FileWriter(
										new File("../data/questions_NOUVEAU/" + this.ressource.getCode() + "/"
												+ this.nom + "/question_" + (this.lstQuestions.size() + 1)
												+ "/text_reponse_" + indRep++ + ".rtf"),
										false));
								writerTextReponse.println((r.estVrai() ? "Vrai" : "Faux") + "|" + r.getText() + "||"
										+ r.getOrdreIndice() + "/" + r.getNbPointPerdu());
								writerTextReponse.close();
							} catch (Exception e)
							{
								e.printStackTrace();
							}
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
									PrintWriter writerTextReponse = new PrintWriter(new FileWriter(
											new File("../data/questions_NOUVEAU/" + this.ressource.getCode() + "/"
													+ this.nom + "/question_" + (this.lstQuestions.size() + 1)
													+ "/text_reponse_" + indRep++ + ".rtf"),
											false));
									writerTextReponse.println(r.getText() + "::" + r.getReponseAssocie().getText());
									writerTextReponse.close();
								} catch (Exception e)
								{
									e.printStackTrace();
								}
							}
						}

						lstQuestions.add(association);
					}

					writerData.println(this.lstQuestions.size() + ";" + (indRep - 1) + ";" + question.getNbPoint()
							+ ";" + question.getClass().getSimpleName() + ";" + question.getStringDifficulte() + ";"
							+ question.getTimer() + ";" + question.getExplication());
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

		File fileCSV = new File(
				"../data/questions_NOUVEAU/" + this.ressource.getCode() + "/" + this.nom + "/" + this.nom + ".csv");

		Notion.supprimerLigne(fileCSV, lstQuestions.indexOf(question) + 1);
		Notion.supprimerRepertoire(new File("../data/questions_NOUVEAU/" + this.ressource.getCode() + "/" + this.nom
				+ "/question_" + (lstQuestions.indexOf(question) + 1)));

		lstQuestions.remove(question);
		return true;
	}

	/**
	 * Supprime un répertoire et son contenu de manière récursive.
	 * 
	 * @param repertoire
	 *            Le répertoire à supprimer.
	 */
	public static void supprimerRepertoire(File repertoire)
	{
		if (repertoire.exists())
		{
			if (repertoire.isDirectory())
			{
				File[] fichiers = repertoire.listFiles();
				if (fichiers != null)
				{
					for (File fichier : fichiers)
					{
						supprimerRepertoire(fichier);
					}
				}
			}
			if (repertoire.delete())
			{
				System.out.println("Supprimé : " + repertoire.getAbsolutePath());
			}
			else
			{
				System.out.println("Impossible de supprimer : " + repertoire.getAbsolutePath());
			}
		}
	}

	/**
	 * Supprime une ligne spécifique d'un fichier.
	 * 
	 * @param fichier
	 *            Le fichier à modifier.
	 * @param valeur
	 *            La valeur de la ligne à supprimer.
	 */
	public static void supprimerLigne(File fichier, int valeur)
	{
		List<String> lignes = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(fichier)))
		{
			String ligne;
			while ((ligne = br.readLine()) != null)
			{
				if (!ligne.isEmpty() && ligne.charAt(0) == (char) (valeur + '0'))
				{
					System.out.println("Ligne supprimée : " + ligne);
				}
				else
				{
					lignes.add(ligne);
				}
			}
		} catch (IOException e)
		{
			System.out.println("Erreur de lecture : " + e.getMessage());
			return;
		}

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(fichier, false)))
		{
			for (String ligne : lignes)
			{
				bw.write(ligne);
				bw.newLine();
			}
		} catch (IOException e)
		{
			System.out.println("Erreur d'écriture : " + e.getMessage());
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
		File fileTextQuestion = new File("./data/questions/" + this.ressource.getCode() + "_"
				+ this.ressource.getNom() + "_" + this.nom + ".rtf");
		File fileInformations = new File("./data/questions/" + this.ressource.getCode() + "_"
				+ this.ressource.getNom() + "_" + this.nom + "_data.rtf");

		if (fileTextQuestion.exists())
		{
			fileTextQuestion.delete();
		}
		else
		{
			return false;
		}

		if (fileInformations.exists())
		{
			fileInformations.delete();
		}
		else
		{
			return false;
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
}
