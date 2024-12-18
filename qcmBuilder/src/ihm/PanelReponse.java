package src.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.ImageIO		;
import java.awt.image.BufferedImage	;
import java.io.File					;
import java.io.IOException			;
import src.ihm.question.*;

public class PanelReponse extends JPanel implements ActionListener
{
	private PanelCreerQCMRepUnique	panelQ				;
	private JButton					corbeille, importer	;
	private JTextField				contenu				;
	private JCheckBox				validation 			;
	private String 					type 				;
	private FileHandler 			fileHandler			;

	public PanelReponse (PanelCreerQCMRepUnique panelQ, String type, int indice)
	{
		this.panelQ = panelQ;
		this.type 	= type 	;
		this.fileHandler = new FileHandler("fichier_reponse"+indice);

		this.setLayout(new GridLayout(1,3));

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

		if (e.getSource().equals(this.validation))
		{
			if (this.type.equals("Unique"))
			{
				if (this.validation.isSelected())
				{
					this.panelQ		.toutDecocher(		);
					this.validation	.setSelected (true	);
				}
			}
		}

		if (e.getSource().equals(this.importer)) 
		{
			try 
			{
				File selectedFile = fileHandler.chooseFile();
				fileHandler.handleFile(selectedFile);
			} 
			catch (IOException ex) 
			{
				System.out.println("Erreur lors du traitement du fichier : " + ex.getMessage());
			}
		}
	}

	public void decocher()
	{
		this.validation.setSelected(false);
	}


	public boolean  getEstBonneReponse ( )	{return this.validation.isSelected();}
	

	public String getString(){return this.contenu.getText();}
}