package src.ihm;

import java.awt.event.*;
import javax.swing.*;
import src.Controleur;

public class FrameCreerRessource extends JFrame implements ActionListener
{
	private PanelCreerRessource 	panelC	;
	private Controleur				ctrl	;

	private JMenuItem 				retourMenu	;
	private JMenuItem 				retour		;

	public FrameCreerRessource (Controleur ctrl)
	{
		this.ctrl=ctrl;

		JMenuBar menubMaBarre = new JMenuBar(		  );
		JMenu 	 menuAcceuil  = new JMenu	("Accueil");
		JMenu 	 menuRetour   = new JMenu	("Retour" );

		this.retourMenu = new JMenuItem("Retour à l'accueil"		 );
		this.retour		= new JMenuItem("Retour à la page précédente");

		menuAcceuil.add(this.retourMenu	);
		menuRetour .add(this.retour		);

		menubMaBarre.add(menuAcceuil);
		menubMaBarre.add(menuRetour );

		this.setJMenuBar( menubMaBarre );

		this.retourMenu	.addActionListener(this);
		this.retour 	.addActionListener(this);
		
		this.setTitle   ("Creation de Ressource et de Notion"	);
		this.setSize    ( 750,750  								);

		this.panelC=new PanelCreerRessource(this.ctrl);

		this.add (this.panelC);

		this.setVisible(true);
	}

	public void actionPerformed ( ActionEvent e )
	{
		if (e.getSource().equals(this.retourMenu))
		{
			new FrameMenu (this.ctrl);
			this.dispose();
		}

		if (e.getSource().equals(this.retour))
		{
			new FrameMenu (this.ctrl);
			this.dispose();
		}
	}
}