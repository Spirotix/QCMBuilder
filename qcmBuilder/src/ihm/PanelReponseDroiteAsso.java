//package src.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class PanelReponseDroiteAsso extends JPanel implements ActionListener
{
	private PanelReponseAsso 	panelQ;
	private JButton				corbeille;
	private JButton				lier;
	private JTextArea			contenu;
	private static int 			nbPanel = 0;
	private String 				nom ;

	public PanelReponseDroiteAsso (PanelReponseAsso panelQ)
	{
		this.panelQ = panelQ;
		this.nom = "Panel Droite "+ ++PanelReponseDroiteAsso.nbPanel;
		this.setLayout(new FlowLayout());

		//Initialisation
		this.corbeille	= new JButton(new ImageIcon("img/poubelle.PNG"));
		this.contenu 	= new JTextArea (2,10);
		this.lier		= new JButton("Lier");

		//Insertion
		this.add (this.lier		);
		this.add (this.contenu	);
		this.add (this.corbeille 	);

		//ActionListener / itemListener
		this.corbeille .addActionListener(this);
		/*this.contenuGauche	 .addActionListener(this);
		this.contenuDroite   .addActionListener(this);*/
		this.lier .addActionListener(this);

		this.setVisible(true);

	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(this.corbeille))
		{
			this.panelQ.supprimer(this);
		}

		if (e.getSource().equals(this.lier))
		{
			this.panelQ.lier(this);
		}
		this.panelQ.getPanelDeBase().getPaintComponent().repaint();
	}

	public String getNom () {return this.nom;}

	public JButton getBoutton() {return this.lier;}

	public String getString()
	{
		String str = "";

		str+=this.contenu.getText();
		
		
		return str;
	}
}