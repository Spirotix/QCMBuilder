package src.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.ImageIO		;
import java.awt.image.BufferedImage	;
import java.io.File					;
import java.io.IOException			;
import src.ihm.question.PanelCreerQCMRepUnique;

public class PanelReponse extends JPanel implements ActionListener
{
	private PanelCreerQCMRepUnique 	panelQ			;
	private JButton				corbeille, importer	;
	private JTextField			contenu				;
	private JCheckBox			validation 			;
	private JFileChooser 		jfc 				;

	public PanelReponse (PanelCreerQCMRepUnique panelQ)
	{
		this.panelQ = panelQ;
		this.setLayout(new GridLayout(1,3));
		this.jfc  = new JFileChooser();

		//Initialisation
		this.corbeille 	= new JButton(new ImageIcon("../img/poubelle.PNG"	));
		this.importer	= new JButton(new ImageIcon("../img/inserer.PNG"	));

		this.contenu 	= new JTextField ();
		this.validation = new JCheckBox();

		//Insertion
		this.add (this.corbeille );
		this.add (this.contenu	 );

		JPanel panelDroite = new JPanel ();
		panelDroite.add (this.validation );
		panelDroite.add (this.importer	 );

		this.add(panelDroite);

		//ActionListener / itemListener
		this.corbeille  .addActionListener(this);
		this.contenu	.addActionListener(this);
		this.validation .addActionListener(this);
		this.importer	.addActionListener(this);

		this.setVisible(true);

	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(this.corbeille))
		{
			this.panelQ.supprimer(this);
		}

		if (e.getSource().equals(this.importer))
		{
			this.jfc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Fichiers Image", "jpg", "jpeg", "png", "bmp", "gif"));

			int response = this.jfc.showOpenDialog(null);

			if (response == JFileChooser.APPROVE_OPTION) 
			{
				try 
				{
					File selectedFile = this.jfc.getSelectedFile();

					BufferedImage image = ImageIO.read(selectedFile);
					if (image == null) 
					{
						System.out.println("Le fichier sélectionné n'est pas une image valide.");
						return;
					}

					File targetDirectory = new File("imagesReponseQCM");
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

	public boolean  getEstBonneReponse ( )	{return this.validation.isSelected();}
	

	public String getString(){return this.contenu.getText();}
}