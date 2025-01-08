package src.ihm.question;

import java.awt.event.*;
import javax.swing.*;
import src.Controleur;
import src.ihm.*;

public class FrameCreerAssociation extends JFrame implements ActionListener
{
	private PanelCreerQuestion 		panelQ		;
	private PanelCreerQuestionAsso 	panel 		;
	private Controleur 				ctrl 		;

	private JMenuItem 				retourMenu	;
	private JMenuItem 				retour		;

	public FrameCreerAssociation (PanelCreerQuestion panelQ, Controleur ctrl)
	{
		this.panelQ=panelQ;
		this.ctrl=ctrl;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(750,350);
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
		
		this.setTitle   ("Creation d'une Question par association");
		this.setSize    ( 750,500  );

		this.panel=new PanelCreerQuestionAsso(this.panelQ, this, this.ctrl);

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