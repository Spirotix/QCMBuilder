package src.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class PanelReponse extends JPanel implements ActionListener
{
	private PanelCreerQCMRepUnique 	panelQ;
	private ArrayList<JPanel>	reponsesPossibles;
	private JButton				corbeille;
	private JTextField			contenu	;
	private JCheckBox			validation ; 

	public PanelReponse (PanelCreerQCMRepUnique panelQ)
	{
		this.panelQ = panelQ;
		this.setLayout(new GridLayout(1,3));

		//Initialisation
		this.corbeille = new JButton(new ImageIcon("img/poubelle.PNG"));
		this.contenu 	= new JTextField ();
		this.validation = new JCheckBox();

		//Insertion
		this.add (this.corbeille );
		this.add (this.contenu	 );
		this.add (this.validation);

		//ActionListener / itemListener
		this.corbeille  .addActionListener(this);
		this.contenu	.addActionListener(this);
		this.validation .addActionListener(this);

		this.setVisible(true);

	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(this.corbeille))
		{
			this.panelQ.supprimer(this);
		}
	}

	

	public String getString()
	{
		String str = "";

		str+=this.contenu.getText();
		if (this.validation.isSelected())
			str+="_OUI";
		else 
			str+="_NON";
		
		return str;
	}
}