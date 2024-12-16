package src.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import src.Controleur;

public class PanelCreerRessource extends JPanel implements ActionListener
{
	//private Controleur ctrl
	private Controleur 	ctrl;
	private PanelRessource 		panelR;
	private PanelNotion 		panelN;
	private JButton				btnAjouterR, btnAjouterN;
	private JTextField 			textNumR, textNumN, textNomR, textNomN;

	public PanelCreerRessource (Controleur ctrl)
	{
		this.ctrl=ctrl;
		this. setLayout (new BorderLayout(30,30));

		JPanel panelHaut 	= new JPanel(new GridLayout(1,2));
		JPanel panelMilieu 	= new JPanel(new GridLayout(1,2));
		JPanel panelBas 	= new JPanel(new GridLayout(1,2));
		JPanel panelBasG	= new JPanel(new FlowLayout(5,5,5));
		JPanel panelBasD	= new JPanel(new FlowLayout(5,5,5));

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

		this.textNumR = new JTextField();
		this.textNumR.setColumns(5);
		this.textNumN = new JTextField();
		this.textNumN.setColumns(5);

		this.textNomR = new JTextField();
		this.textNomR.setColumns(10);
		this.textNomN = new JTextField();
		this.textNomN.setColumns(10);

		this.btnAjouterR.addActionListener(this);
		this.btnAjouterN.addActionListener(this);

		panelBasG.add(this.btnAjouterR);
		panelBasG.add(this.textNumR);
		panelBasG.add(this.textNomR);

		panelBasD.add(this.btnAjouterN);
		panelBasD.add(this.textNumN);
		panelBasD.add(this.textNomN);


		panelBas.add(panelBasG);
		panelBas.add(panelBasD);

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