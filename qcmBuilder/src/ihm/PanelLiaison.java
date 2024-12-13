package src.ihm;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import javax.swing.*;


public class PanelLiaison extends JPanel implements ActionListener 
{
	private JPanel panelGauche;
	private JPanel panelDroite;
	private JPanel panelBas;

	private JPanel panelBtnValider;
	private JButton boutonValider;

	private JButton boutonAjouterD;
	private JButton boutonAjouterG;

	private List<JTextField> textFieldsGauche;
	private List<JTextField> textFieldsDroite;
	private List<JButton> boutonsGauche;
	private List<JButton> boutonsDroite;
	private JButton boutonGaucheSelectionne = null;
	private JButton boutonDroiteSelectionne = null;

	private PanelCreerQuestionAsso panelC;

	private List<Liaison> liaisons;

	private Graphics2D g2;

	public PanelLiaison(PanelCreerQuestionAsso panelC) 
	{
		this.panelC = panelC;
		this.setLayout(new BorderLayout());

		this.panelGauche = new JPanel();
		this.panelGauche.setLayout(new GridLayout(10, 1));
		this.panelGauche.setOpaque(false);

		this.textFieldsGauche = new ArrayList<>();
		this.boutonsGauche = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			this.ajouterBouton("Lier", this.boutonsGauche, textFieldsGauche, this.panelGauche, 'G');
		}

		this.panelDroite = new JPanel();
		this.panelDroite.setLayout(new GridLayout(10, 1));
		this.panelDroite.setOpaque(false);

		this.textFieldsDroite = new ArrayList<>();
		this.boutonsDroite = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			this.ajouterBouton("Lier", this.boutonsDroite, textFieldsDroite, this.panelDroite, 'D');
		}

		this.panelBas = new JPanel();
		this.panelBas.setLayout(new BorderLayout());
		this.panelBas.setOpaque(false);

		this.boutonAjouterG = new JButton("Ajouter");
		this.boutonAjouterG.addActionListener(this);
		this.panelBas.add(this.boutonAjouterG, BorderLayout.WEST);

		this.boutonAjouterD = new JButton("Ajouter");
		this.boutonAjouterD.addActionListener(this);
		this.panelBas.add(this.boutonAjouterD, BorderLayout.EAST);

		this.panelBtnValider = new JPanel();
		this.panelBtnValider.setOpaque(false);
		this.boutonValider = new JButton("Valider");
		this.boutonValider.addActionListener(this);
		this.panelBtnValider.add(this.boutonValider);
		this.panelBas.add(panelBtnValider, BorderLayout.CENTER);

		this.add(this.panelGauche, BorderLayout.WEST);
		this.add(this.panelDroite, BorderLayout.EAST);
		this.add(this.panelBas, BorderLayout.SOUTH);

		this.liaisons = new ArrayList<>();
	}

	private void ajouterBouton(String texte, List<JButton> boutons, List<JTextField> texts, JPanel panel, char dir) {
		if (boutons.size() >= 10) {
			return;
		}
		Random r = new Random();

		JTextField textField = new JTextField(10);
		textField.addActionListener(this);
		textField.setText("Test"+r.nextInt(10));
		texts.add(textField);

		JPanel panelBouton = new JPanel(new BorderLayout());
		JButton bouton = new JButton(texte);
		bouton.addActionListener(this);
		boutons.add(bouton);

		JButton boutonSupprimer = new JButton(new ImageIcon("img/poubelle.PNG"));
		boutonSupprimer.addActionListener(this);

		panelBouton.add(textField, BorderLayout.CENTER);

		if (dir == 'D') {
			panelBouton.add(boutonSupprimer, BorderLayout.EAST);
			panelBouton.add(bouton, BorderLayout.WEST);
		} else {
			panelBouton.add(boutonSupprimer, BorderLayout.WEST);
			panelBouton.add(bouton, BorderLayout.EAST);
		}

		panel.add(panelBouton);
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		for (JButton bouton : boutonsGauche) {
			if (source == bouton) {
				boutonGaucheSelectionne = bouton;
				break;
			}
		}

		for (JButton bouton : boutonsDroite) {
			if (source == bouton) {
				boutonDroiteSelectionne = bouton;
				break;
			}
		}

		if (boutonGaucheSelectionne != null && boutonDroiteSelectionne != null) {
			JPanel panelGaucheSelectionne = (JPanel) boutonGaucheSelectionne.getParent();
			JPanel panelDroiteSelectionne = (JPanel) boutonDroiteSelectionne.getParent();
			Liaison nouvelleLiaison = new Liaison(panelGaucheSelectionne, panelDroiteSelectionne);

			boolean exists = false;
			for (Liaison liaison : liaisons) {
				if (liaison.panelItemGauche == nouvelleLiaison.panelItemGauche && liaison.panelItemDroite == nouvelleLiaison.panelItemDroite) {
					exists = true;
					break;
				}
			}
			if (!exists) {
				liaisons.add(nouvelleLiaison);
			}
			boutonGaucheSelectionne = null;
			boutonDroiteSelectionne = null;
			repaint();
		}

		if (source == boutonAjouterG) {
			ajouterBouton("Lier", boutonsGauche, textFieldsGauche, panelGauche, 'G');
			revalidate();
			repaint();
		}

		if (source == boutonAjouterD) {
			ajouterBouton("Lier", boutonsDroite, textFieldsDroite, panelDroite, 'D');
			revalidate();
			repaint();
		}

		for (int i = 0; i < panelGauche.getComponentCount(); i++) {
			JPanel panelBouton = (JPanel) panelGauche.getComponent(i);
			JButton boutonSupprimer = (JButton) panelBouton.getComponent(1);
			if (source == boutonSupprimer) {
				boutonsGauche.remove(i);
				panelGauche.remove(i);
				liaisons.removeIf(liaison -> liaison.panelItemGauche == panelBouton);
				revalidate();
				repaint();
				break;
			}
		}

		for (int i = 0; i < panelDroite.getComponentCount(); i++) {
			JPanel panelBouton = (JPanel) panelDroite.getComponent(i);
			JButton boutonSupprimer = (JButton) panelBouton.getComponent(1);
			if (source == boutonSupprimer) {
				boutonsDroite.remove(i);
				panelDroite.remove(i);
				liaisons.removeIf(liaison -> liaison.panelItemDroite == panelBouton);
				revalidate();
				repaint();
				break;
			}
		}

		

		if (source == boutonValider)
		{
			System.out.println("Liaisons : " + liaisons);

			
		}
	}

	public ArrayList<TypeReponse> getReponses()
	{
		ArrayList<TypeReponse> lstReponses  = new ArrayList<TypeReponse>();

		for (JTextField jtf : this.textFieldsGauche)
			lstReponses.add(new TypeReponse(jtf.getText(), "Gauche"));
		
		for (JTextField jtf : this.textFieldsDroite)
			lstReponses.add(new TypeReponse(jtf.getText(), "Droite"));
			
		ArrayList<JTextField> lstReponsesG = new ArrayList<JTextField>();
		ArrayList<JTextField> lstReponsesD = new ArrayList<JTextField>();

		for (Liaison l : this.liaisons)
			lstReponsesG.add ((JTextField) l.panelItemGauche.getComponent(0));

		for (Liaison l : this.liaisons)
			lstReponsesD.add ((JTextField) l.panelItemDroite.getComponent(0));

		for (int i=0; i<this.liaisons.size();i++)
			for (TypeReponse t : lstReponses)
				if (t.getContenu().equals(lstReponsesG.get(i).getText()) && t.getPosition().equals("Gauche"))
					for (TypeReponse t2 : lstReponses)
						 if (t2.getContenu().equals(lstReponsesD.get(i).getText()) && t2.getPosition().equals("Droite"))
						 	t.ajouterLiaison(t2);
		
		return lstReponses;
				
	} 	

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g2 = (Graphics2D) g;
		g2.setStroke(new java.awt.BasicStroke(3));

		for (Liaison liaison : liaisons) {
			int indexG = -1;
			int indexD = -1;
			for (int i = 0; i < panelGauche.getComponentCount(); i++) {
				if (panelGauche.getComponent(i) == liaison.panelItemGauche) {
					indexG = i;
					break;
				}
			}
			for (int i = 0; i < panelDroite.getComponentCount(); i++) {
				if (panelDroite.getComponent(i) == liaison.panelItemDroite) {
					indexD = i;
					break;
				}
			}
			if (indexG != -1 && indexD != -1) {
				g2.drawLine(240, indexG * 54 + 25, 760, indexD * 54 + 25);
			}
		}
	}

	private class Liaison {
		JPanel panelItemGauche;
		JPanel panelItemDroite;

		Liaison(JPanel panelItemGauche, JPanel panelItemDroite) {
			this.panelItemGauche = panelItemGauche;
			this.panelItemDroite = panelItemDroite;
		}

		@Override
		public String toString() {
			JTextField textFieldGauche = (JTextField) panelItemGauche.getComponent(0);
			JTextField textFieldDroite = (JTextField) panelItemDroite.getComponent(0);
			return textFieldGauche.getText() + " -> " + textFieldDroite.getText();
		}
	}
}
