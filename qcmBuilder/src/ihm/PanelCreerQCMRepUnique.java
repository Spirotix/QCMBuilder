import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class PanelCreerQCMRepUnique extends JPanel implements ActionListener 
{
	private PanelCreerQuestion panelQ;
	private ArrayList<PanelReponse> reponsesPossibles;
	private JTextArea question;
	private JButton ajouterQ, explication, enreg;
	private JPanel panelReponses; 

	public PanelCreerQCMRepUnique(PanelCreerQuestion panelQ) 
	{
		this.panelQ = panelQ;

		this.reponsesPossibles = new ArrayList<>();
		this.reponsesPossibles.add(new PanelReponse(this.panelQ));
		this.reponsesPossibles.add(new PanelReponse(this.panelQ));

		this.setLayout(new BorderLayout());

		JPanel panelQuestion = new JPanel(new BorderLayout());
		panelQuestion.add(new JLabel("Question : "), BorderLayout.NORTH);

		this.question = new JTextArea(3, 30);
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
		this.ajouterQ = new JButton("Ajouter réponse");
		this.explication = new JButton("Ajouter explication");
		this.enreg = new JButton("Enregistrer");
		panelBoutons.add(this.ajouterQ);
		panelBoutons.add(this.explication);
		panelBoutons.add(this.enreg);

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

	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == this.ajouterQ) 
		{
			this.reponsesPossibles.add(new PanelReponse(this.panelQ));
			mettreAJourReponses();
		} 
		else if (e.getSource() == this.enreg) 
			System.out.println("Question enregistrée : " + this.question.getText());
	}
}
