package src.ihm.question;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import src.ihm.FrameExplication;
import src.ihm.TypeReponse;

public class PanelCreerQuestionAsso extends JPanel implements ActionListener 
{
	private PanelCreerQuestion 	panelQ				; // Référence au panneau principal

	private PanelLiaison 		panelL				;

	private JTextArea 			question			; // Zone de texte pour la question
	private JButton 			explication, enreg	; // Boutons modifier l'explication et enregistrer
	private String 				txtExplication		; // Texte de l'explication
	

	public PanelCreerQuestionAsso(PanelCreerQuestion panelQ) 
	{
		this.panelQ = panelQ;

		this.setLayout(new BorderLayout());

		// Créer et configurer le panneau de la question
		JPanel panelQuestion = new JPanel(new BorderLayout());
		panelQuestion.add(new JLabel("Question : "), BorderLayout.NORTH);

		this.question = new JTextArea (5, 30);
		this.question.setLineWrap	  (true );
		this.question.setWrapStyleWord(true );

		JScrollPane questionScrollPane    = new JScrollPane(this.question);
		questionScrollPane.setPreferredSize(new Dimension(400, 100		));
		panelQuestion.add(questionScrollPane, BorderLayout.CENTER		 );

		this.add(panelQuestion, BorderLayout.NORTH);


		// Créer et configurer le panneau des boutons

		this.panelL = new PanelLiaison(this);
		this.add(panelL, BorderLayout.CENTER);
		JPanel panelBoutons = new JPanel();

		this.explication = new JButton(new ImageIcon("img/modifier.PNG"));
		this.enreg 		= new JButton ("Enregistrer"					);

		panelBoutons.add (this.explication	);
		panelBoutons.add (this.enreg		);

		this.add(panelBoutons, BorderLayout.SOUTH);

		// Ajouter des écouteurs d'action aux boutons
		this.explication.addActionListener(this);
		this.enreg		.addActionListener(this);
	}


	// Gérer les actions des boutons
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == this.enreg) 
		{
			ArrayList<TypeReponse> reponses = this.panelL.getReponses();

			this.panelQ.creerQuestion(this.txtExplication, this.question.getText(), reponses);
		}
		if (e.getSource().equals(this.explication))
		{
			// Ouvrir le cadre d'explication
			new FrameExplication(this);
		}
	}

	// Définir le texte de l'explication
	public void setTxtExplication(String expli) 
	{
		this.txtExplication = expli;
	}

	// Obtenir le texte de l'explication
	public String getTextExplication() 
	{
		return this.txtExplication;
	}
}
