package src.ihm;

import java.awt.*					;
import java.awt.event.*				;
import java.io.File				;
import java.io.IOException		;
import javax.imageio.ImageIO					;
import javax.swing.*			;
import src.ihm.question.*			;

public class PanelReponse extends JPanel implements ActionListener
{
	private PanelCreerQCMRepUnique	panelQ				;
	private JButton					corbeille 			;
	private JTextField				contenu				;
	private JCheckBox 				validationM 		;
	private JRadioButton 			validationU 		;
	private String 					type 				;

	public PanelReponse (PanelCreerQCMRepUnique panelQ, String type, int indice)
	{
		this.panelQ = panelQ ;
		this.type 	= type 	 ;

		this.setLayout(new GridLayout(1,3));

		//Initialisation

		ImageIcon icon = null;
		try {
			Image image = ImageIO.read(new File("../img/poubelle.PNG"));
			Image scaledImage = image.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
			icon = new ImageIcon(scaledImage);
		} catch (IOException ex) {
			System.out.println("Erreur lors du chargement de l'image : " + ex.getMessage());
		}

		this.corbeille 	= new JButton(icon);

		this.contenu 	= new TextFieldPerso ("contenu");
		this.contenu.setPreferredSize(new Dimension(100, 25));
		
		
		if ( this.type.equals("Unique") )
			this.validationU = new JRadioButton();
		else
			this.validationM = new JCheckBox();

		//Insertion
		JPanel panelGauche = new JPanel ();
		panelGauche.add(this.corbeille);

		this.add (panelGauche);

		JPanel panelCentre = new JPanel ();
		panelCentre.add(this.contenu);
		this.add (panelCentre);

		JPanel panelDroite	 	= new JPanel ();
		
		if ( this.type.equals("Unique") )
			panelDroite.add ( this.validationU );
		else
			panelDroite.add ( this.validationM );



		this.add(panelDroite);

		//ActionListener / itemListener
		this.corbeille  .addActionListener(this);
		this.contenu	.addActionListener(this);

		if ( this.type.equals("Unique") )
			this.validationU.addActionListener(this);
		else
			this.validationM.addActionListener(this);

		this.setVisible(true);

	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(this.corbeille))
		{
			this.panelQ.supprimer(this);
		}

		if (e.getSource().equals(this.validationU))
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