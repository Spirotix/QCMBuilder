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
	private JRadioButton		btnChronoOui, btnChronoNon	;
	private JPanel 				ligne1, fin		;
	private PanelGrilleQuestionnaire grille 	;
	private JButton 			generer			;

	public PanelGenererQuestionnaire (Controleur ctrl)
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
		
		this.btnChronoOui = new JRadioButton("oui");
		this.btnChronoNon = new JRadioButton("non");

		this.grpOuiNon = new ButtonGroup();
		this.grpOuiNon.add(this.btnChronoOui);
		this.grpOuiNon.add(this.btnChronoNon);
		
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
		droite.add(this.btnChronoOui);
		droite.add(this.btnChronoNon);

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
		this.btnChronoOui 		.addActionListener	(this);
		this.btnChronoNon 		.addActionListener	(this);
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
			this.ctrl.genererQuestionnaire(this.choixRessource.getSelectedItem(),this.btnChronoOui.isSelected(),this.grille.getSelectionner());
	}

	public void itemStateChanged(ItemEvent e) 
	{
		this.reConstruireIHM();
	}
}