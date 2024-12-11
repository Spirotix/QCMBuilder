package src.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class PanelCreerQuestionAsso extends JPanel implements ActionListener 
{
	private PanelCreerQuestion panelQ;
	private ArrayList<PanelReponseAsso> reponsesPossibles;
	private ArrayList<PanelReponseGaucheAsso> reponsesPossiblesGauche;
	private ArrayList<PanelReponseDroiteAsso>  reponsesPossiblesDroite;
	private JTextArea question;
	private JButton ajouterQ, explication, enreg;
	private JPanel panelReponses; 
	private String txtExplication;
	

	public PanelCreerQuestionAsso(PanelCreerQuestion panelQ) 
	{
		this.panelQ = panelQ;

		
		this.reponsesPossiblesGauche = new ArrayList<PanelReponseGaucheAsso>();
		this.reponsesPossiblesDroite = new ArrayList<PanelReponseDroiteAsso>();

		this.reponsesPossibles =  new ArrayList<PanelReponseAsso>();
		this.reponsesPossibles.add(new PanelReponseAsso(this));
		this.reponsesPossibles.add(new PanelReponseAsso(this));

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

		this.ajouterQ 	 = new JButton(new ImageIcon("img/ajouter.PNG"	));
		this.explication = new JButton(new ImageIcon("img/modifier.PNG"));
		this.enreg 		 = new JButton("Enregistrer"                        );


		panelBoutons.add(this.ajouterQ	 );
		panelBoutons.add(this.explication);
		panelBoutons.add(this.enreg      );

		this.add(panelBoutons, BorderLayout.SOUTH);

		this.ajouterQ.addActionListener(this);
		this.explication.addActionListener(this);
		this.enreg.addActionListener(this);
	}

	public void addReponseGauche(PanelReponseGaucheAsso p)
	{
		this.reponsesPossiblesGauche.add(p);
	}

	public void addReponseDroite(PanelReponseDroiteAsso p)
	{
		this.reponsesPossiblesDroite.add(p);
	}


	private void mettreAJourReponses() 
	{
		panelReponses.removeAll();
		for (PanelReponseAsso p : this.reponsesPossibles) 
			panelReponses.add(p);
		
		panelReponses.revalidate();
		panelReponses.repaint();
	}

	public void supprimer(PanelReponseAsso p)
	{
		this.reponsesPossibles.remove(p);
		this.mettreAJourReponses();
	}

	
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == this.ajouterQ) 
		{
			this.reponsesPossibles.add(new PanelReponseAsso(this));
			mettreAJourReponses();
		} 
		if (e.getSource() == this.enreg) 
		{
			ArrayList<String> lstReponses = new ArrayList<String>();
			for (PanelReponseAsso p : this.reponsesPossibles)
				lstReponses.add(p.getString());

			this.panelQ.creer(this.txtExplication, this.question.getText(), lstReponses);
		}
			
		
		if (e.getSource().equals(this.explication))
			new FrameExplication(this);
		
	}

	public void   setTxtExplication  (String expli) {this.txtExplication=expli  ;}
	public String getTextExplication (            ) {return this.txtExplication ;}
}
