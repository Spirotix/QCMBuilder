package src.ihm.question;

import javax.imageio.ImageIO		;
import java.awt.image.BufferedImage	;
import java.io.File					;
import java.io.IOException			;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PanelCreerReponsesElim extends JPanel implements ActionListener
{
	private PanelCreerElim 		panelQ;
	private JButton				corbeille, importer;
	private JTextField			ordre, cout, contenu	;
	private JCheckBox			validation ; 
	private JFileChooser 		jfc;

	public PanelCreerReponsesElim (PanelCreerElim panelQ)
	{
		this.panelQ = panelQ;
		this.jfc  = new JFileChooser();
		this.setLayout(new GridLayout(1,4));

		//Initialisation
		this.corbeille = new JButton(new ImageIcon("../img/poubelle.PNG"));
		this.importer	= new JButton(new ImageIcon("../img/inserer.PNG"	));
		this.contenu 	= new JTextField ();
		this.ordre 		= new JTextField ();
		this.cout 		= new JTextField ();
		this.validation = new JCheckBox();

		//Insertion
		this.add (this.corbeille );
		this.add (this.contenu	 );

		this.ordre.setColumns(10);
		this.cout.setColumns(10);

		JPanel temp = new JPanel();
		temp.setLayout(new GridLayout(2,1));
		
		temp.add (this.ordre	 );
		temp.add (this.cout);

		this.add(temp);
		JPanel panelDroite = new JPanel ();
		panelDroite.add (this.validation );
		panelDroite.add (this.importer	 );
		this.add (panelDroite);
		

		//ActionListener / itemListener
		this.corbeille  .addActionListener(this);
		this.contenu	.addActionListener(this);
		this.validation .addActionListener(this);
		this.ordre		.addActionListener(this);
		this.cout 		.addActionListener(this);
		this.importer	.addActionListener(this);

		this.setVisible(true);

	}

	public void actionPerformed(ActionEvent e)
	{
		if (this.validation.isSelected())
		{
			this.cout .setText("");
			this.ordre.setText("");

			this.cout .setEnabled(false);
			this.ordre.setEnabled(false);
		}
		else 
		{
			this.cout .setEnabled(true);
			this.ordre.setEnabled(true);
		}

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

					File targetDirectory = new File("imagesReponseElim");
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

	public String getString(){return this.contenu.getText();}
	public int    getOrdre()
	{
		int rtnOrdre;

		try 
		{
			rtnOrdre = Integer.parseInt(this.ordre.getText());
			return rtnOrdre;
		} 
		catch (Exception ex) 
		{
			System.out.println("Ce n'est pas un entier (ordre)");
			return 0;
		}
	}

	public int    getCout()
	{
		int rtnCout;

		try 
		{
			rtnCout = Integer.parseInt(this.cout.getText());
			return rtnCout;
		} 
		catch (Exception ex) 
		{
			System.out.println("Ce n'est pas un entier (cout)");
			return 0;
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

}