//package src.ihm;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
//import src.Controleur;

public class PanelCreerQuestion extends JPanel implements ActionListener, ItemListener
{
	private 	TestCreerQuestion 	ctrl;
	private 	JTextField 		nbPoints, tpsReponses;
	private  	Choice 			choixRessource, choixNotion;
	private 	ButtonGroup		btnGroup, btnGroupImg;
	private 	JRadioButton	btnChoixUnique, btnChoixMult, btnAsso, btnElim;
	private		JButton 		btnCreer;	
	private 	JRadioButton 	btnTF, btnF, btnM, btnD ; 
	private 	JLabel			msgErrNbPts, msgErrTpsRep, msgErrRess,msgErrNiv,msgErrNot, msgChoixType ;
	private 	FrameCreerQuestion fr;

	//Ressources finale
	private		String		typeQuestion;
	private 	String		ressourceQuestion;
	private 	String 		notionQuestion;
	private 	String 		textQuestion;
	private 	int 		tempsQuestion;
	private 	int 		nbPointQuestion;
	private 	int 		difficulteQuestion ;
	private 	String 		explicationQuestion;

	public PanelCreerQuestion (TestCreerQuestion ctrl, FrameCreerQuestion fr)
	{
		this.ctrl = ctrl;
		this.fr = fr;
		this.setLayout(new GridLayout(11, 1));

		// Initialisation
		this.nbPoints = new JTextField("5", 5);
		this.tpsReponses = new JTextField("10", 5);

		this.choixRessource = new Choice();
		this.choixRessource.add(" ");
		for (String s : this.ctrl.getChoixRessources())
			this.choixRessource.add(s);

		this.choixNotion = new Choice();
		
		this.choixNotion.add("                    ");
		this.choixNotion.setEnabled(false); // Disable initially

		this.btnGroupImg = new ButtonGroup();
		this.btnTF = new JRadioButton(new ImageIcon("img/TF2.PNG"));
		this.btnF = new JRadioButton(new ImageIcon("img/F2.PNG"));
		this.btnM = new JRadioButton(new ImageIcon("img/M2.PNG"));
		this.btnD = new JRadioButton(new ImageIcon("img/D2.PNG"));

		this.btnTF.setEnabled(false);
		this.btnF.setEnabled(false);
		this.btnM.setEnabled(false);
		this.btnD.setEnabled(false);

		this.btnGroupImg.add(this.btnTF);
		this.btnGroupImg.add(this.btnF);
		this.btnGroupImg.add(this.btnM);
		this.btnGroupImg.add(this.btnD);

		this.btnChoixUnique = new JRadioButton("Choix Unique");
		this.btnChoixMult = new JRadioButton("Choix Multiple");
		this.btnAsso = new JRadioButton("Associatif");
		this.btnElim = new JRadioButton("Elimination");

		this.msgErrNbPts = new JLabel("");
		this.msgErrTpsRep = new JLabel("");
		this.msgErrRess = new JLabel("");
		this.msgErrNiv = new JLabel("");
		this.msgErrNot = new JLabel("");
		this.msgChoixType = new JLabel("");

		this.btnGroup = new ButtonGroup();
		this.btnGroup.add(this.btnChoixUnique);
		this.btnGroup.add(this.btnChoixMult);
		this.btnGroup.add(this.btnAsso);
		this.btnGroup.add(this.btnElim);

		this.btnCreer = new JButton("Creer");

		// ActionListener / itemListener
		this.nbPoints.addActionListener(this);
		this.tpsReponses.addActionListener(this);
		this.btnTF.addActionListener(this);
		this.btnF.addActionListener(this);
		this.btnM.addActionListener(this);
		this.btnD.addActionListener(this);
		this.btnChoixUnique.addActionListener(this);
		this.btnChoixMult.addActionListener(this);
		this.btnAsso.addActionListener(this);
		this.btnElim.addActionListener(this);
		this.choixRessource.addItemListener(this);
		this.choixNotion.addItemListener(this);
		this.btnCreer.addActionListener(this);

		// Layout
		this.add(createLabeledPanel("Nombre de points", this.nbPoints));
		this.add(createLabeledPanel("Temps de réponse", this.tpsReponses));
		this.add(createErrorPanel(this.msgErrNbPts, this.msgErrTpsRep));
		this.add(createLabeledPanel("Ressource", this.choixRessource));
		this.add(createLabeledPanel("Notion", this.choixNotion));
		this.add(createErrorPanel(this.msgErrRess, this.msgErrNot));
		this.add(createLabeledPanel("Niveau", createDifficultyPanel()));
		this.add(createErrorPanel(this.msgErrNiv));
		this.add(createLabeledPanel("Type de question", createTypePanel()));
		this.add(createErrorPanel(this.msgChoixType));
		this.add(this.btnCreer);

		this.setVisible(true);
	}

	private JPanel createLabeledPanel(String labelText, Component component) {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(new JLabel(labelText));
		panel.add(component);
		return panel;
	}

	private JPanel createErrorPanel(JLabel... labels) {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		for (JLabel label : labels) {
			panel.add(label);
		}
		return panel;
	}

	private JPanel createDifficultyPanel() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(this.btnTF);
		panel.add(this.btnF);
		panel.add(this.btnM);
		panel.add(this.btnD);
		return panel;
	}

	private JPanel createTypePanel() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(this.btnChoixUnique);
		panel.add(this.btnChoixMult);
		panel.add(this.btnAsso);
		panel.add(this.btnElim);
		return panel;
	}

	public void actionPerformed(ActionEvent e) {
		boolean peutCreer = true;

		if (e.getSource().equals(this.btnCreer)) {
			if (this.nbPoints.getText().equals("")) {
				this.msgErrNbPts.setForeground(Color.RED);
				this.msgErrNbPts.setText("Vous devez rentrer une nombre de points");
				peutCreer = false;
			} else {
				try {
					this.nbPointQuestion = Integer.parseInt(this.nbPoints.getText());
					this.msgErrNbPts.setText("");
				} catch (Exception ex) {
					this.msgErrNbPts.setForeground(Color.RED);
					this.msgErrNbPts.setText("Vous devez rentrer un entier pour le nombre de points");
					peutCreer = false;
				}
			}

			if (this.tpsReponses.getText().equals("")) {
				this.msgErrTpsRep.setForeground(Color.RED);
				this.msgErrTpsRep.setText("Vous devez rentrer un temps de réponse");
				peutCreer = false;
			} else {
				try {
					this.tempsQuestion = Integer.parseInt(this.tpsReponses.getText());
					this.msgErrTpsRep.setText("");
				} catch (Exception ex) {
					this.msgErrTpsRep.setForeground(Color.RED);
					this.msgErrTpsRep.setText("Vous devez rentrer un entier pour le temps des réponses");
					peutCreer = false;
				}
			}

			if (this.choixRessource.getSelectedItem().equals(" ")) {
				this.msgErrRess.setForeground(Color.RED);
				this.msgErrRess.setText("Vous devez choisir une ressource");
				peutCreer = false;
			} else {
				this.msgErrRess.setText("");
			}

			if (this.choixNotion.getSelectedItem().equals(" ")) {
				this.msgErrNot.setForeground(Color.RED);
				this.msgErrNot.setText("Vous devez choisir une notion");
				peutCreer = false;
			} else {
				this.msgErrNot.setText("");
			}

			if (!(this.btnChoixUnique.isSelected() || this.btnChoixMult.isSelected() || this.btnAsso.isSelected() || this.btnElim.isSelected())) {
				this.msgChoixType.setForeground(Color.RED);
				this.msgChoixType.setText("Vous devez choisir un type");
				peutCreer = false;
			} else {
				this.msgChoixType.setText("");
			}

			if (!(this.btnTF.isSelected() || this.btnF.isSelected() || this.btnM.isSelected() || this.btnD.isSelected())) {
				this.msgErrNiv.setForeground(Color.RED);
				this.msgErrNiv.setText("Vous devez choisir un niveau de difficulté");
				peutCreer = false;
			} else {
				this.msgErrNiv.setText("");
			}

			if (peutCreer)
			{
				if (this.btnChoixMult.isSelected() || this.btnChoixUnique.isSelected())
					new FrameCreerQCMRepUnique(this);
				if (this.btnAsso.isSelected())
					new FrameCreerAssociation(this);
				this.fr.dispose();
			}
		}

		if (this.btnTF.isSelected() && !this.choixNotion.getSelectedItem().equals("")&& !this.choixRessource.getSelectedItem().equals("                    "))
		{
			this.btnTF.setIcon(new ImageIcon("img/TF1.PNG"));
			this.btnF .setIcon(new ImageIcon("img/F2.PNG" ));
			this.btnM .setIcon(new ImageIcon("img/M2.PNG" ));
			this.btnD .setIcon(new ImageIcon("img/D2.PNG" ));
			this.difficulteQuestion = 1;
			this.msgErrNiv.setText("");
		}

		else if (this.btnF.isSelected() && !this.choixNotion.getSelectedItem().equals("") && !this.choixRessource.getSelectedItem().equals("                    "))
		{
			this.btnTF.setIcon(new ImageIcon("img/TF2.PNG"));
			this.btnF .setIcon(new ImageIcon("img/F1.PNG" ));
			this.btnM .setIcon(new ImageIcon("img/M2.PNG" ));
			this.btnD .setIcon(new ImageIcon("img/D2.PNG" ));
			this.difficulteQuestion = 2;
			this.msgErrNiv.setText("");
		}

		else if (this.btnM.isSelected() && !this.choixNotion.getSelectedItem().equals("")&& !this.choixRessource.getSelectedItem().equals("                    "))
			{
			this.btnTF.setIcon(new ImageIcon("img/TF2.PNG"));
			this.btnF .setIcon(new ImageIcon("img/F2.PNG" ));
			this.btnM .setIcon(new ImageIcon("img/M1.PNG" ));
			this.btnD .setIcon(new ImageIcon("img/D2.PNG" ));
			this.difficulteQuestion = 3;
			this.msgErrNiv.setText("");
		}

		else if (this.btnD.isSelected() && !this.choixNotion.getSelectedItem().equals("")&& !this.choixRessource.getSelectedItem().equals("                    "))
		{
			this.btnTF.setIcon(new ImageIcon("img/TF2.PNG"));
			this.btnF .setIcon(new ImageIcon("img/F2.PNG" ));
			this.btnM .setIcon(new ImageIcon("img/M2.PNG" ));
			this.btnD .setIcon(new ImageIcon("img/D1.PNG" ));
			this.difficulteQuestion = 4;
			this.msgErrNiv.setText("");
		}
		

		updateDifficultyIcons();
	}

	private void updateDifficultyIcons() {
		if (this.btnTF.isSelected()) {
			this.difficulteQuestion = 1;
		} else if (this.btnF.isSelected()) {
			this.difficulteQuestion = 2;
		} else if (this.btnM.isSelected()) {
			this.difficulteQuestion = 3;
		} else if (this.btnD.isSelected()) {
			this.difficulteQuestion = 4;
		}
	}

	public void itemStateChanged(ItemEvent e) {
		if (e.getSource().equals(this.choixRessource) && !this.choixRessource.getSelectedItem().equals(" ")) {
			this.choixNotion.setEnabled(true);
			this.choixNotion.removeAll();
			this.choixNotion.add(" ");
			this.ressourceQuestion = this.choixRessource.getSelectedItem();
			for (String s : this.ctrl.getChoixNotion(this.choixRessource.getSelectedItem()))
				this.choixNotion.add(s);
		}

		if (e.getSource().equals(this.choixRessource) && this.choixRessource.getSelectedItem().equals(" ")) {
			this.choixNotion.removeAll();
			this.choixNotion.add(" ");
			this.choixNotion.setEnabled(false);
			this.btnTF.setEnabled(false);
			this.btnF.setEnabled(false);
			this.btnM.setEnabled(false);
			this.btnD.setEnabled(false);
			this.btnTF.setIcon(new ImageIcon("img/TF2.PNG"));
			this.btnF .setIcon(new ImageIcon("img/F2.PNG" ));
			this.btnM .setIcon(new ImageIcon("img/M2.PNG" ));
			this.btnD .setIcon(new ImageIcon("img/D2.PNG" ));
		}

		if (e.getSource().equals(this.choixNotion) && !this.choixNotion.getSelectedItem().equals("                    ")) {
			this.notionQuestion = this.choixNotion.getSelectedItem();
			this.btnGroupImg.clearSelection();
			this.btnTF.setEnabled(true);
			this.btnF.setEnabled(true);
			this.btnM.setEnabled(true);
			this.btnD.setEnabled(true);
		}

		if (e.getSource().equals(this.choixNotion) && this.choixNotion.getSelectedItem().equals("                    ")) {
			this.btnTF.setIcon(new ImageIcon("img/TF2.PNG"));
			this.btnF .setIcon(new ImageIcon("img/F2.PNG" ));
			this.btnM .setIcon(new ImageIcon("img/M2.PNG" ));
			this.btnD .setIcon(new ImageIcon("img/D2.PNG" ));
			this.btnTF.setEnabled(false);
			this.btnF.setEnabled(false);
			this.btnM.setEnabled(false);
			this.btnD.setEnabled(false);
			
		}
	}

	public void creerQCM(String explication, String intituleQuestion, ArrayList<String> lstReponses) 
	{
		/*
		 * Format des réponses
		 * Texte_réponse1_VRAI
		 * Texte_réponse2_FAUX
		 * Texte_réponse3_FAUX
		 * Texte_réponse4_FAUX
		 * Exemple : 
		 * 35_VRAI
		 * 33_FAUX
		 * 25_FAUX
		 */
		this.typeQuestion = "QCM";
		this.textQuestion = intituleQuestion;
		this.explicationQuestion = explication;
		this.ctrl.creerQCM(this.typeQuestion, this.ressourceQuestion, this.notionQuestion, this.textQuestion, this.explicationQuestion, this.tempsQuestion, this.nbPointQuestion, lstReponses, this.difficulteQuestion);
	}

	public void creerAsso(String explication, String intituleQuestion, ArrayList<String> lstReponses) 
	{
		/*
		 * Format des réponses
		 * Texte_réponse1 -> indice-liaison1_indice-liaison2_indice-liaison3///Texte_réponse_coté_opposée
		 * Texte_réponse2 -> indice-liaison1_indice-liaison2_indice-liaison3///Texte_réponse_coté_opposée
		 * Exemple : 
		 * Chien -> 1_3///aboie
		 * Chat -> 2///Miaule
		 * Poisson///grogne
		 */
		this.typeQuestion = "Association";
		this.textQuestion = intituleQuestion;
		this.explicationQuestion = explication;
		this.ctrl.creerAsso(this.typeQuestion, this.ressourceQuestion, this.notionQuestion, this.textQuestion, this.explicationQuestion, this.tempsQuestion, this.nbPointQuestion, lstReponses, this.difficulteQuestion);
	}
}
