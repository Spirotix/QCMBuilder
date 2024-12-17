package src.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import src.Controleur;

public class PanelRessource extends JPanel implements ActionListener
{
	//private Controleur ctrl
	private Controleur 			ctrl	 ;
	private PanelCreerRessource panelC	 ;
	private ArrayList<JLabel> 	lstLabel ;
	private ArrayList<JButton> 	lstSup	 ;
	private ArrayList<JPanel>	lstPanel ;

	public PanelRessource (Controleur ctrl, PanelCreerRessource panelC)
	{
		this.ctrl=ctrl		;
		this.panelC=panelC	;

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.lstLabel	= new ArrayList<JLabel> ();
		this.lstSup 	= new ArrayList<JButton>();
		this.lstPanel 	= new ArrayList<JPanel> ();

		for (int i=0; i<this.ctrl.getChoixRessources().size(); i++)
		{
			this.lstLabel.add (new JLabel(this.ctrl.getChoixRessources().get(i)));
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
					panelC.getPanelNotion().Update(temp.getText());
					Update();
				}
			});
			this.add(this.lstPanel.get(i));
		}
		this.setVisible(true);

	}

	public void Update()
	{
		this.lstLabel	= new ArrayList<JLabel> ();
		this.lstSup 	= new ArrayList<JButton>();
		this.lstPanel 	= new ArrayList<JPanel> ();

		this.removeAll();

		for (int i=0; i<this.ctrl.getChoixRessources().size(); i++)
		{
			this.lstLabel.add (new JLabel(this.ctrl.getChoixRessources().get(i)));

			if (this.panelC.getPanelNotion().getRessource().equals(this.lstLabel.get(i).getText()))
				this.lstLabel.get(i).setForeground(Color.RED);
			else 
				this.lstLabel.get(i).setForeground(Color.BLACK);

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
					panelC.getPanelNotion().Update(temp.getText());
					Update();
				}
			});
			this.add(this.lstPanel.get(i));
		}
		this.revalidate();

	}

	public void actionPerformed(ActionEvent e)
	{
		for (int i=0; i<this.lstLabel.size(); i++)
			if (e.getSource().equals(this.lstSup.get(i)))
			{
				this.ctrl.supprimerRessource(this.lstLabel.get(i).getText());
				Update();
			}
	}

	

	public void ajouter(String nomRessource)
	{
		//this.ctrl.ajouterRessource(nomRessource);
		this.Update();
	}
}