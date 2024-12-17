package src.ihm.question;

import src.ihm.PanelReponse;
import src.ihm.TypeReponse;
import src.ihm.FrameExplication;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import src.Controleur;
import src.ihm.*;

public class PanelCreerQCMRepUnique extends JPanel implements ActionListener 
{
	private PanelCreerQuestion 		panelQ						;
	private ArrayList<PanelReponse> reponsesPossibles			;
	private JTextArea 				question					;
	private JButton 				ajouterQ, explication, enreg;
	private JPanel 					panelReponses				; 
	private String 					txtExplication				;
	private FrameCreerQCMRepUnique	fr 							;
	private Controleur 				ctrl						;

	public PanelCreerQCMRepUnique(PanelCreerQuestion panelQ, FrameCreerQCMRepUnique fr, Controleur ctrl) 
	{
		this.panelQ = panelQ;
		this.ctrl 	= ctrl	;
		this.fr 	= fr	;

		this.reponsesPossibles = new ArrayList<>();
		this.reponsesPossibles.add(new PanelReponse(this));
		this.reponsesPossibles.add(new PanelReponse(this));

		this.setLayout(new BorderLayout());

		JPanel panelQuestion = new JPanel(new BorderLayout());
		panelQuestion.add(new JLabel("Question : "), BorderLayout.NORTH);

		this.question = new JTextArea(5, 30);
		this.question.setLineWrap(true);
		this.question.setWrapStyleWord(true);

		JScrollPane questionScrollPane = new JScrollPane(this.question);
		questionScrollPane.setPreferredSize(new Dimension(400, 100));
		panelQuestion.add(questionScrollPane, BorderLayout.CENTER);

		this.add(panelQuestion, BorderLayout.NORTH);

		panelReponses = new JPanel();
		panelReponses.setLayout(new BoxLayout(panelReponses, BoxLayout.Y_AXIS));
		mettreAJourReponses(); 

		JScrollPane scrollPaneReponses = new JScrollPane(panelReponses);
		scrollPaneReponses.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.add(scrollPaneReponses, BorderLayout.CENTER);

		JPanel panelBoutons = new JPanel();

		this.ajouterQ 	 = new JButton(new ImageIcon("../img/ajouter.PNG"	));
		this.explication = new JButton(new ImageIcon("../img/modifier.PNG"	));
		this.enreg 		 = new JButton("Enregistrer"		);


		panelBoutons.add(this.ajouterQ	 );
		panelBoutons.add(this.explication);
		panelBoutons.add(this.enreg		 );

		this.add(panelBoutons, BorderLayout.SOUTH);

		this.ajouterQ.addActionListener(this);
		this.explication.addActionListener(this);
		this.enreg.addActionListener(this);
	}

	private void mettreAJourReponses() 
	{
		panelReponses.removeAll();
		for (PanelReponse p : this.reponsesPossibles) 
			panelReponses.add(p);
		
		panelReponses.revalidate();
		panelReponses.repaint();
	}

	public void supprimer(PanelReponse p)
	{
		this.reponsesPossibles.remove(p);
		this.mettreAJourReponses();
	}

	public void actionPerformed(ActionEvent e) 
	{
		boolean 	estPossible = true				;
		boolean 	aUnBon		= false 			;
		JOptionPane message 	= new JOptionPane()	;

		if (e.getSource() == this.ajouterQ) 
		{
			this.reponsesPossibles.add(new PanelReponse(this));
			mettreAJourReponses();
		} 
		if (e.getSource() == this.enreg) 
		{
			if (this.question.getText().equals(""))
			{
				message.showMessageDialog(null, "Remplissez le champ de question", "Attention", JOptionPane.WARNING_MESSAGE);
				estPossible=false;
			}

			for (PanelReponse p : this.reponsesPossibles)
			{
				if (p.getString().equals(""))
				{
					message.showMessageDialog(null, "Remplissez les champ de réponses", "Attention", JOptionPane.WARNING_MESSAGE);
					estPossible=false;
				}
				if (p.getEstBonneReponse())
					aUnBon = true;
				
			}
			
			if (!aUnBon)
			{
				message.showMessageDialog(null, "Choissisez au moins une bonne réponse", "Attention", JOptionPane.WARNING_MESSAGE);
				estPossible=false;
			}

			ArrayList<TypeReponse> reponses = new ArrayList<TypeReponse>();
			for (PanelReponse p : this.reponsesPossibles)
			{
				reponses.add(new TypeReponse(p.getString(), p.getEstBonneReponse()));
			}
				

			this.panelQ.creerQuestion(this.txtExplication, this.question.getText(), reponses);

			this.fr.dispose	(		  );
			new FrameMenu	(this.ctrl);
		}
		
		
		if (e.getSource().equals(this.explication))
			new FrameExplication(this);
		
	}

	public void   setTxtExplication  (String expli)	{this.txtExplication=expli	;}
	public String getTextExplication (			  )	{return this.txtExplication	;}
}
