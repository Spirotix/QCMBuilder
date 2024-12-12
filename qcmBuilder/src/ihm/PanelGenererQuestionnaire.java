package src.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import src.Controleur;

public class PanelGenererQuestionnaire extends JPanel implements ActionListener, ItemListener
{
	//private Controleur ctrl
	private Controleur 	ctrl			;
	private Choice 				choixRessource	;
	private ButtonGroup			grpOuiNon		;
	private JRadioButton		btnOui, btnNon	;

	public PanelGenererQuestionnaire (Controleur ctrl)
	{
		this.ctrl=ctrl;
		this.setLayout(new GridLayout (10,1));

		/*
		 * Definition des composants
		 */
		this.choixRessource = new Choice();
		this.choixRessource.add(" ");
		for (String s : this.ctrl.getChoixRessources())
			this.choixRessource.add(s);
		
		this.btnOui = new JRadioButton("oui");
		this.btnNon = new JRadioButton("non");

		this.grpOuiNon = new ButtonGroup();
		this.grpOuiNon.add(this.btnOui);
		this.grpOuiNon.add(this.btnNon);

		/*
		 * Definition des panels
		 */
		JPanel ligne1, gauche,droite, droite2;

		gauche = new JPanel(new BorderLayout());
		gauche.add(new JLabel("ressource"),BorderLayout.NORTH);
		gauche.add(this.choixRessource,BorderLayout.CENTER);

		droite = new JPanel(new GridLayout(2,1));
		droite.add(this.btnOui);
		droite.add(this.btnNon);

		droite2 = new JPanel(new GridLayout(1,2));
		droite2.add(new JLabel(new ImageIcon("img/chrono.PNG")));
		droite2.add(droite);

		ligne1 = new JPanel(new GridLayout(1,2));
		ligne1.add(gauche);
		ligne1.add(droite2);

		/*
		 * Ajout des actionListeners/ itemListener
		 */
		this.choixRessource	.addItemListener	(this);
		this.btnOui 		.addActionListener	(this);
		this.btnNon 		.addActionListener	(this);

		/*
		 * Placements
		 */
		this.add(ligne1);
		this.add(new JPanel());
		this.add(new JPanel());
		this.add(new JPanel());
		this.add(new JPanel());
		this.add(new JPanel());
		this.add(new JPanel());
		this.add(new JPanel());
		this.add(new JPanel());
		this.add(new JPanel());

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) 
	{

	}

	public void itemStateChanged(ItemEvent e) 
	{

	}
}