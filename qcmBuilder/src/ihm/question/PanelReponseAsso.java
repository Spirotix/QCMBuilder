//package src.ihm;
package src.ihm.question;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class PanelReponseAsso extends JPanel implements ActionListener
{
	private PanelCreerQuestionAsso 			panelQ;
	private JButton							corbeille;
	private JTextArea						contenuGauche  , contenuDroite	;	

	public PanelReponseAsso (PanelCreerQuestionAsso panelQ)
	{
		this.panelQ = panelQ;

		//Initialisation
		JPanel panelGauche = new JPanel();
		JPanel panelDroite = new JPanel();

		this.corbeille		= new JButton(new ImageIcon("img/poubelle.PNG"));
		this.contenuGauche 	= new JTextArea (2,15);
		this.contenuDroite 	= new JTextArea (2,15);

		this.add(this.corbeille);
		this.add(this.contenuGauche  );

		this.corbeille.addActionListener(this);

		this.add(this.contenuDroite);


		this.setVisible(true);

	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(this.corbeille))
			this.panelQ.supprimer(this);
	}

	public String toString			() {return this.contenuGauche.getText() + " : "+this.contenuDroite.getText();}
	public String getContenuGauche 	() {return this.contenuGauche.getText()										;}
	public String getContenuDroite 	() {return this.contenuDroite.getText()										;}
}