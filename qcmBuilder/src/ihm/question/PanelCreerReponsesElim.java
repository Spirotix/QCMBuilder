package src.ihm.question;

import javax.imageio.ImageIO		;
import java.io.File					;
import java.io.IOException			;
import src.ihm.*					;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PanelCreerReponsesElim extends JPanel implements ActionListener
{
	private PanelCreerElim 		panelQ				;
	private JButton				corbeille, importer ;
	private JTextField			ordre, cout, contenu;
	private JCheckBox			validation 			; 
	private FileHandler 		fileHandler			;
	private File 				fileChoisi 			;
	private int 				indice				;
	private JPanel 				panelImage 			;
	private JLabel 				imageImporter		;


	public PanelCreerReponsesElim (PanelCreerElim panelQ, int indice)
	{
		this.panelQ = panelQ;
		this.indice = indice ;

		this.imageImporter 	= new JLabel	  (							);
		this.fileHandler 	= new FileHandler ("fichier_reponse"+indice );

		this.setLayout(new GridLayout(1,4));

		//Initialisation
		this.corbeille 	= new JButton(new ImageIcon("../img/poubelle.PNG")	);
		this.importer	= new JButton(new ImageIcon("../img/inserer.PNG")	);
		this.contenu 	= new TextFieldPerso ("contenu"						);
		this.ordre 		= new TextFieldPerso ("ordre d'élimination"			);
		this.cout 		= new TextFieldPerso ("points perdus"				);
		this.validation = new JCheckBox  ();

		//Insertion
		JPanel panelGauche = new JPanel ();
		panelGauche.add(this.corbeille);

		this.add(panelGauche);
		this.add (this.contenu	 );

		this.ordre.setColumns(10);
		this.cout.setColumns(10);

		JPanel temp = new JPanel();
		temp.setLayout(new GridLayout(2,1));
		
		temp.add (this.ordre);
		temp.add (this.cout );

		this.add(temp);
		JPanel panelDroite	 	= new JPanel (new BorderLayout());
		JPanel panelDroiteHaut 	= new JPanel ();
		this.panelImage	= new JPanel ();
		this.panelImage.setPreferredSize(new Dimension(75, 75));
		
		panelDroiteHaut	.add (this.validation						);
		panelDroiteHaut	.add (this.importer							);
		panelDroite		.add (panelDroiteHaut, BorderLayout.NORTH	);
		this.panelImage .add (this.imageImporter					);
		panelDroite		.add (this.panelImage, BorderLayout.CENTER	);

		this.add(panelDroite);
		

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
		this.updateImage();
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

		if (e.getSource().equals(this.validation))
		{
			if (this.validation.isSelected())
			{
				this.panelQ		.toutDecocher(		);
				this.validation	.setSelected (true	);
				this.cout .setEnabled(false);
				this.ordre.setEnabled(false);
			}
		}

		if (e.getSource().equals(this.corbeille))
		{
			this.panelQ.supprimer(this);
		}

		if (e.getSource().equals(this.importer)) 
		{
			try 
			{
				this.fileChoisi = fileHandler.chooseFile();
				fileHandler.handleFile(this.fileChoisi);
				this.updateImage();
			} 
			catch (IOException ex) 
			{
				System.out.println("Erreur lors du traitement du fichier : " + ex.getMessage());
			}
		}
	}

	public void updateImage()
	{
		if (this.fileChoisi==null)
			return ;
		try 
		{
			Image image 		 = ImageIO.read(new File("../data/ressources_notions_questions/temp/fichier_reponse"+this.indice+"."+this.fileHandler.getExtension(this.fileChoisi.getName())));
			Image imageRetaillee = image.getScaledInstance( this.panelImage.getHeight(), this.panelImage.getHeight(), Image.SCALE_AREA_AVERAGING);

			this.imageImporter.setIcon(new ImageIcon(imageRetaillee));
			System.out.println(this.imageImporter.getIcon());
		} 
		catch (IOException ex) 
		{
			System.out.println("Erreur lors du traitement du fichier : " + ex.getMessage());
		}
		this.repaint();
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
			//System.out.println("Ce n'est pas un entier (ordre)");
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
			//System.out.println("Ce n'est pas un entier (cout)");
			return 0;
		}
	}

	public void decocher()
	{
		this.validation.setSelected(false);
		this.cout .setEnabled(true);
		this.ordre.setEnabled(true);
	}

	public boolean  getEstBonneReponse ( )	{return this.validation.isSelected();}

}