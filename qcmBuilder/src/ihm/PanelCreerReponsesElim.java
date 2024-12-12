//package src.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PanelCreerReponsesElim extends JPanel implements ActionListener
{
	private PanelCreerElim 	panelQ;
	private JButton				corbeille;
	private JTextField			ordre, cout, contenu	;
	private JCheckBox			validation ; 

	public PanelCreerReponsesElim (PanelCreerElim panelQ)
	{
		this.panelQ = panelQ;
		this.setLayout(new GridLayout(1,4));

		//Initialisation
		this.corbeille = new JButton(new ImageIcon("img/poubelle.PNG"));
		this.contenu 	= new JTextField ();
		this.ordre 		= new JTextField ();
		this.cout 		= new JTextField ();
		this.validation = new JCheckBox();

		//Insertion
		this.add (this.corbeille );
		this.add (this.contenu	 );

		this.ordre.setColumns(10);
		this.cout.setColumns(10);

		JPanel temp = new JPanel();
		temp.setLayout(new GridLayout(2,1));
		
		temp.add (this.ordre	 );
		temp.add (this.cout);

		this.add(temp);
		this.add (this.validation);
		

		//ActionListener / itemListener
		this.corbeille  .addActionListener(this);
		this.contenu	.addActionListener(this);
		this.validation .addActionListener(this);
		this.ordre		.addActionListener(this);
		this.cout 		.addActionListener(this);

		this.setVisible(true);

	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(this.corbeille))
		{
			this.panelQ.supprimer(this);
		}
	}


	

	public String getString(){return this.contenu.getText();}
	public int    getOrdre()
	{
		int rtnOrdre;

		try 
		{
			rtnOrdre = Integer.parseInt(this.ordre.getText());
			return rtnOrdre;
		} 
		catch (Exception ex) 
		{
			System.out.println("Ce n'est pas un entier (ordre)");
			return 0;
		}
	}

	public int    getCout()
	{
		int rtnCout;

		try 
		{
			rtnCout = Integer.parseInt(this.cout.getText());
			return rtnCout;
		} 
		catch (Exception ex) 
		{
			System.out.println("Ce n'est pas un entier (cout)");
			return 0;
		}
	}

	public boolean  getEstBonneReponse ( )	{return this.validation.isSelected();}

}