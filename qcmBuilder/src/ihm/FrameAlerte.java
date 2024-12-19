package src.ihm;

import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import src.Controleur;

public class FrameAlerte extends JFrame implements ActionListener
{
	private Controleur		ctrl		;

	private PanelRessource 	panelR		;
	private String 			nomRessource;

	private PanelNotion 	panelN 		;
	private String 			nomNotion 		;

	private JButton			valider		;
	private JButton			annuler		; 
	private JPanel 			panel 		;

	public FrameAlerte (Controleur ctrl, PanelRessource panelR, String ressource)
	{
		this.setTitle	("Attention");
		this.setSize	( 500,350	);

		this.ctrl	= ctrl  ;
		this.panelR = panelR;
		this.nomRessource = ressource;

		this.panel = new JPanel (new BorderLayout());

		this.valider 	= new JButton 	("valider");
		this.annuler 	= new JButton 	("annuler");
		JLabel message1	= new JLabel	("\tVoulez vous supprimer la ressource : "+this.nomRessource +"?");
		JLabel message2	= new JLabel	("\tCela supprimera les notions suivantes ainsi que leurs questions : ");

		ArrayList<JLabel> lstNotion = new ArrayList<JLabel>();
		for (String s : this.ctrl.getChoixNotion(ressource))
			lstNotion.add(new JLabel("\t"+s));
		
		JPanel panelMilieu = new JPanel(new GridLayout (lstNotion.size()+2,1));
		panelMilieu.add(message1);
		panelMilieu.add(message2);

		for (JLabel jl : lstNotion)
			panelMilieu.add(jl);

		JPanel panelBas = new JPanel();
		panelBas.add (this.valider);
		panelBas.add (this.annuler);

		this.panel.add(panelMilieu, BorderLayout.CENTER);
		this.panel.add(panelBas	  , BorderLayout.SOUTH);

		this.valider.addActionListener(this);
		this.annuler.addActionListener(this);

		this.add (this.panel);
		this.setVisible(true);
	}

	public FrameAlerte (Controleur ctrl, PanelNotion panelN, String notion)
	{
		this.setTitle	("Attention");
		this.setSize	( 500,350	);

		this.ctrl	= ctrl  ;
		this.panelN = panelN;
		this.nomNotion = notion;

		this.panel = new JPanel (new BorderLayout());

		this.valider 	= new JButton 	("valider");
		this.annuler 	= new JButton 	("annuler");
		JLabel message1	= new JLabel	("\tVoulez vous supprimer la Notion : "+this.nomNotion +"?");
		JLabel message2	= new JLabel	("\tCela supprimera les questions suivantes : ");

		ArrayList<JLabel> lstNotion = new ArrayList<JLabel>();
		for (String s : this.ctrl.getQuestions( this.panelN.getRessource(),this.nomNotion))
			lstNotion.add(new JLabel("\t"+s));
		
		JPanel panelMilieu = new JPanel(new GridLayout (lstNotion.size()+2,1));
		panelMilieu.add(message1);
		panelMilieu.add(message2);

		for (JLabel jl : lstNotion)
			panelMilieu.add(jl);

		JPanel panelBas = new JPanel();
		panelBas.add (this.valider);
		panelBas.add (this.annuler);

		this.panel.add(panelMilieu, BorderLayout.CENTER);
		this.panel.add(panelBas	  , BorderLayout.SOUTH);

		this.valider.addActionListener(this);
		this.annuler.addActionListener(this);

		this.add (this.panel);
		this.setVisible(true);
	}


	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(this.valider))
		{
			if (this.nomRessource!=null)
				this.panelR.supprimerRessource(this.nomRessource);

			else if (this.nomNotion!=null)
				this.panelN.supprimerNotion(this.nomNotion);

		}
		this.dispose();
	}

	
}