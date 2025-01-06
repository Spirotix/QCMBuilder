package src.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import src.ihm.question.*;

public class PanelReponse extends JPanel implements ActionListener
{
	private PanelCreerQCMRepUnique	panelQ				;
	private JButton					corbeille			;
	private JTextField				contenu				;
	private JCheckBox 				validationM 		;
	private JCheckBox 				validation		;
	private JRadioButton 			validationU 		;
	private String 					type 				;
	private int 					indice				;

	public PanelReponse (PanelCreerQCMRepUnique panelQ, String type, int indice)
	{
		this.panelQ = panelQ ;
		this.type 	= type 	 ;
		this.indice = indice ;

		this.setLayout(new GridLayout(1,3));

		//Initialisation

		this.corbeille 	= new JButton("Supprimer");
		this.corbeille.setPreferredSize(new Dimension(120, 50)); // Increase the width to fit the text
		this.corbeille.setBackground(new Color(255, 102, 102)); // Set a less bright red color
		this.corbeille.setForeground(Color.WHITE); // Set the text color to white

		this.contenu 	= new TextFieldPerso ("contenu");
		this.contenu.setPreferredSize(new Dimension(100, 50));

		this.validation = new JCheckBox();
		this.validation.setPreferredSize(new Dimension(30, 50));

		//Insertion
		JPanel panelGauche = new JPanel(new GridBagLayout());
		panelGauche.add(this.corbeille);

		JPanel panelMilieu = new JPanel(new GridBagLayout());
		panelMilieu.add(this.contenu);

		JPanel panelDroite = new JPanel(new GridBagLayout());
		panelDroite.add(this.validation);

		this.add(panelGauche);
		this.add(panelMilieu);
		this.add(panelDroite);

		//ActionListener / itemListener
		this.corbeille  .addActionListener(this);
		this.contenu	.addActionListener(this);
		this.validation .addActionListener(this);;

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(this.corbeille))
		{
			this.panelQ.supprimer(this);
		}

		if (e.getSource().equals(this.validation))
		{
			if (this.validationU.isSelected())
			{
				this.panelQ     .toutDecocher(    );
				this.validationU.setSelected (true);
			}
		}
	}

	public void decocher()
	{
		if ( this.type.equals("Unique") )
			this.validationU.setSelected(false);
		else
			this.validationM.setSelected(false);
	}


	public boolean  getEstBonneReponse ()
	{
		if ( this.type.equals("Unique") )
			return this.validationU.isSelected();
		else
			return this.validationM.isSelected();
	}


	public String getString(){return this.contenu.getText();}
}