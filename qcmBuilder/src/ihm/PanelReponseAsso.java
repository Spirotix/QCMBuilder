//package src.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class PanelReponseAsso extends JPanel implements ActionListener
{
	private PanelCreerQuestionAsso 	panelQ;
	private ArrayList<JPanel>	reponsesPossibles;
	private JButton				corbeilleGauche, corbeilleDroite;
	private JTextField			contenuGauche, contenuDroite	;

	public PanelReponseAsso (PanelCreerQuestionAsso panelQ)
	{
		this.panelQ = panelQ;
		this.setLayout(new GridLayout(1,6));

		//Initialisation
		this.corbeilleGauche= new JButton(new ImageIcon("img/poubelle.PNG"));
		this.contenuGauche 	= new JTextField ();
		this.contenuDroite 	= new JTextField ();
		this.corbeilleGauche= new JButton(new ImageIcon("img/poubelle.PNG"));

		//Insertion
		this.add (this.corbeilleGauche );
		this.add (this.contenuGauche   );
		this.add (this.contenuDroite   );
		this.add (this.corbeilleDroite );

		//ActionListener / itemListener
		this.corbeilleGauche .addActionListener(this);
		this.contenuGauche	 .addActionListener(this);
		this.contenuDroite   .addActionListener(this);
		this.corbeilleDroite .addActionListener(this);

		this.setVisible(true);

	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(this.corbeilleGauche) || e.getSource().equals(this.corbeilleDroite))
		{
			this.panelQ.supprimer(this);
		}
	}

	

	public String getString()
	{
		String str = "";

		str+=this.contenuGauche.getText();
		
		
		return str;
	}
}