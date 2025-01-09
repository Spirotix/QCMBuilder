package src.ihm;

import java.awt.event.*;
import javax.swing.*;
import src.Controleur;

public class FrameGenererQuestionnaire extends JFrame implements ActionListener
{
	private PanelGenererQuestionnaire panelQ	;
	private Controleur				  ctrl		;

	private JMenuItem 				retourMenu	;
	private JMenuItem 				retour		;

	public FrameGenererQuestionnaire (Controleur ctrl)
	{
		this.ctrl=ctrl;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500,600);
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

		this.setTitle   ("Creation d'un questionnaire"	);
		this.setSize    ( 500,600  						);

		this.panelQ = new PanelGenererQuestionnaire(this.ctrl, this);

		this.add (this.panelQ);

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