package src.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import src.Controleur;

public class PanelNotion extends JPanel 
{
	private Controleur 	ctrl	;
	private PanelCreerRessource panelC	;
	private ArrayList<JLabel> 	lstLabel;
	private String				ressource;

	public PanelNotion (Controleur ctrl, PanelCreerRessource panelC)
	{
		this.ctrl=ctrl;
		this.panelC=panelC	;
		this.ressource="";
		
		this.lstLabel = new ArrayList<JLabel>();

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.setVisible(true);
	}

	public void Update(String ressource)
	{
		this.ressource = ressource;
		this.lstLabel = new ArrayList<JLabel>();
		this.removeAll();

		for (String s : this.ctrl.getChoixNotion(this.ressource))
			this.lstLabel.add(new JLabel(s));

		for (JLabel j : lstLabel)
		{
			j.addMouseListener(new MouseAdapter() 
			{
				@Override
				public void mouseClicked(MouseEvent e) 
				{
					//JOptionPane.showMessageDialog(frame, "Label cliqué !");
					System.out.println(j.getText());
				}
			});
			this.add(j);
		}
		this.revalidate();

	}

	public String getRessource() {return this.ressource;}

	public void ajouter(String nomNotion)
	{
		//this.ctrl.ajouterNotion(nomNotion);
		this.Update(this.ressource);
	}
}