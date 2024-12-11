//package src.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class PanelCreerElim extends JPanel implements ActionListener 
{
	private PanelCreerQuestion panelQ;
	private ArrayList<PanelCreerReponsesElim> reponsesPossibles;
	private JTextArea question;
	private JButton ajouterQ, explication, enreg;
	private JPanel panelReponses;  
	private String txtExplication;

	public PanelCreerElim(PanelCreerQuestion panelQ) 
	{
		this.panelQ = panelQ;

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

		this.ajouterQ 	 = new JButton(new ImageIcon("img/ajouter.PNG"	));
		this.explication = new JButton(new ImageIcon("img/modifier.PNG"	));
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
		if (e.getSource() == this.ajouterQ) 
		{
			this.reponsesPossibles.add(new PanelCreerReponsesElim(this));
			mettreAJourReponses();
		} 
		if (e.getSource() == this.enreg) 
		{
			ArrayList<String> lstReponses = new ArrayList<String>();
			for (PanelCreerReponsesElim p : this.reponsesPossibles)
				lstReponses.add(p.getString());

			this.panelQ.creerElim(this.txtExplication, this.question.getText(), lstReponses);
		}
		
		
		if (e.getSource().equals(this.explication))
			new FrameExplication(this);
		
	}

	public void   setTxtExplication  (String expli)	{this.txtExplication=expli	;}
	public String getTextExplication (			  )	{return this.txtExplication	;}
}
