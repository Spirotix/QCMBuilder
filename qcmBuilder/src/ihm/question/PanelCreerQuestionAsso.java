//package src.ihm;
package src.ihm.question;

import src.ihm.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import src.Controleur;

public class PanelCreerQuestionAsso extends JPanel implements ActionListener 
{
	private PanelCreerQuestion 			panelQ						; // Référence au panneau principal
	private ArrayList<PanelReponseAsso> reponsesPossibles			; // Liste des réponses possibles
	private FrameCreerAssociation 		fr 							;
	private Controleur					ctrl						;

	private JTextArea 					question					; // Zone de texte pour la question
	private JButton 					ajouterQ, explication, enreg; // Boutons pour ajouter une question, modifier l'explication et enregistrer
	private JPanel 						panelReponses				; // Composant personnalisé pour afficher les réponses
	private String 						txtExplication				; // Texte de l'explication
	private int 						indiceReponse 				;
	

	public PanelCreerQuestionAsso(PanelCreerQuestion panelQ, FrameCreerAssociation fr, Controleur ctrl) 
	{
		this.panelQ = panelQ;
		this.fr 	= fr 	;
		this.ctrl 	= ctrl	;
		this.indiceReponse = 1;

		// Initialiser la liste des réponses possibles avec deux réponses vides
		this.reponsesPossibles = new ArrayList<PanelReponseAsso>();
		this.reponsesPossibles.add(new PanelReponseAsso(this,this.indiceReponse++));
		this.reponsesPossibles.add(new PanelReponseAsso(this,this.indiceReponse++));

		this.setLayout(new BorderLayout());

		// Créer et configurer le panneau de la question
		JPanel panelQuestion = 	 new JPanel(new BorderLayout());
		panelQuestion.add		(new JLabel("Question : "), BorderLayout.NORTH);

		this.question = new JTextArea(5, 30);
		this.question.setLineWrap		(true);
		this.question.setWrapStyleWord	(true);

		JScrollPane questionScrollPane = new JScrollPane(this.question);
		questionScrollPane.setPreferredSize(new Dimension(400, 100));
		panelQuestion.add(questionScrollPane, BorderLayout.CENTER);

		this.add(panelQuestion, BorderLayout.NORTH);

		// Créer et configurer le panneau des réponses
		this.panelReponses = new JPanel();
		this.panelReponses.setLayout(new BoxLayout(this.panelReponses, BoxLayout.Y_AXIS));
		mettreAJourReponses(); 

		JScrollPane scrollPaneReponses = new JScrollPane(this.panelReponses);
		scrollPaneReponses.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.add(scrollPaneReponses, BorderLayout.CENTER);

		// Créer et configurer le panneau des boutons
		JPanel panelBoutons = new JPanel();

		this.ajouterQ 	 = new JButton (new ImageIcon("../img/ajouter.PNG"));
		this.explication = new JButton (new ImageIcon("../img/modifier.PNG"));
		this.enreg		 = new JButton ("Enregistrer");

		panelBoutons.add(this.ajouterQ);
		panelBoutons.add(this.explication);
		panelBoutons.add(this.enreg);

		this.add(panelBoutons, BorderLayout.SOUTH);

		// Ajouter des écouteurs d'action aux boutons
		this.ajouterQ	.addActionListener(this);
		this.explication.addActionListener(this);
		this.enreg		.addActionListener(this);
	}

	// Mettre à jour le panneau des réponses avec la liste actuelle des réponses possibles
	private void mettreAJourReponses() 
	{
		this.panelReponses.removeAll();
		for (PanelReponseAsso p : this.reponsesPossibles) 
		{
			panelReponses.add(p);
			panelReponses.add(p);
			p.updateImageG();
			p.updateImageD();
		} 
		
		this.panelReponses.revalidate	();
		this.panelReponses.repaint		();
	}

	// Obtenir la liste des réponses possibles
	public ArrayList<PanelReponseAsso> getLstReponses() 
	{
		return this.reponsesPossibles;
	}



	// Supprimer une réponse de la liste et mettre à jour le panneau
	public void supprimer(PanelReponseAsso p)
	{
		this.reponsesPossibles.remove(p);
		this.mettreAJourReponses();
	}

	// Gérer les actions des boutons
	public void actionPerformed(ActionEvent e) 
	{

		if (e.getSource() == this.ajouterQ) 
		{
			// Ajouter une nouvelle réponse et mettre à jour le panneau
			this.reponsesPossibles.add(new PanelReponseAsso(this,this.indiceReponse++));
			mettreAJourReponses();
		} 
		if (e.getSource() == this.enreg) 
		{
			//Verification de la validité de la question
			if (this.question.getText().equals(""))
			{
				JOptionPane.showMessageDialog(null, "Remplissez le champ de question", "Attention", JOptionPane.WARNING_MESSAGE);
				return ;
			}
				
			for (PanelReponseAsso p : this.reponsesPossibles)
				if (p.getContenuGauche().equals("") || p.getContenuDroite().equals(""))
				{
					JOptionPane.showMessageDialog(null, "Remplissez tous les champs", "Attention", JOptionPane.WARNING_MESSAGE);
					return ;
				}
			// Enregistrer la question et les réponses
			ArrayList<TypeReponse> reponses = new ArrayList<TypeReponse>();
			TypeReponse 		repGauche,repDroite;

			for (PanelReponseAsso p : this.reponsesPossibles)
			{
				repGauche = new TypeReponse(p.getContenuGauche(), "Gauche", p.getCheminG());
				repDroite = new TypeReponse(p.getContenuDroite(),"Droite", p.getCheminD());
				reponses.add(new TypeReponse(repGauche, repDroite));
			}

			if (this.panelQ.creerQuestion(this.txtExplication, this.question.getText(), reponses))
			{
				this.fr.dispose();
				new FrameMenu(this.ctrl);
			}
			else 
				JOptionPane.showMessageDialog(null, "Cette Question existe deja ", "Attention", JOptionPane.WARNING_MESSAGE);
			
			
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
