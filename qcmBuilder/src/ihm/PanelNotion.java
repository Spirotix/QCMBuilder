package src.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import src.Controleur;

public class PanelNotion extends JPanel implements ActionListener
{
	private Controleur 			ctrl	;
	private PanelCreerRessource panelC	;
	private ArrayList<JLabel> 	lstLabel;
	private String				ressource;
	private ArrayList<JButton> 	lstSup	 ;
	private ArrayList<JPanel>	lstPanel ;

	public PanelNotion (Controleur ctrl, PanelCreerRessource panelC)
	{
		this.ctrl=ctrl;
		this.panelC=panelC	;
		this.ressource="";

		
		this.lstLabel 	= new ArrayList<JLabel>();
		this.lstSup 	= new ArrayList<JButton>();
		this.lstPanel 	= new ArrayList<JPanel> ();

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.setVisible(true);
	}

	public void Update(String ressource)
	{
		this.ressource = ressource;
		this.lstLabel	= new ArrayList<JLabel> ();
		this.lstSup 	= new ArrayList<JButton>();
		this.lstPanel 	= new ArrayList<JPanel> ();
		this.removeAll();

		for (int i=0; i<this.ctrl.getChoixNotion(ressource).size(); i++)
		{
			this.lstLabel.add (new JLabel(this.ctrl.getChoixNotion(ressource).get(i)));
			this.lstSup	 .add (new JButton("Supprimer")		);
			this.lstPanel.add (new JPanel()					);
			this.lstPanel.get (i).add(this.lstLabel	.get(i)	);
			this.lstPanel.get (i).add(this.lstSup	.get(i)	);
			this.lstSup	 .get (i).addActionListener(this	);
		}
			
		for (int i=0; i<this.lstLabel.size(); i++)
		{
			JLabel temp = this.lstLabel.get(i);
			this.lstLabel.get(i).addMouseListener(new MouseAdapter() 
			{
				@Override
				public void mouseClicked(MouseEvent e) 
				{
					//JOptionPane.showMessageDialog(frame, "Label cliqué !");
					System.out.println(e);
				}
			});
			this.add(this.lstPanel.get(i));
		}
		if (this.ctrl.getChoixNotion(ressource).size()==0)
			this.removeAll();
		this.revalidate();
	}

	public void actionPerformed(ActionEvent e)
	{
		System.out.println(e);
	}

	public String getRessource() {return this.ressource;}

	public void ajouter(String nomNotion)
	{
		//this.ctrl.ajouterNotion(nomNotion);
		this.Update(this.ressource);
	}
}