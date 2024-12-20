package src.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import src.Controleur;

public class PanelRessource extends JPanel implements ActionListener
{
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

		JPanel tempP = new JPanel();

		for (int i=0; i<this.ctrl.getChoixRessources().size(); i++)
		{
			tempP = new JPanel();
			this.lstLabel	.add (new JLabel(this.ctrl.getChoixRessources().get(i))	);
			this.lstSup	 	.add (new JButton("Supprimer")						);
			this.lstPanel	.add (new JPanel(new BorderLayout())					);
			this.lstPanel	.get (i).add(this.lstLabel	.get(i), BorderLayout.CENTER);
			tempP			.add (this.lstSup.get(i)								);
			this.lstPanel	.get (i).add(tempP, BorderLayout.SOUTH					);
			this.lstSup	 	.get (i).addActionListener(this							);
		}
		
		int cpt = 0;
		for (JLabel label : lstLabel)
		{
			JLabel temp = label;
			label.addMouseListener(new MouseAdapter() 
			{
				@Override
				public void mouseClicked(MouseEvent e) 
				{
					//JOptionPane.showMessageDialog(frame, "Label cliqué !");
					panelC.getPanelNotion().Update(temp.getText());
					Update();
				}
			});
			ajouterPassageSouris(label);
			this.add(this.lstPanel.get(cpt++));
		}

		this.setVisible(true);
	}

	public void Update()
	{
		this.lstLabel	= new ArrayList<JLabel> ();
		this.lstSup 	= new ArrayList<JButton>();
		this.lstPanel 	= new ArrayList<JPanel> ();

		this.removeAll();

		JPanel tempP = new JPanel();

		for (int i=0; i<this.ctrl.getChoixRessources().size(); i++)
		{
			tempP = new JPanel();
			
			this.lstLabel	.add (new JLabel(this.ctrl.getChoixRessources().get(i))	);

			if (this.panelC.getPanelNotion().getRessource().equals(this.lstLabel.get(i).getText()))
			{
				this.lstLabel.get(i).setForeground(Color.RED);
				this.lstLabel.get(i).setBorder(BorderFactory.createLineBorder(Color.RED, 2));
			}
				
			else 
			{
				this.lstLabel.get(i).setForeground(Color.BLACK);
				this.lstLabel.get(i).setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));
			}
			
			this.lstSup	 	.add (new JButton("Supprimer")							);
			this.lstPanel	.add (new JPanel(new BorderLayout())					);
			this.lstPanel	.get (i).add(this.lstLabel	.get(i), BorderLayout.CENTER);
			tempP			.add (this.lstSup.get(i)								);
			this.lstPanel	.get (i).add(tempP, BorderLayout.SOUTH					);
			this.lstSup	 	.get (i).addActionListener(this							);
		}
			
		int cpt = 0;
		for (JLabel label : lstLabel)
		{
			JLabel temp = label;
			label.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mouseClicked(MouseEvent e)
				{
					// JOptionPane.showMessageDialog(frame, "Label cliqué !");
					panelC.getPanelNotion().Update(temp.getText());
					Update();
				}
			});
			ajouterPassageSouris(label);
			this.add(this.lstPanel.get(cpt++));
		}

		this.revalidate();
	}

	public void actionPerformed(ActionEvent e)
	{
		for (int i=0; i<this.lstLabel.size(); i++)
			if (e.getSource().equals(this.lstSup.get(i)))
			{
				if (this.ctrl.getChoixNotion(this.lstLabel.get(i).getText()).size()>0)
					new FrameAlerte(this.ctrl, this, this.lstLabel.get(i).getText());
				else 
					this.ctrl.supprimerRessource(this.lstLabel.get(i).getText());
				Update();
			}
	}

	public void supprimerRessource(String nomRessource)
	{
		this.ctrl.supprimerRessource(nomRessource);
		this.Update();
		panelC.getPanelNotion().Update("");
	}
	
	public void ajouter(String nomRessource)
	{
		//this.ctrl.ajouterRessource(nomRessource);
		this.Update();
	}

	private void ajouterPassageSouris(JLabel label)
	{
		label.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				label.setForeground(Color.RED);
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				label.setForeground(Color.BLACK);
			}
		});
	}
}