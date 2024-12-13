//package src.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
//import src.Controleur;

public class FrameMenu extends JFrame implements ActionListener {

	private JButton creerQuestion, genererQuestionnaire, creerResNot;
	private TestCreerQuestion ctrl;
	private JPanel panel;

	public FrameMenu(TestCreerQuestion ctrl) 
	{
		this.ctrl = ctrl;

		this.setLayout(new BorderLayout());
		this.panel = new JPanel(new GridLayout(3, 1, 10, 10));
		this.panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		this.panel.setBackground(Color.LIGHT_GRAY);
		

		this.setTitle("Menu principal");
		this.setSize(400, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		this.creerQuestion = new JButton("Créer Question");
		this.genererQuestionnaire = new JButton("Générer Questionnaire");
		this.creerResNot = new JButton("Créer Ressource/Notion");

		this.creerQuestion.setFont(new Font("Arial", Font.PLAIN, 14));
		this.genererQuestionnaire.setFont(new Font("Arial", Font.PLAIN, 14));
		this.creerResNot.setFont(new Font("Arial", Font.PLAIN, 14));

		this.creerQuestion.setOpaque(false);
		this.creerQuestion.setContentAreaFilled(false);
		this.creerQuestion.setBorder(new LineBorder(Color.BLACK));
		this.creerQuestion.setFocusPainted(false);
		this.creerQuestion.setForeground(Color.BLACK);

		this.genererQuestionnaire.setOpaque(false);
		this.genererQuestionnaire.setContentAreaFilled(false);
		this.genererQuestionnaire.setBorder(new LineBorder(Color.BLACK));
		this.genererQuestionnaire.setFocusPainted(false);
		this.genererQuestionnaire.setForeground(Color.BLACK);

		this.creerResNot.setOpaque(false);
		this.creerResNot.setContentAreaFilled(false);
		this.creerResNot.setBorder(new LineBorder(Color.BLACK));
		this.creerResNot.setFocusPainted(false);
		this.creerResNot.setForeground(Color.BLACK);

		this.creerQuestion.addActionListener(this);
		this.genererQuestionnaire.addActionListener(this);
		this.creerResNot.addActionListener(this);

		this.panel.add(this.creerQuestion);
		this.panel.add(this.genererQuestionnaire);
		this.panel.add(this.creerResNot);

		JPanel southPanel = new JPanel();
		southPanel.setBackground(Color.LIGHT_GRAY);
		JPanel northPanel = new JPanel();
		northPanel.setBackground(Color.LIGHT_GRAY);
		JPanel westPanel = new JPanel();
		westPanel.setBackground(Color.LIGHT_GRAY);
		JPanel eastPanel = new JPanel();
		eastPanel.setBackground(Color.LIGHT_GRAY);

		this.add(southPanel, BorderLayout.SOUTH);
		this.add(northPanel, BorderLayout.NORTH);
		this.add(westPanel, BorderLayout.WEST);
		this.add(eastPanel, BorderLayout.EAST);

		this.add(this.panel, BorderLayout.CENTER);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(this.creerQuestion)) {
			new FrameCreerQuestion(this.ctrl);
			this.dispose();
		}

		if (e.getSource().equals(this.genererQuestionnaire))
		{
			new FrameGenererQuestionnaire(this.ctrl);
			this.dispose();
		}

		if (e.getSource().equals(this.creerResNot))
		{
			new FrameCreerRessource(this.ctrl);
			this.dispose();
		}
	}
}