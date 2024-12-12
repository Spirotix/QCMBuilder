//package src.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class PanelCreerQuestionAsso extends JPanel implements ActionListener 
{
	private PanelCreerQuestion panelQ; // Référence au panneau principal
	//private ArrayList<PanelReponseAsso> reponsesPossibles; // Liste des réponses possibles

	private PanelLiaison 	panelL;

	private JTextArea question; // Zone de texte pour la question
	private JButton ajouterQ, explication, enreg; // Boutons pour ajouter une question, modifier l'explication et enregistrer
	//private PaintComponentAsso panelReponses; // Composant personnalisé pour afficher les réponses
	private String txtExplication; // Texte de l'explication
	

	public PanelCreerQuestionAsso(PanelCreerQuestion panelQ) 
	{
		this.panelQ = panelQ;

		// Initialiser la liste des réponses possibles avec deux réponses vides
		//this.reponsesPossibles = new ArrayList<PanelReponseAsso>();
		//this.reponsesPossibles.add(new PanelReponseAsso(this));
		//this.reponsesPossibles.add(new PanelReponseAsso(this));

		this.setLayout(new BorderLayout());

		// Créer et configurer le panneau de la question
		JPanel panelQuestion = new JPanel(new BorderLayout());
		panelQuestion.add(new JLabel("Question : "), BorderLayout.NORTH);

		this.question = new JTextArea(5, 30);
		this.question.setLineWrap(true);
		this.question.setWrapStyleWord(true);

		JScrollPane questionScrollPane = new JScrollPane(this.question);
		questionScrollPane.setPreferredSize(new Dimension(400, 100));
		panelQuestion.add(questionScrollPane, BorderLayout.CENTER);

		this.add(panelQuestion, BorderLayout.NORTH);

		// Créer et configurer le panneau des réponses
		/*this.panelReponses = new PaintComponentAsso(this);
		this.panelReponses.setLayout(new BoxLayout(this.panelReponses, BoxLayout.Y_AXIS));
		mettreAJourReponses(); 

		JScrollPane scrollPaneReponses = new JScrollPane(this.panelReponses);
		scrollPaneReponses.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.add(scrollPaneReponses, BorderLayout.CENTER);*/

		// Créer et configurer le panneau des boutons

		this.panelL = new PanelLiaison(this);
		this.add(panelL, BorderLayout.CENTER);
		JPanel panelBoutons = new JPanel();

		this.ajouterQ = new JButton(new ImageIcon("img/ajouter.PNG"));
		this.explication = new JButton(new ImageIcon("img/modifier.PNG"));
		this.enreg = new JButton("Enregistrer");

		panelBoutons.add(this.ajouterQ);
		panelBoutons.add(this.explication);
		panelBoutons.add(this.enreg);

		this.add(panelBoutons, BorderLayout.SOUTH);

		// Ajouter des écouteurs d'action aux boutons
		this.ajouterQ.addActionListener(this);
		this.explication.addActionListener(this);
		this.enreg.addActionListener(this);
	}

	// Mettre à jour le panneau des réponses avec la liste actuelle des réponses possibles
	/*private void mettreAJourReponses() 
	{
		this.panelReponses.removeAll();
		for (PanelReponseAsso p : this.reponsesPossibles) 
			this.panelReponses.add(p);
		
		this.panelReponses.revalidate();
		this.panelReponses.repaint();
	}

	// Obtenir la liste des réponses possibles
	public ArrayList<PanelReponseAsso> getLstReponses() 
	{
		return this.reponsesPossibles;
	}

	// Obtenir le composant personnalisé pour afficher les réponses
	public PaintComponentAsso getPaintComponent() 
	{
		return this.panelReponses;
	}

	// Supprimer une réponse de la liste et mettre à jour le panneau
	public void supprimer(PanelReponseAsso p)
	{
		this.reponsesPossibles.remove(p);
		this.mettreAJourReponses();
	}*/

	// Gérer les actions des boutons
	public void actionPerformed(ActionEvent e) 
	{
		/*if (e.getSource() == this.ajouterQ) 
		{
			// Ajouter une nouvelle réponse et mettre à jour le panneau
			this.reponsesPossibles.add(new PanelReponseAsso(this));
			mettreAJourReponses();
		} */
		if (e.getSource() == this.enreg) 
		{
			// Enregistrer la question et les réponses
			/*ArrayList<TypeReponse> reponses = new ArrayList<TypeReponse>();
			TypeReponse repGauche,repDroite;
			for (PanelReponseAsso p : this.reponsesPossibles)
			{
				repGauche = new TypeReponse(p.getPanelGauche().getString());
				repDroite = new TypeReponse(p.getPanelDroit ().getString());
				reponses.add(new TypeReponse(repGauche, repDroite, p.getPanelGauche().getListe()));
			}*/
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

	/*public void supprimerLiaison(PanelReponseDroiteAsso pd)
	{
		System.out.println("supprime");
		for (PanelReponseAsso pa : this.reponsesPossibles)
		{
			if (pa.getPanelGauche()!=null)
				pa.getPanelGauche().supprimerLiaison(pd);
		}
			
	}*/

	// Obtenir le texte de l'explication
	public String getTextExplication() 
	{
		return this.txtExplication;
	}
}
