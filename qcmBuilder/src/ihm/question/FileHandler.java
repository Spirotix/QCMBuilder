package src.ihm.question;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.*;
import java.io.IOException;

/**
 * La classe FileHandler gère les opérations de sélection et de traitement des fichiers.
 */
public class FileHandler 
{
	/**
	 * Sélecteur de fichiers.
	 */
	private JFileChooser fileChooser;
	
	/**
	 * Nom du fichier.
	 */
	private String nomFichier;

	/**
	 * Constructeur de la classe FileHandler.
	 * 
	 * @param nomFichier Le nom du fichier à traiter.
	 */
	public FileHandler(String nomFichier) 
	{
		this.nomFichier = nomFichier;
		this.fileChooser = new JFileChooser();
		this.fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
			"Fichiers autorisés (Images, MP3, MP4, PDF)",
			"jpg", "jpeg", "png", "bmp", "gif", "mp3", "mp4", "pdf"));
	}

	/**
	 * Ouvre une boîte de dialogue pour sélectionner un fichier.
	 * 
	 * @return Le fichier sélectionné, ou null si aucun fichier n'a été sélectionné.
	 */
	public File chooseFile() 
	{
		int response = this.fileChooser.showOpenDialog(null);

		if (response == JFileChooser.APPROVE_OPTION) 
			return this.fileChooser.getSelectedFile();

		System.out.println("Aucun fichier sélectionné.");
		return null;
	}

	/**
	 * Traite le fichier sélectionné en fonction de son extension.
	 * 
	 * @param file Le fichier à traiter.
	 * @throws IOException Si une erreur d'entrée/sortie se produit.
	 */
	public void handleFile(File file) throws IOException 
	{
		if (file == null) return;

		String extension = getExtension(file.getName());
		if (extension == null) 
		{
			System.out.println("Type de fichier inconnu.");
			return;
		}

		switch (extension) 
		{
			case "jpg": case "jpeg": case "png": case "bmp": case "gif":
				gererImage(file, extension); break;

			case "mp3": case "m34":
				gererAudio(file, extension); break;

			case "pdf":
				gererPDF(file, extension); break;

			default:
				System.out.println("Type de fichier non supporté.");
		}
	}

	/**
	 * Gère les fichiers image.
	 * 
	 * @param file Le fichier image à traiter.
	 * @param extension L'extension du fichier image.
	 * @throws IOException Si une erreur d'entrée/sortie se produit.
	 */
	private void gererImage(File file, String extension) throws IOException 
	{
		BufferedImage image = ImageIO.read(file);
		if (image == null) 
		{
			System.out.println("Le fichier sélectionné n'est pas une image valide.");
			return;
		}

		File targetDirectory = new File("../data/ressources_notions_questions/temp");
		if (!targetDirectory.exists()) 
			targetDirectory.mkdirs();

		File outputFile = new File(targetDirectory, renommerFichier(extension));
		ImageIO.write(image, extension, outputFile);

		System.out.println("Image enregistrée avec succès dans : " + outputFile.getAbsolutePath());
	}

	/**
	 * Gère les fichiers audio.
	 * 
	 * @param file Le fichier audio à traiter.
	 * @param extension L'extension du fichier audio.
	 * @throws IOException Si une erreur d'entrée/sortie se produit.
	 */
	private void gererAudio(File file, String extension) throws IOException 
	{
		File targetDirectory = new File("../data/ressources_notions_questions/temp");
		if (!targetDirectory.exists()) 
			 targetDirectory.mkdirs();

		File outputFile = new File(targetDirectory, renommerFichier(extension));
		if (!file.renameTo(outputFile))
			System.out.println("Erreur lors de la copie du fichier audio.");
		else 
			System.out.println("Fichier audio enregistré avec succès dans : " + outputFile.getAbsolutePath());
	}

	/**
	 * Gère les fichiers PDF.
	 * 
	 * @param file Le fichier PDF à traiter.
	 * @param extension L'extension du fichier PDF.
	 * @throws IOException Si une erreur d'entrée/sortie se produit.
	 */
	private void gererPDF(File file, String extension) throws IOException 
	{
		File targetDirectory = new File("../data/ressources_notions_questions/temp");
		if (!targetDirectory.exists()) 
			 targetDirectory.mkdirs();

		File outputFile = new File(targetDirectory, renommerFichier(extension));
		if (!file.renameTo(outputFile))
			System.out.println("Erreur lors de la copie du fichier PDF.");
		else 
			System.out.println("Fichier PDF enregistré avec succès dans : " + outputFile.getAbsolutePath());
	}

	/**
	 * Récupère l'extension d'un fichier.
	 * 
	 * @param fileName Le nom du fichier.
	 * @return L'extension du fichier, ou null si aucune extension n'est trouvée.
	 */
	public String getExtension(String fileName) 
	{
		int lastDot = fileName.lastIndexOf('.');
		if (lastDot > 0 && lastDot < fileName.length() - 1) 
			return fileName.substring(lastDot + 1).toLowerCase();

		return null;
	}

	/**
	 * Supprime tous les fichiers temporaires dans le répertoire spécifié.
	 */
	public static void supprimerFichiersTemp()
	{
		Path dir = Paths.get("../data/ressources_notions_questions/temp");

		try 
		{
			Files.list(dir).forEach(file -> 
			{
				try 
				{
					if (Files.isRegularFile(file)) 
					{
						Files.delete(file);
						System.out.println("Fichier supprimé : " + file);
					}
				} 
				catch (IOException e) 
				{
					System.out.println("Erreur lors de la suppression du fichier : " + file + " - " + e.getMessage());
				}
			});
		} 
		catch (IOException e) 
		{
			System.out.println("Erreur lors de la suppression des fichiers : " + e.getMessage());
		}
	}

	/**
	 * Renomme le fichier avec l'extension spécifiée.
	 * 
	 * @param extension L'extension du fichier.
	 * @return Le nouveau nom du fichier avec l'extension.
	 */
	private String renommerFichier(String extension) 
	{
		return this.nomFichier + "." + extension;
	}
}
