package src.ihm.question;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FileHandler 
{
	private JFileChooser fileChooser;
	private String		 nomFichier ;

	public FileHandler(String nomFichier) 
	{
		this.nomFichier	 = nomFichier;
		this.fileChooser = new JFileChooser();
		this.fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
			"Fichiers autorisés (Images, MP3, MP4, PDF)",
			"jpg", "jpeg", "png", "bmp", "gif", "mp3", "mp4", "pdf"));
	}

	public File chooseFile() 
	{
		int response = this.fileChooser.showOpenDialog(null);

		if (response == JFileChooser.APPROVE_OPTION) 
			return this.fileChooser.getSelectedFile();

		System.out.println("Aucun fichier sélectionné.");
		return null;
	}

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
				handleImage(file, extension);
				break;
			case "mp3": case "m34":
				handleAudio(file, extension);
				break;
			case "pdf":
				handlePdf(file, extension);
				break;
			default:
				System.out.println("Type de fichier non supporté.");
		}
	}

	private void handleImage(File file, String extension) throws IOException 
	{
		BufferedImage image = ImageIO.read(file);
		if (image == null) 
		{
			System.out.println("Le fichier sélectionné n'est pas une image valide.");
			return;
		}

		File targetDirectory = new File("../data/questions_NOUVEAU/temp");
		if (!targetDirectory.exists()) 
			targetDirectory.mkdirs();

		File outputFile = new File(targetDirectory, getRenamedFile(extension));
		ImageIO.write(image, extension, outputFile);

		System.out.println("Image enregistrée avec succès dans : " + outputFile.getAbsolutePath());
	}

	private void handleAudio(File file, String extension) throws IOException 
	{
		File targetDirectory = new File("../data/questions_NOUVEAU/temp");
		if (!targetDirectory.exists()) 
			targetDirectory.mkdirs();

		File outputFile = new File(targetDirectory, getRenamedFile(extension));
		if (!file.renameTo(outputFile))
			System.out.println("Erreur lors de la copie du fichier audio.");
		else 
			System.out.println("Fichier audio enregistré avec succès dans : " + outputFile.getAbsolutePath());
	}

	private void handlePdf(File file, String extension) throws IOException 
	{
		File targetDirectory = new File("../data/questions_NOUVEAU/temp");
		if (!targetDirectory.exists()) 
			targetDirectory.mkdirs();

		File outputFile = new File(targetDirectory, getRenamedFile(extension));
		if (!file.renameTo(outputFile))
			System.out.println("Erreur lors de la copie du fichier PDF.");
		else 
			System.out.println("Fichier PDF enregistré avec succès dans : " + outputFile.getAbsolutePath());
	}

	private String getExtension(String fileName) 
	{
		int lastDot = fileName.lastIndexOf('.');
		if (lastDot > 0 && lastDot < fileName.length() - 1) 
			return fileName.substring(lastDot + 1).toLowerCase();

		return null;
	}

	private String getRenamedFile(String extension) 
	{
		return this.nomFichier+"." + extension;
	}
}
