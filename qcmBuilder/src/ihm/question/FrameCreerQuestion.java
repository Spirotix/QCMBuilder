package src.ihm.question;

import java.awt.*					;
import java.awt.event.*				;
import java.io.File				;
import java.io.IOException					;
import javax.swing.*		;
import javax.swing.border.EmptyBorder;

import src.Controleur	;
import src.ihm.*					;

public class FrameCreerQuestion extends JFrame implements ActionListener
{
	private Controleur 			ctrl	;
	private PanelCreerQuestion 	panelQ	;

	private JMenuItem 	 retourMenu		;
	private JMenuItem 	 retour			;
	private JMenuItem 	 importerImage	;
	private FileHandler fileHandler		;

	public FrameCreerQuestion (Controleur ctrl)
	{
		this.ctrl = ctrl;
		this.fileHandler = new FileHandler("fichier_question");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setTitle   				("Creation de question"	);
		this.setSize    				( 730,500  				);
		this.setLocationRelativeTo		(null					);
		this.setDefaultCloseOperation	(JFrame.EXIT_ON_CLOSE	);
		this.setResizable				(false					);
		this.setBackground				(Color.LIGHT_GRAY		);

		JPanel titrePanel = new JPanel();
		titrePanel.setLayout(new BorderLayout());
		titrePanel.setBorder(new EmptyBorder(10,10,10,10));
		JLabel titreLabel = new JLabel("Création de question", JLabel.CENTER);
		titreLabel.setFont(new Font("Arial", Font.BOLD, 20));
		titrePanel.add(titreLabel, BorderLayout.CENTER);
		titrePanel.setBackground(Color.lightGray);
		this.add(titrePanel, BorderLayout.NORTH);

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
			try 
			{
				File selectedFile = fileHandler.chooseFile();
				fileHandler.handleFile(selectedFile);
			} 
			catch (IOException ex) 
			{
				System.out.println("Erreur lors du traitement du fichier : " + ex.getMessage());
			}
		}
	}

	public PanelCreerQuestion getPanelCreerQuestion () {return this.panelQ;}
}