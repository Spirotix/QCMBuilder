package src.ihm.question;

import java.awt.event.*;
import javax.swing.*;
import src.Controleur;
import src.ihm.*;

public class FrameCreerElimination extends JFrame implements ActionListener
{
	private PanelCreerQuestion 		panelQ;
	private PanelCreerElim 			panel ;
	private Controleur 				ctrl 	;

	private JMenuItem 				retourMenu	;
	private JMenuItem 				retour		;

	public FrameCreerElimination (PanelCreerQuestion panelQ, Controleur ctrl, String ressource, String notion)
	{
		this.panelQ=panelQ;
		this.ctrl=ctrl;
		this.setTitle   ("Creation d'une question à élimination");
		this.setSize    ( 500,500  );
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

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
		
		

		this.panel=new PanelCreerElim(this.panelQ, this, this.ctrl, ressource, notion);

		this.add (this.panel);

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
			new FrameCreerQuestion (this.ctrl);
			this.dispose();
		}
	}


}