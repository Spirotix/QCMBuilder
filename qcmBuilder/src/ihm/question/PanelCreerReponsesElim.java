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
	private PanelCreerElim 		panelQ				;
	private JButton				corbeille, importer ;
	private JTextField			ordre, cout, contenu;
	private JCheckBox			validation 			; 
	private FileHandler 		fileHandler			;

	public PanelCreerReponsesElim (PanelCreerElim panelQ, int indice)
	{
		this.panelQ = panelQ;
		this.fileHandler = new FileHandler("fichier_reponse"+indice);
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
				File selectedFile = fileHandler.chooseFile();
				fileHandler.handleFile(selectedFile);
			} 
			catch (IOException ex) 
			{
				System.out.println("Erreur lors du traitement du fichier : " + ex.getMessage());
			}
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