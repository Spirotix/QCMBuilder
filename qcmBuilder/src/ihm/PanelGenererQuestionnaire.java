package src.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import src.Controleur;

public class PanelGenererQuestionnaire extends JPanel implements ActionListener, ItemListener 
{
	private Controleur               ctrl                       ;
	private Choice                   choixRessource             ;
	private ButtonGroup              grpOuiNon                  ;
	private JRadioButton             btnChronoOui, btnChronoNon ;
	private JPanel                   ligne1, fin                ;
	private PanelGrilleQuestionnaire grille                     ;
	private JButton                  generer                    ;
	private TextFieldPerso           nomQuestionnaire           ;
	private JScrollPane              scrollPane                 ;

	public PanelGenererQuestionnaire(Controleur ctrl) 
	{
		this.ctrl = ctrl;
		this.setLayout(new BorderLayout()); 

		/*
		* Définition des composants
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

		this.generer = new JButton("Générer questionnaire");
		this.nomQuestionnaire = new TextFieldPerso("nom du questionnaire");

		/*
		* Définition des panels
		*/

		JPanel gauche = new JPanel(new BorderLayout());
		gauche.add(new JLabel("Ressource"), BorderLayout.NORTH);
		gauche.add(this.choixRessource, BorderLayout.CENTER);

		JPanel droite = new JPanel(new GridLayout(2, 1));
		droite.add(this.btnChronoOui);
		droite.add(this.btnChronoNon);

		JPanel droite2 = new JPanel(new GridLayout(1, 2));
		droite2.add(new JLabel(new ImageIcon("../img/chrono.PNG")));
		droite2.add(droite);

		this.ligne1 = new JPanel(new GridLayout(1, 2));
		this.ligne1.add(gauche );
		this.ligne1.add(droite2);

		this.fin = new JPanel(new GridLayout(2, 1));
		this.fin.add(this.nomQuestionnaire);
		this.fin.add(this.generer         );

		/*
		* Ajout des actionListeners
		*/
		this.choixRessource.addItemListener  (this);
		this.btnChronoOui  .addActionListener(this);
		this.btnChronoNon  .addActionListener(this);
		this.generer       .addActionListener(this);

		/*
		* Placement initial
		*/
		this.add(this.ligne1 , BorderLayout.NORTH );
		this.add(new JPanel(), BorderLayout.CENTER);
		this.add(this.fin    , BorderLayout.SOUTH );

		this.setVisible(true);
	}

	public void reConstruireIHM() 
	{
		this.removeAll();
		this.add(this.ligne1, BorderLayout.NORTH);

		// Reconstruire la grille
		this.grille     = new PanelGrilleQuestionnaire(this.ctrl, this.choixRessource.getSelectedItem());
		this.scrollPane = new JScrollPane             (this.grille);
		this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.scrollPane.setPreferredSize(new Dimension(400, 400)); // Taille du panneau défilable

		this.add(this.scrollPane, BorderLayout.CENTER);
		this.add(this.fin       , BorderLayout.SOUTH );

		this.revalidate();
		this.repaint   ();
	}

	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource().equals(this.generer)) 
		{
			if (!this.btnChronoOui.isSelected() && !this.btnChronoNon.isSelected())
			{
				JOptionPane.showMessageDialog(null, "choissisez si le questionnaire est chronometré ou non", "Attention", JOptionPane.WARNING_MESSAGE);
				return ;
			}
			if (this.nomQuestionnaire.getText().equals(""))
			{
				JOptionPane.showMessageDialog(null, "rentrez un nom pour le questionnaire", "Attention", JOptionPane.WARNING_MESSAGE);
				return ;
			}


			this.ctrl.genererQuestionnaire
			(
				this.choixRessource  .getSelectedItem(),
				this.btnChronoOui    .isSelected(),
				this.grille          .getSelectionner(),
				this.nomQuestionnaire.getText(),
			);

			
		}
	}

	public void itemStateChanged(ItemEvent e) 
	{
		this.reConstruireIHM();
	}
}
