package src.ihm;

import javax.swing.*;
import java.awt.event.*;
import src.Controleur;

public class FrameListerQuestion extends JFrame implements ActionListener
{
	private PanelListerQuestion 	panelC	;
	private Controleur				ctrl	;

	private JMenuItem 				retourMenu	;
	private JMenuItem 				retour		;

	public FrameListerQuestion (Controleur ctrl)
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
		
		this.setTitle   ("Liste des question");
		this.setSize    ( 750,350  			 );

		this.panelC=new PanelListerQuestion(this.ctrl);

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