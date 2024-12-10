//package src.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class PanelReponseGaucheAsso extends JPanel implements ActionListener
{
	private PanelReponseAsso 		panelQ;
	private JButton					corbeille;
	private JButton					lier;
	private JTextArea				contenu;
	private ArrayList<PanelReponseDroiteAsso>  lstLiaison;
	private static int 				nbPanel = 0;
	private String 					nom ;

	public PanelReponseGaucheAsso (PanelReponseAsso panelQ)
	{
		this.lstLiaison = new ArrayList<PanelReponseDroiteAsso>();
		this.nom = "Panel Gauche "+ ++PanelReponseGaucheAsso.nbPanel;
		this.panelQ = panelQ;
		this.setLayout(new FlowLayout());

		//Initialisation
		this.corbeille	= new JButton(new ImageIcon("img/poubelle.PNG"));
		this.contenu 	= new JTextArea (2,10);
		this.lier		= new JButton("Lier");

		//Insertion
		this.add (this.corbeille 	);
		this.add (this.contenu	);
		this.add (this.lier		);

		//ActionListener / itemListener
		this.corbeille .addActionListener(this);
		/*this.contenuGauche	 .addActionListener(this);
		this.contenuDroite   .addActionListener(this);*/
		this.lier .addActionListener(this);

		this.setVisible(true);

	}

	public String getNom () {return this.nom;}

	public void ajouterBonneRep (PanelReponseDroiteAsso pb)
	{
		for (PanelReponseDroiteAsso pi : this.lstLiaison)
			if (pi.equals(pb))
				return;
		System.out.println("test");
		this.lstLiaison.add(pb);

		for (PanelReponseDroiteAsso pl : this.lstLiaison)
				System.out.println(this.nom+" liste " + pl.getNom());
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

			for (PanelReponseDroiteAsso pl : this.lstLiaison)
				System.out.println(this.nom+" liste " + pl.getNom());
		}
	}

	

	public String getString()
	{
		String str = "";

		str+=this.contenu.getText();
		
		
		return str;
	}
}