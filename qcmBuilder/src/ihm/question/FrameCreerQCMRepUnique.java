package src.ihm.question;

import javax.swing.*;
import java.awt.event.*;
import src.ihm.*;
import src.Controleur;

public class FrameCreerQCMRepUnique extends JFrame implements ActionListener
{
	private PanelCreerQuestion 		panelQ	;
	private PanelCreerQCMRepUnique 	panel 	;
	private Controleur 				ctrl 	;

	private JMenuItem retourMenu;
	private JMenuItem retour;

	public FrameCreerQCMRepUnique (PanelCreerQuestion panelQ, Controleur ctrl )
	{
		this.panelQ	= panelQ;
		this.ctrl	= ctrl;

		this.setTitle   ("Creation d'un QCM a choix unique");
		this.setSize    ( 500,500  );

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


		this.panel=new PanelCreerQCMRepUnique(this.panelQ);

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