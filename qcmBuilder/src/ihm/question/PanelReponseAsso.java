package src.ihm.question;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.imageio.ImageIO		;
import java.io.File					;
import java.io.IOException			;
import src.ihm.*;

public class PanelReponseAsso extends JPanel implements ActionListener
{
	private PanelCreerQuestionAsso 	panelQ										;
	private JButton					corbeille, importerGauche, importerDroite	;
	private JLabel 					imageImporterG, imageImporterD				;
	private TextFieldPerso			contenuGauche  , contenuDroite				;
	private FileHandler 			fileHandlerG,fileHandlerD 					;
	private File 					fileChoisiG, fileChoisiD 					;
	private int 					indice										;
	private JPanel 					panelImageG,panelImageD 					;

	private String 					cheminG, cheminD							;

	public PanelReponseAsso (PanelCreerQuestionAsso panelQc, int indice)
	{
		this.panelQ = panelQ;
		this.indice = indice ;

		this.imageImporterG = new JLabel	  ();
		this.imageImporterD = new JLabel	  ();

		this.fileHandlerG = new FileHandler("fichier_reponse_gauche"+indice);
		this.fileHandlerD = new FileHandler("fichier_reponse_droite"+indice);
		//Initialisation
		JPanel panelGauche = new JPanel();
		JPanel panelDroite = new JPanel();

		this.corbeille		= new JButton(new ImageIcon("../img/poubelle.PNG")	);
		this.importerGauche	= new JButton(new ImageIcon("../img/inserer.PNG" )	);
		this.importerDroite	= new JButton(new ImageIcon("../img/inserer.PNG" )	);
		this.contenuGauche 	= new TextFieldPerso ("contenu"						);
		this.contenuGauche.setColumns(15);
		this.contenuDroite 	= new TextFieldPerso ("contenu"						);
		this.contenuDroite.setColumns(15);

		this.panelImageG = new JPanel ();
		this.panelImageG.setPreferredSize(new Dimension(75, 75));

		this.panelImageD = new JPanel ();
		this.panelImageD.setPreferredSize(new Dimension(75, 75));

		this.panelImageG.add(this.imageImporterG);
		this.panelImageD.add(this.imageImporterD);

		this.add(this.panelImageG		);
		this.add(this.corbeille			);
		this.add(this.contenuGauche		);
		this.add(this.importerGauche	);
		this.add(this.importerDroite	);

		this.corbeille		.addActionListener(this);
		this.importerGauche	.addActionListener(this);
		this.importerDroite	.addActionListener(this);

		this.add(this.contenuDroite);
		this.add(this.panelImageD		);


		this.setVisible(true);

	}

	public void actionPerformed(ActionEvent e)
	{
		this.updateImageG();
		this.updateImageD();
		if (e.getSource().equals(this.corbeille))
			this.panelQ.supprimer(this);

		if (e.getSource().equals(this.importerGauche)) 
		{
			
			try 
			{
				this.fileChoisiG = fileHandlerG.chooseFile();
				this.cheminG = this.fileChoisiG.getAbsolutePath();
				fileHandlerG.handleFile(this.fileChoisiG);
				this.updateImageG();
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
				this.fileChoisiD = fileHandlerD.chooseFile();
				this.cheminD = this.fileChoisiD.getAbsolutePath();
				fileHandlerD.handleFile(this.fileChoisiD);
				this.updateImageD();
			} 
			catch (IOException ex) 
			{
				System.out.println("Erreur lors du traitement du fichier : " + ex.getMessage());
			}
		}
	}

	public void updateImageG()
	{
		if (this.fileChoisiG==null)
			return ;
		try 
		{
			Image image 		 = ImageIO.read(new File("../data/ressources_notions_questions/temp/fichier_reponse_gauche"+this.indice+"."+this.fileHandlerG.getExtension(this.fileChoisiG.getName())));
			Image imageRetaillee = image.getScaledInstance( this.panelImageG.getHeight(), this.panelImageG.getHeight(), Image.SCALE_AREA_AVERAGING);

			this.imageImporterG.setIcon(new ImageIcon(imageRetaillee));
			System.out.println(this.imageImporterG.getIcon());
		} 
		catch (IOException ex) 
		{
			System.out.println("Erreur lors du traitement du fichier : " + ex.getMessage());
		}
		this.repaint();
	}

	public void updateImageD()
	{
		if (this.fileChoisiD==null)
			return ;
		try 
		{
			Image image 		 = ImageIO.read(new File("../data/ressources_notions_questions/temp/fichier_reponse_droite"+this.indice+"."+this.fileHandlerD.getExtension(this.fileChoisiD.getName())));
			Image imageRetaillee = image.getScaledInstance( this.panelImageD.getHeight(), this.panelImageD.getHeight(), Image.SCALE_AREA_AVERAGING);

			this.imageImporterD.setIcon(new ImageIcon(imageRetaillee));
			System.out.println(this.imageImporterD.getIcon());
		} 
		catch (IOException ex) 
		{
			System.out.println("Erreur lors du traitement du fichier : " + ex.getMessage());
		}
		this.repaint();
	}

	public String getCheminG(){ return this.cheminG; }
	
	public String getCheminD(){ return this.cheminD; }

	public String toString			() {return this.contenuGauche.getText() + " : "+this.contenuDroite.getText();}
	public String getContenuGauche 	() {return this.contenuGauche.getText()										;}
	public String getContenuDroite 	() {return this.contenuDroite.getText()										;}
}