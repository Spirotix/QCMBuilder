//package src.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
//import src.Controleur;

public class PanelRessource extends JPanel 
{
	//private Controleur ctrl
	private TestCreerQuestion 	ctrl	;
	private PanelCreerRessource panelC	;
	private ArrayList<JLabel> 	lstLabel;

	public PanelRessource (TestCreerQuestion ctrl, PanelCreerRessource panelC)
	{
		this.ctrl=ctrl		;
		this.panelC=panelC	;

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.lstLabel = new ArrayList<JLabel>();


		for (String s : this.ctrl.getChoixRessources())
			this.lstLabel.add(new JLabel(s));
		
		
		for (JLabel j : lstLabel)
		{
			j.addMouseListener(new MouseAdapter() 
			{
				@Override
				public void mouseClicked(MouseEvent e) 
				{
					//JOptionPane.showMessageDialog(frame, "Label cliqué !");
					panelC.getPanelNotion().Update(j.getText());
				}
			});
			this.add(j);
		}


		this.setVisible(true);

	}

	public void Update()
	{
		this.lstLabel = new ArrayList<JLabel>();
		this.removeAll();

		for (String s : this.ctrl.getChoixRessources())
			this.lstLabel.add(new JLabel(s));

		for (JLabel j : lstLabel)
		{
			j.addMouseListener(new MouseAdapter() 
			{
				@Override
				public void mouseClicked(MouseEvent e) 
				{
					//JOptionPane.showMessageDialog(frame, "Label cliqué !");
					panelC.getPanelNotion().Update(j.getText());
				}
			});
			this.add(j);
		}
		this.revalidate();

	}

	public void ajouter(String nomRessource)
	{
		this.ctrl.ajouterRessource(nomRessource);
		this.Update();
	}
}