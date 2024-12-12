//package src.ihm;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
//import src.Controleur;

public class PanelCreerQuestionnaire extends JPanel implements ActionListener, ItemListener
{
	//private Controleur ctrl
	private TestCreerQuestion 	ctrl			;
	private Choice 				choixRessource	;
	private ButtonGroup			grpOuiNon		;
	private JRadioButton		btnOui, btnNon	;
	private JPanel 				ligne1, fin		;
	private PanelGrilleQuestionnaire grille 	;
	private JButton 			generer			;

	public PanelCreerQuestionnaire (TestCreerQuestion ctrl)
	{
		this.ctrl=ctrl;
		this.setLayout(new GridLayout (4,1));

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
		
		this.generer = new JButton ("generer questionnaire");

		
		/*this.grille = new PanelGrilleQuestionnaire(this.ctrl,"Test");
		JScrollPane scrollPane = new JScrollPane(this.grille);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Disposition verticale
		this.add(scrollPane);*/

		/*
		 * Definition des panels
		 */
		JPanel gauche,droite, droite2;

		gauche = new JPanel(new BorderLayout());
		gauche.add(new JLabel("ressource"),BorderLayout.NORTH);
		gauche.add(this.choixRessource,BorderLayout.CENTER);

		droite = new JPanel(new GridLayout(2,1));
		droite.add(this.btnOui);
		droite.add(this.btnNon);

		droite2 = new JPanel(new GridLayout(1,2));
		droite2.add(new JLabel(new ImageIcon("img/chrono.PNG")));
		droite2.add(droite);

		this.ligne1 = new JPanel(new GridLayout(1,2));
		this.ligne1.add(gauche);
		this.ligne1.add(droite2);

		this.fin = new JPanel (); 
		this.fin. add (this.generer);

		/*
		 * Ajout des actionListeners/ itemListener
		 */
		this.choixRessource	.addItemListener	(this);
		this.btnOui 		.addActionListener	(this);
		this.btnNon 		.addActionListener	(this);
		this.generer		.addActionListener	(this);

		/*
		 * Placements
		 */
		this.add(this.ligne1);
		this.add(new JPanel());
		this.add(new JPanel());
		this.add(fin);

		this.setVisible(true);
	}

	public void reConstruireIHM ()
	{
		this.removeAll();
		this.add(this.ligne1);

		this.grille= new PanelGrilleQuestionnaire(this.ctrl,"Test2");
		/*JScrollPane scrollPane = new JScrollPane(this.grille);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Disposition verticale
		this.add(scrollPane);*/

		this.add(this.grille);
		this.add(new JPanel());
		this.add(this.fin);
		this.revalidate();
	}

	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource().equals(this.generer))
			this.ctrl.creerQuestionnaire(this.choixRessource.getSelectedItem(),this.btnOui.isSelected(),this.grille.getSelectionner());
	}

	public void itemStateChanged(ItemEvent e) 
	{
		this.reConstruireIHM();
	}
}