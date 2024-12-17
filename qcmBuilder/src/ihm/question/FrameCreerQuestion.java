package src.ihm.question;

import java.awt.*					;
import java.awt.event.*				;
import javax.swing.*				;
import src.ihm.*					;
import javax.imageio.ImageIO		;
import java.awt.image.BufferedImage	;
import java.io.File					;
import java.io.IOException			;
import src.Controleur				;

public class FrameCreerQuestion extends JFrame implements ActionListener
{
	private Controleur 			ctrl	;
	private PanelCreerQuestion 	panelQ	;

	private JMenuItem 	 retourMenu		;
	private JMenuItem 	 retour			;
	private JMenuItem 	 importerImage	;
	private JFileChooser jfc 			;

	public FrameCreerQuestion (Controleur ctrl)
	{
		this.ctrl = ctrl;
		this.jfc  = new JFileChooser();

		this.setTitle   				("Creation de question"	);
		this.setSize    				( 730,500  				);
		this.setLocationRelativeTo		(null					);
		this.setDefaultCloseOperation	(JFrame.EXIT_ON_CLOSE	);
		this.setResizable				(false					);
		this.setBackground				(Color.LIGHT_GRAY		);


		JMenuBar menubMaBarre = new JMenuBar(			);
		JMenu 	 menuAcceuil  = new JMenu	("Accueil"	);
		JMenu 	 menuRetour   = new JMenu	("Retour" 	);
		JMenu 	 menuImport   = new JMenu	("Importer" );

		this.retourMenu 	= new JMenuItem("Retour à l'accueil"		 );
		this.retour			= new JMenuItem("Retour à la page précédente");
		this.importerImage	= new JMenuItem("Importer image"			 );

		menuAcceuil.add(this.retourMenu		);
		menuRetour .add(this.retour			);
		menuImport .add(this.importerImage	);

		menubMaBarre.add(menuAcceuil);
		menubMaBarre.add(menuRetour );
		menubMaBarre.add(menuImport );

		this.setJMenuBar( menubMaBarre );

		this.retourMenu   .addActionListener(this);
		this.retour       .addActionListener(this);
		this.importerImage.addActionListener(this);

		this.panelQ=new PanelCreerQuestion(this.ctrl, this);

		this.add (this.panelQ);

		this.setVisible(true);

	}

	public void actionPerformed ( ActionEvent e )
	{
		if (e.getSource().equals(this.retourMenu))
		{
			new FrameMenu (this.ctrl);
			this.dispose();
		}

		if (e.getSource().equals(this.retour))
		{
			new FrameMenu (this.ctrl);
			this.dispose();
		}

		if (e.getSource().equals(this.importerImage))
		{
			this.jfc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Fichiers Image", "jpg", "jpeg", "png", "bmp", "gif"));

			int response = this.jfc.showOpenDialog(null);

			if (response == JFileChooser.APPROVE_OPTION) 
			{
				try 
				{
					File selectedFile = this.jfc.getSelectedFile();

					BufferedImage image = ImageIO.read(selectedFile);
					if (image == null) 
					{
						System.out.println("Le fichier sélectionné n'est pas une image valide.");
						return;
					}

					File targetDirectory = new File("imagesQuestion");
					if (!targetDirectory.exists()) 
						targetDirectory.mkdirs();

					File outputFile = new File(targetDirectory, selectedFile.getName());

					String formatName = getExtension(selectedFile.getName());
					if (formatName == null)
						formatName = "png"; 

					ImageIO.write(image, formatName, outputFile);
					System.out.println("Image enregistrée avec succès dans : " + outputFile.getAbsolutePath());
				} 
				catch (IOException ex) 
				{
					System.out.println("Erreur lors de la lecture ou de l'enregistrement de l'image : " + ex.getMessage());
				}
			}
			else
				System.out.println("Aucune image sélectionnée.");

		}
	}

	private static String getExtension(String fileName) 
	{
		int lastDot = fileName.lastIndexOf('.');
		if (lastDot > 0 && lastDot < fileName.length() - 1) 
			return fileName.substring(lastDot + 1).toLowerCase();
		return null;
	}
	public PanelCreerQuestion getPanelCreerQuestion () {return this.panelQ;}
}