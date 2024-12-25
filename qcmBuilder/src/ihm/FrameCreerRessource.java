package src.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import src.Controleur;

public class FrameCreerRessource extends JFrame implements ActionListener
{
	private PanelCreerRessource 	panelC	;
	private Controleur				ctrl	;

	private JMenuItem 				retourMenu	;
	private JMenuItem 				retour		;

	public FrameCreerRessource (Controleur ctrl)
	{
		this.ctrl = ctrl;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(750, 750);
		this.setLocationRelativeTo(null);
		this.setTitle("Création de Ressource et de Notion");

		// Create menu bar
		JMenuBar menuBar = new JMenuBar();
		JMenu menuAcceuil = new JMenu("Accueil");
		JMenu menuRetour = new JMenu("Retour");

		this.retourMenu = new JMenuItem("Retour à l'accueil");
		this.retour = new JMenuItem("Retour à la page précédente");

		menuAcceuil.add(this.retourMenu);
		menuRetour.add(this.retour);

		menuBar.add(menuAcceuil);
		menuBar.add(menuRetour);

		this.setJMenuBar(menuBar);

		this.retourMenu.addActionListener(this);
		this.retour.addActionListener(this);

		// Create main panel with border and layout
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		this.panelC = new PanelCreerRessource(this.ctrl);
		mainPanel.add(this.panelC, BorderLayout.CENTER);

		this.add(mainPanel);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(this.retourMenu) || e.getSource().equals(this.retour))
		{
			new FrameMenu(this.ctrl);
			this.dispose();
		}
	}
}