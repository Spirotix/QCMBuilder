package src.ihm.question;

import src.ihm.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import src.Controleur;

public class PanelCreerElim extends JPanel implements ActionListener 
{
	private PanelCreerQuestion 					panelQ						;
	private ArrayList<PanelCreerReponsesElim> 	reponsesPossibles			;
	private JTextArea 							question					;
	private JButton 							ajouterQ, explication, enreg;
	private JPanel 								panelReponses				;  
	private String 								txtExplication				;
	private FrameCreerElimination				fr 							;
	private Controleur							ctrl 						;

	public PanelCreerElim(PanelCreerQuestion panelQ, FrameCreerElimination fr, Controleur ctrl) 
	{
		this.panelQ = panelQ;
		this.ctrl	= ctrl	;
		this.fr 	= fr	;

		this.reponsesPossibles = new ArrayList<>();
		this.reponsesPossibles.add(new PanelCreerReponsesElim(this));
		this.reponsesPossibles.add(new PanelCreerReponsesElim(this));

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
		for (PanelCreerReponsesElim p : this.reponsesPossibles) 
			panelReponses.add(p);
		
		panelReponses.revalidate();
		panelReponses.repaint();
	}

	public void supprimer(PanelCreerReponsesElim p)
	{
		this.reponsesPossibles.remove(p);
		this.mettreAJourReponses();
	}

	public void actionPerformed(ActionEvent e) 
	{
		JOptionPane message 	= new JOptionPane()	;
		boolean 	aUnBon		= false 			;

		if (e.getSource() == this.ajouterQ) 
		{
			this.reponsesPossibles.add(new PanelCreerReponsesElim(this));
			mettreAJourReponses();
		} 

		if (e.getSource() == this.enreg) 
		{

			if (this.question.getText().equals(""))
			{
				message.showMessageDialog(null, "Remplissez le champ de question", "Attention", JOptionPane.WARNING_MESSAGE);
				return ;
			}

			for (PanelCreerReponsesElim p : this.reponsesPossibles)
			{
				if (p.getString().equals(""))
				{
					message.showMessageDialog(null, "Remplissez les champ de réponses", "Attention", JOptionPane.WARNING_MESSAGE);
					return ;
				}
				if (p.getEstBonneReponse())
					aUnBon = true;
			}

			if (!aUnBon)
			{
				message.showMessageDialog(null, "Choissisez au moins une bonne réponse", "Attention", JOptionPane.WARNING_MESSAGE);
				return ;
			}

			ArrayList<TypeReponse> reponses = new ArrayList<TypeReponse>();

			for (PanelCreerReponsesElim p : this.reponsesPossibles)
				reponses.add(new TypeReponse(p.getString(),p.getOrdre(), p.getCout(),p.getEstBonneReponse() ));

			if (this.panelQ.creerQuestion(this.txtExplication, this.question.getText(), reponses))
			{
				this.fr.dispose();
				new FrameMenu(this.ctrl);
			}
			else 
				message.showMessageDialog(null, "Cette Question existe deja ", "Attention", JOptionPane.WARNING_MESSAGE);
			
		}
		
		
		if (e.getSource().equals(this.explication))
			new FrameExplication(this);
		
	}

	public void toutDecocher ()
	{
		for (PanelCreerReponsesElim p : this.reponsesPossibles)
			p.decocher();
	}

	public void   setTxtExplication  (String expli)	{this.txtExplication=expli	;}
	public String getTextExplication (			  )	{return this.txtExplication	;}
}
