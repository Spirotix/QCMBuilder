package src.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import src.Controleur;
import src.ihm.question.*;

public class PanelAfficherQuestion extends JPanel implements ActionListener
{
	private Controleur 			ctrl				;
	private ArrayList<JPanel> 	lstPanel			;
	private String				ressource, notion	;
	private ArrayList<JButton> 	btnModif, btnSup	;
	private ArrayList<JLabel>	lstLabel 			;

	public PanelAfficherQuestion (Controleur ctrl)
	{
		this.ctrl		= ctrl	;
		this.ressource	= ""	;
		this.notion		= ""	;
		
		this.lstPanel = new ArrayList<JPanel> ();
		this.btnModif = new ArrayList<JButton>();
		this.btnSup	  = new ArrayList<JButton>();
		this.lstLabel = new ArrayList<JLabel> ();

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.setVisible(true);
	}

	public void Update(String ressource,String notion)
	{
		this.ressource  = ressource	;
		this.notion		= notion 	;
		JPanel temp,temp2, tempBas;
		System.out.println("test");

		this.lstPanel = new ArrayList<JPanel> ();
		this.lstLabel = new ArrayList<JLabel> ();
		this.btnSup   = new ArrayList<JButton>();
		this.btnModif = new ArrayList<JButton>();
		this.removeAll();

		for (int i=0; i<this.ctrl.getQuestions(this.ressource, this.notion).size(); i++)
		{
			temp  = new JPanel ();
			temp2 = new JPanel ();
			temp.setLayout(new BorderLayout());
			this.lstLabel.add(new JLabel(this.ctrl.getQuestions(this.ressource, this.notion).get(i)));
			temp.add(this.lstLabel.get(i),BorderLayout.CENTER);

			tempBas = new JPanel ();
			this.btnModif.add (new JButton("Modifier")  );
			this.btnModif.get (i).addActionListener(this);
			tempBas.add(this.btnModif.get(i));

			
			this.btnSup.add(new JButton("Supprimer"));
			this.btnSup.get(i).addActionListener(this);
			tempBas.add(this.btnSup.get(i));

			temp .add(tempBas,BorderLayout.SOUTH );
			temp2.add(temp						 );

			this.lstPanel.add(temp2);
			this.add(this.lstPanel.get(i));
		}
		//JScrollPane scrollPane = new JScrollPane(this, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


		this.revalidate	();
		this.repaint	();

	}

	public void actionPerformed(ActionEvent e) 
	{
		for (int i=0; i<this.lstPanel.size(); i++)
		{
			if (e.getSource().equals(this.btnSup.get(i)))
			{	
				this.ctrl.supprimerQuestion(this.lstLabel.get(i).getText(), this.ressource, this.notion);
				this.Update(this.ressource, this.notion);
				return;
			}

			if (e.getSource().equals(this.btnModif.get(i)))
			{
				FrameCreerQuestion fr	 = new FrameCreerQuestion(this.ctrl		);
				PanelCreerQuestion panel = new PanelCreerQuestion(this.ctrl, fr	); 

				panel.setValeur("10","7,5","Initiation au developpement","TP1","1" );
				return;
			}
		}
	}
}