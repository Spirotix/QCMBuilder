//package src.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import src.Controleur;

public class PanelCreerRessource extends JPanel implements ActionListener
{
	//private Controleur ctrl
	private TestCreerQuestion 	ctrl;
	private PanelRessource 		panelR;
	private PanelNotion 		panelN;
	private JButton				btnAjouterR, btnAjouterN;

	public PanelCreerRessource (TestCreerQuestion ctrl)
	{
		this.ctrl=ctrl;
		this. setLayout (new BorderLayout(30,30));

		JPanel panelHaut 	= new JPanel(new GridLayout(1,2));
		JPanel panelMilieu 	= new JPanel(new GridLayout(1,2));
		JPanel panelBas 	= new JPanel(new GridLayout(1,2));

		panelHaut.add(new JLabel("Choix de la Ressource"));
		panelHaut.add(new JLabel("Choix de la notion"	));

		this.add(panelHaut, BorderLayout.NORTH);

		this.panelR = new PanelRessource (ctrl, this);
		this.panelN = new PanelNotion 	 (ctrl, this);

		panelMilieu.add (this.panelR);
		panelMilieu.add (this.panelN);

		this.add(panelMilieu, BorderLayout.CENTER);

		this.btnAjouterR = new JButton("ajouter");
		this.btnAjouterN = new JButton("ajouter");

		this.btnAjouterR.addActionListener(this);
		this.btnAjouterN.addActionListener(this);

		panelBas.add(this.btnAjouterR);
		panelBas.add(this.btnAjouterN);

		this.add(panelBas, BorderLayout.SOUTH);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource().equals(this.btnAjouterN))
			this.panelN.ajouter("blablabla");

		if (e.getSource().equals(this.btnAjouterR))
			this.panelR.ajouter("blablabla");
	}

	public PanelRessource getPanelRessource	() {return this.panelR;}
	public PanelNotion    getPanelNotion	() {return this.panelN;}
}