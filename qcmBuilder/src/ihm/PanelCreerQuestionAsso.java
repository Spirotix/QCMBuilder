package src.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class PanelCreerQuestionAsso extends JPanel implements ActionListener 
{
	private PanelCreerQuestion panelQ;
	private ArrayList<PanelReponseAsso> reponsesPossibles;

	private JTextArea question;
	private JButton ajouterQ, explication, enreg;
	private PaintComponentAsso panelReponses; 
	private String txtExplication;
	

	public PanelCreerQuestionAsso(PanelCreerQuestion panelQ) 
	{
		this.panelQ = panelQ;



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

		this.panelReponses = new PaintComponentAsso(this);
		this.panelReponses.setLayout(new BoxLayout(this.panelReponses, BoxLayout.Y_AXIS));
		mettreAJourReponses(); 

		JScrollPane scrollPaneReponses = new JScrollPane(this.panelReponses);
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

	


	private void mettreAJourReponses() 
	{
		this.panelReponses.removeAll();
		for (PanelReponseAsso p : this.reponsesPossibles) 
			this.panelReponses.add(p);
		
		this.panelReponses.revalidate();
		this.panelReponses.repaint();
	}

	public ArrayList<PanelReponseAsso> getLstReponses() 
	{
		return this.reponsesPossibles;
	}

	public PaintComponentAsso getPaintComponent() {return this.panelReponses;}

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
