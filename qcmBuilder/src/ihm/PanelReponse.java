package src.ihm;

import java.awt.*					;
import java.awt.event.*				;
import javax.swing.*				;
import javax.imageio.ImageIO		;
import java.io.File					;
import java.io.IOException			;
import src.ihm.question.*			;

public class PanelReponse extends JPanel implements ActionListener
{
	private PanelCreerQCMRepUnique	panelQ				;
	private JButton					corbeille, importer	;
	private JLabel 					imageImporter		;
	private JTextField				contenu				;
	private JCheckBox				validation 			;
	private String 					type 				;
	private FileHandler 			fileHandler			;
	private File 					fileChoisi 			;
	private int 					indice				;
	private JPanel 					panelImage 			;

	public PanelReponse (PanelCreerQCMRepUnique panelQ, String type, int indice)
	{
		this.panelQ = panelQ ;
		this.type 	= type 	 ;
		this.indice = indice ;

		this.imageImporter 	= new JLabel	  (								);
		this.fileHandler	= new FileHandler ("fichier_reponse"+this.indice);

		this.setLayout(new GridLayout(1,3));

		//Initialisation
		this.corbeille 	= new JButton(new ImageIcon("../img/poubelle.PNG"	));
		this.importer	= new JButton(new ImageIcon("../img/inserer.PNG"	));

		this.contenu 	= new TextFieldPerso ("contenu");
		this.validation = new JCheckBox  ();

		//Insertion
		JPanel panelGauche = new JPanel ();
		panelGauche.add(this.corbeille);

		this.add (panelGauche);
		this.add (this.contenu	 );

		JPanel panelDroite	 	= new JPanel (new BorderLayout());
		JPanel panelDroiteHaut 	= new JPanel ();
		this.panelImage	= new JPanel ();
		this.panelImage.setPreferredSize(new Dimension(75, 75));
		
		panelDroiteHaut	.add (this.validation						);
		panelDroiteHaut	.add (this.importer							);
		panelDroite		.add (panelDroiteHaut, BorderLayout.NORTH	);
		this.panelImage .add (this.imageImporter					);
		panelDroite		.add (this.panelImage, BorderLayout.CENTER	);

		this.add(panelDroite);

		//ActionListener / itemListener
		this.corbeille  .addActionListener(this);
		this.contenu	.addActionListener(this);
		this.validation .addActionListener(this);
		this.importer	.addActionListener(this);

		this.setVisible(true);

	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(this.corbeille))
		{
			this.panelQ.supprimer(this);
		}
		
		this.updateImage();

		if (e.getSource().equals(this.validation))
		{
			if (this.type.equals("Unique"))
			{
				if (this.validation.isSelected())
				{
					this.panelQ		.toutDecocher(		);
					this.validation	.setSelected (true	);
				}
			}
		}

		if (e.getSource().equals(this.importer)) 
		{
			try 
			{
				this.fileChoisi = fileHandler.chooseFile();
				fileHandler.handleFile(this.fileChoisi);
				this.updateImage();
			} 
			catch (IOException ex) 
			{
				System.out.println("Erreur lors du traitement du fichier : " + ex.getMessage());
			}
			this.repaint();
		}
	}

	public void updateImage()
	{
		if (this.fileChoisi==null)
			return ;
		try 
		{
			Image image 		 = ImageIO.read(new File("../data/questions_NOUVEAU/temp/fichier_reponse"+this.indice+"."+this.fileHandler.getExtension(this.fileChoisi.getName())));
			Image imageRetaillee = image.getScaledInstance( this.panelImage.getHeight(), this.panelImage.getHeight(), Image.SCALE_AREA_AVERAGING);

			this.imageImporter.setIcon(new ImageIcon(imageRetaillee));
			System.out.println(this.imageImporter.getIcon());
		} 
		catch (IOException ex) 
		{
			System.out.println("Erreur lors du traitement du fichier : " + ex.getMessage());
		}
		this.repaint();
	}

	public void decocher()
	{
		this.validation.setSelected(false);
	}


	public boolean  getEstBonneReponse ( )	{return this.validation.isSelected();}
	

	public String getString(){return this.contenu.getText();}
}