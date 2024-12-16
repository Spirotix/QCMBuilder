package src.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import src.Controleur;

public class PanelAfficherQuestion extends JPanel implements ActionListener
{
	private Controleur 	ctrl	;
	private ArrayList<JPanel> 	lstLabel;
	private String				ressource, notion;
	private ArrayList<JButton> 	btnModif, btnSup;

	public PanelAfficherQuestion (Controleur ctrl)
	{
		this.ctrl=ctrl;
		this.ressource="";
		this.notion="";
		
		this.lstLabel = new ArrayList<JPanel>();
		this.btnModif = new ArrayList<JButton>();
		this.btnSup	  = new ArrayList<JButton>();

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.setVisible(true);
	}

	public void Update(String ressource,String notion)
	{
		this.ressource  = ressource	;
		this.notion		= notion 	;
		JPanel temp;
		System.out.println("test");

		this.lstLabel = new ArrayList<JPanel>();
		this.removeAll();

		for (int i=0; i<this.ctrl.getQuestions(this.ressource, this.notion).size(); i++)
		{
			temp = new JPanel ();
			temp.add(new JLabel(this.ctrl.getQuestions(this.ressource, this.notion).get(i)));

			this.btnModif.add(new JButton("Modifier"));
			this.btnModif.get(i).addActionListener(this);
			temp.add(this.btnModif.get(i));

			this.btnSup.add(new JButton("Supprimer"));
			this.btnSup.get(i).addActionListener(this);
			temp.add(this.btnSup.get(i));

			this.lstLabel.add(temp);
			this.add(this.lstLabel.get(i));
		}

		this.revalidate();

	}

	public void actionPerformed(ActionEvent e) 
	{
		for (JButton j : this.btnModif)
		{
			if (e.getSource().equals(j))
			{
				System.out.println("test1");
			}
		}
		for (JButton j : this.btnSup)
		{
			if (e.getSource().equals(j))
				System.out.println("test2");
		}
	}
}