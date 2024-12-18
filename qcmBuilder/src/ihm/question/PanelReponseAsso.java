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
	private FileHandler 					fileHandlerG,fileHandlerD 			;

	public PanelReponseAsso (PanelCreerQuestionAsso panelQc, int indice)
	{
		this.panelQ = panelQ;
		this.fileHandlerG = new FileHandler("fichier_reponse_gauche"+indice);
		this.fileHandlerD = new FileHandler("fichier_reponse_droite"+indice);
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
			try 
			{
				File selectedFile = fileHandlerG.chooseFile();
				fileHandlerG.handleFile(selectedFile);
			} 
			catch (IOException ex) 
			{
				System.out.println("Erreur lors du traitement du fichier : " + ex.getMessage());
			}
		}

		if (e.getSource().equals(this.importerDroite)) 
		{
			try 
			{
				File selectedFile = fileHandlerD.chooseFile();
				fileHandlerD.handleFile(selectedFile);
			} 
			catch (IOException ex) 
			{
				System.out.println("Erreur lors du traitement du fichier : " + ex.getMessage());
			}
		}
	}

	public String toString			() {return this.contenuGauche.getText() + " : "+this.contenuDroite.getText();}
	public String getContenuGauche 	() {return this.contenuGauche.getText()										;}
	public String getContenuDroite 	() {return this.contenuDroite.getText()										;}
}