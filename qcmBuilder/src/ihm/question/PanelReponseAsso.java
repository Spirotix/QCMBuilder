//package src.ihm;
package src.ihm.question;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.imageio.ImageIO		;
import java.awt.image.BufferedImage	;
import java.io.File					;
import java.io.IOException			;

public class PanelReponseAsso extends JPanel implements ActionListener
{
	private PanelCreerQuestionAsso 			panelQ;
	private JButton							corbeille, importerGauche, importerDroite;
	private JTextArea						contenuGauche  , contenuDroite	;
	private JFileChooser 					jfcG, jfcD ;

	public PanelReponseAsso (PanelCreerQuestionAsso panelQ)
	{
		this.panelQ = panelQ;
		this.jfcG  = new JFileChooser();
		this.jfcD  = new JFileChooser();

		//Initialisation
		JPanel panelGauche = new JPanel();
		JPanel panelDroite = new JPanel();

		this.corbeille		= new JButton(new ImageIcon("../img/poubelle.PNG"));
		this.importerGauche	= new JButton(new ImageIcon("../img/inserer.PNG" ));
		this.importerDroite	= new JButton(new ImageIcon("../img/inserer.PNG" ));
		this.contenuGauche 	= new JTextArea (2,15);
		this.contenuDroite 	= new JTextArea (2,15);

		this.add(this.corbeille);
		this.add(this.contenuGauche  );
		this.add(this.importerGauche);
		this.add(this.importerDroite);

		this.corbeille		.addActionListener(this);
		this.importerGauche	.addActionListener(this);
		this.importerDroite	.addActionListener(this);

		this.add(this.contenuDroite);


		this.setVisible(true);

	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(this.corbeille))
			this.panelQ.supprimer(this);
		if (e.getSource().equals(this.importerGauche))
		{
			this.jfcG.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Fichiers Image", "jpg", "jpeg", "png", "bmp", "gif"));

			int response = this.jfcG.showOpenDialog(null);

			if (response == JFileChooser.APPROVE_OPTION) 
			{
				try 
				{
					File selectedFile = this.jfcG.getSelectedFile();

					BufferedImage image = ImageIO.read(selectedFile);
					if (image == null) 
					{
						System.out.println("Le fichier sélectionné n'est pas une image valide.");
						return;
					}

					File targetDirectory = new File("imagesReponseAssoGauche");
					if (!targetDirectory.exists()) 
						targetDirectory.mkdirs();

					File outputFile = new File(targetDirectory, selectedFile.getName());

					String formatName = getExtension(selectedFile.getName());
					if (formatName == null)
						formatName = "png"; 

					ImageIO.write(image, formatName, outputFile);
					System.out.println("Image enregistrée avec succès dans : " + outputFile.getAbsolutePath());
				} 
				catch (IOException ex) 
				{
					System.out.println("Erreur lors de la lecture ou de l'enregistrement de l'image : " + ex.getMessage());
				}
			} 
			else
				System.out.println("Aucune image sélectionnée.");
		}
		if (e.getSource().equals(this.importerDroite))
		{
			this.jfcD.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Fichiers Image", "jpg", "jpeg", "png", "bmp", "gif"));

			int response = this.jfcD.showOpenDialog(null);

			if (response == JFileChooser.APPROVE_OPTION) 
			{
				try 
				{
					File selectedFile = this.jfcD.getSelectedFile();

					BufferedImage image = ImageIO.read(selectedFile);
					if (image == null) 
					{
						System.out.println("Le fichier sélectionné n'est pas une image valide.");
						return;
					}

					File targetDirectory = new File("imagesReponseAssoDroite");
					if (!targetDirectory.exists()) 
						targetDirectory.mkdirs();

					File outputFile = new File(targetDirectory, selectedFile.getName());

					String formatName = getExtension(selectedFile.getName());
					if (formatName == null)
						formatName = "png"; 

					ImageIO.write(image, formatName, outputFile);
					System.out.println("Image enregistrée avec succès dans : " + outputFile.getAbsolutePath());
				} 
				catch (IOException ex) 
				{
					System.out.println("Erreur lors de la lecture ou de l'enregistrement de l'image : " + ex.getMessage());
				}
			} 
			else
				System.out.println("Aucune image sélectionnée.");
		}
	}

	private static String getExtension(String fileName) 
	{
		int lastDot = fileName.lastIndexOf('.');
		if (lastDot > 0 && lastDot < fileName.length() - 1) 
			return fileName.substring(lastDot + 1).toLowerCase();
		return null;
	}

	public String toString			() {return this.contenuGauche.getText() + " : "+this.contenuDroite.getText();}
	public String getContenuGauche 	() {return this.contenuGauche.getText()										;}
	public String getContenuDroite 	() {return this.contenuDroite.getText()										;}
}