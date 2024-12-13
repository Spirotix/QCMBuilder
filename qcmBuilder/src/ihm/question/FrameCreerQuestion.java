package src.ihm.question;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import src.ihm.*;
import src.Controleur;

public class FrameCreerQuestion extends JFrame implements ActionListener
{
	private Controleur 			ctrl;
	private PanelCreerQuestion 	panelQ;

	private JMenuItem retourMenu;
	private JMenuItem retour;

	public FrameCreerQuestion (Controleur ctrl)
	{
		this.ctrl=ctrl;

		this.setTitle   ("Creation de question");
		this.setSize    ( 730,500  );
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setBackground(Color.LIGHT_GRAY);


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

		this.panelQ=new PanelCreerQuestion(this.ctrl, this);

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