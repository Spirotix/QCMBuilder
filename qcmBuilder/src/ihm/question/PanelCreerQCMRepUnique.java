package src.ihm.question;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import src.Controleur;
import src.ihm.*;

public class PanelCreerQCMRepUnique extends JPanel implements ActionListener {
	private PanelCreerQuestion panelQ;
	private ArrayList<PanelReponse> reponsesPossibles;
	private JTextArea question;
	private JButton ajouterQ, explication, enreg;
	private JPanel panelReponses;
	private String txtExplication;
	private FrameCreerQCMRepUnique fr;
	private Controleur ctrl;
	private String type;
	private int indiceReponse;

	public PanelCreerQCMRepUnique(PanelCreerQuestion panelQ, FrameCreerQCMRepUnique fr, Controleur ctrl, String type) {
		this.panelQ = panelQ;
		this.ctrl = ctrl;
		this.fr = fr;
		this.type = type;
		this.indiceReponse = 1;

		this.reponsesPossibles = new ArrayList<>();
		this.reponsesPossibles.add(new PanelReponse(this, this.type, this.indiceReponse++));
		this.reponsesPossibles.add(new PanelReponse(this, this.type, this.indiceReponse++));

		this.setLayout(new BorderLayout());

		JPanel panelQuestion = new JPanel(new BorderLayout());
		panelQuestion.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelQuestion.add(new JLabel("Question : "), BorderLayout.NORTH);

		this.question = new JTextArea(5, 30);
		this.question.setLineWrap(true);
		this.question.setWrapStyleWord(true);

		JScrollPane questionScrollPane = new JScrollPane(this.question);
		questionScrollPane.setPreferredSize(new Dimension(400, 100));
		panelQuestion.add(questionScrollPane, BorderLayout.CENTER);

		this.add(panelQuestion, BorderLayout.NORTH);

		JPanel panelReponsesContainer = new JPanel(new BorderLayout());
		panelReponsesContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel panelLabels = new JPanel(new GridLayout(1, 3));
		panelLabels.add(new JLabel("              Supprimé"));
		panelLabels.add(new JLabel("         Contenu"));
		panelLabels.add(new JLabel("  Bonne réponse"));
		panelReponsesContainer.add(panelLabels, BorderLayout.NORTH);

		panelReponses = new JPanel();
		panelReponses.setLayout(new BoxLayout(panelReponses, BoxLayout.Y_AXIS));
		panelReponses.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		mettreAJourReponses();

		JScrollPane scrollPaneReponses = new JScrollPane(panelReponses);
		scrollPaneReponses.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		panelReponsesContainer.add(scrollPaneReponses, BorderLayout.CENTER);

		this.add(panelReponsesContainer, BorderLayout.CENTER);

		JPanel panelBoutons = new JPanel();
		panelBoutons.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		this.ajouterQ = new JButton(new ImageIcon("../img/ajouter.PNG"));
		this.explication = new JButton(new ImageIcon("../img/modifier.PNG"));
		this.enreg = new JButton("Enregistrer");

		// Style des boutons
		ajouterQ.setBackground(Color.LIGHT_GRAY);
		explication.setBackground(Color.LIGHT_GRAY);
		enreg.setBackground(Color.LIGHT_GRAY);

		panelBoutons.add(this.ajouterQ);
		panelBoutons.add(this.explication);
		panelBoutons.add(this.enreg);

		this.add(panelBoutons, BorderLayout.SOUTH);

		this.ajouterQ.addActionListener(this);
		this.explication.addActionListener(this);
		this.enreg.addActionListener(this);
	}

	public void toutDecocher() {
		for (PanelReponse p : this.reponsesPossibles) {
			p.decocher();
		}
	}

	private void mettreAJourReponses() {
		panelReponses.removeAll();
		for (PanelReponse p : this.reponsesPossibles) {
			panelReponses.add(p);
			p.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY)); // Bordure entre les réponses
		}
		panelReponses.revalidate();
		panelReponses.repaint();
	}

	public void supprimer(PanelReponse p) {
		this.reponsesPossibles.remove(p);
		this.mettreAJourReponses();
	}

	public void actionPerformed(ActionEvent e) {
		boolean aUnBon = false;

		if (e.getSource() == this.ajouterQ) {
			this.reponsesPossibles.add(new PanelReponse(this, this.type, this.indiceReponse++));
			mettreAJourReponses();
		}
		if (e.getSource() == this.enreg) {
			if (this.question.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Remplissez le champ de question", "Attention", JOptionPane.WARNING_MESSAGE);
				return;
			}

			for (PanelReponse p : this.reponsesPossibles) {
				if (p.getString().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Remplissez les champ de réponses", "Attention", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if (p.getEstBonneReponse()) {
					aUnBon = true;
				}
			}

			if (!aUnBon) {
				JOptionPane.showMessageDialog(null, "Choissisez au moins une bonne réponse", "Attention", JOptionPane.WARNING_MESSAGE);
				return;
			}

			ArrayList<TypeReponse> reponses = new ArrayList<>();
			for (PanelReponse p : this.reponsesPossibles) {
				reponses.add(new TypeReponse(p.getString(), p.getEstBonneReponse()));
			}

			if (this.panelQ.creerQuestion(this.txtExplication, this.question.getText(), reponses)) {
				this.fr.dispose();
				new FrameMenu(this.ctrl);
			} else {
				JOptionPane.showMessageDialog(null, "Cette Question existe deja", "Attention", JOptionPane.WARNING_MESSAGE);
			}
		}

		if (e.getSource().equals(this.explication)) {
			new FrameExplication(this);
		}
	}

	public void setTxtExplication(String expli) {
		this.txtExplication = expli;
	}

	public String getTextExplication() {
		return this.txtExplication;
	}
}
