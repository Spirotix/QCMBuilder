package src.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import src.Controleur;

public class PanelListerQuestion extends JPanel implements  ItemListener 
{
	private Controleur 			  ctrl						  ;
	private Choice				  choixRessource, choixNotion ;
	private String 				  ressourceQuestion			  ;
	private PanelAfficherQuestion paq						  ;
	private JPanel 			 	  panelMilieu				  ;

	public PanelListerQuestion (Controleur ctrl)
	{
		this.ctrl=ctrl;

		this.setLayout (new BorderLayout());

		this.choixRessource =	new Choice();
		this.choixNotion	=	new Choice();

		this.choixRessource.add			("");

		for (String s : this.ctrl.getChoixRessources())
			this.choixRessource.add(s);
		
		this.choixNotion.add			("								"  	);
		this.choixNotion.setEnabled		(false	);

		this.choixRessource	.addItemListener	(this);
		this.choixNotion	.addItemListener	(this);

		this.add(new JLabel("Liste des Question"), BorderLayout.NORTH);

		this.panelMilieu = new JPanel(new GridLayout(1,2));

		JPanel panelGauche = new JPanel(new GridLayout(5,1));
		this.paq = new PanelAfficherQuestion(this.ctrl);
		JPanel temp  = new JPanel();
		JPanel temp2 = new JPanel();

		this.paq.add(new JPanel());

		panelGauche.add(new JLabel("Choix de la ressource"));
		temp.add (this.choixRessource);
		panelGauche.add(temp);

		panelGauche.add(new JLabel("Choix de la notion"));
		temp2.add (this.choixNotion);
		panelGauche.add (temp2);

		JScrollPane scrollPane=new JScrollPane (this.paq);
		scrollPane.setVerticalScrollBarPolicy(scrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		this.panelMilieu.add(panelGauche);
		//this.panelMilieu.add(this.paq);
		this.panelMilieu.add(scrollPane);

		this.add(new JPanel());

		this.add(this.panelMilieu, BorderLayout.CENTER);

	}

	public void majIHM ()
	{
		this.removeAll();
		this.add(new JLabel("Liste des Question"), BorderLayout.NORTH);
		this.add(this.panelMilieu, BorderLayout.CENTER);
		this.revalidate();
	}

	public void itemStateChanged(ItemEvent e) 
	{
		if (e.getSource().equals(this.choixRessource) && !this.choixRessource.getSelectedItem().equals(" ")) 
		{
			this.choixNotion.setEnabled	(true);
			this.choixNotion.removeAll	(	 );
			this.choixNotion.add		(" " );

			this.ressourceQuestion = this.choixRessource.getSelectedItem();
			for (String s : this.ctrl.getChoixNotion(this.choixRessource.getSelectedItem()))
				this.choixNotion.add(s);
		}

		if (e.getSource().equals(this.choixNotion) && !this.choixNotion.getSelectedItem().equals(" ")) 
		{
			this.paq.Update(this.choixRessource.getSelectedItem(), this.choixNotion.getSelectedItem());
			this.repaint();
			this.majIHM ();
		}
	}

}