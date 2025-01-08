package src.ihm.question;

import java.awt.*					;
import java.awt.event.*				;
import javax.swing.*				;
import src.Controleur				;
import src.ihm.*					;

public class PanelModifierQuestion extends JPanel implements ActionListener
{
	private PanelAfficherQuestion paq                                                             ;
	private Controleur            ctrl                                                            ;
	private JTextField            textIntitule, nbPoints, tpsReponses, textExplication            ;
	private ButtonGroup           btnGroupImg                                                     ;
	private JButton               btnModifier                                                     ;
	private JRadioButton          btnTF, btnF, btnM, btnD	                                      ;
	private JLabel                msgErrNbPts, msgErrTpsRep, msgErrNiv, msgErrTextQue, msgErrExpl ;
	private FrameModifierQuestion fr                                                              ;

	// Ressources finale
	private int    tempsQuestion      ;
	private double nbPointQuestion    ;
	private int    difficulteQuestion ;
	private String explicationQuestion;
	private String textQuestion       ;
	private String ressource          ;
	private String notion             ;
	private String textInitiale       ;
	private String typeQuestion       ;

	public PanelModifierQuestion(Controleur ctrl, FrameModifierQuestion fr, String textInitiale, String notion, String ressource, PanelAfficherQuestion paq) 
	{
		this.ctrl = ctrl;
		this.fr   = fr  ;
		this.paq  = paq ;
		FileHandler.supprimerFichiersTemp();

		this.setLayout    (new GridBagLayout());
		this.setBackground(Color.LIGHT_GRAY);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5) ;
		gbc.fill   = GridBagConstraints.HORIZONTAL ;

		// Initialisation
		this.ressource       = ressource;
		this.notion          = notion   ;
		this.textInitiale    = textInitiale;

		this.typeQuestion    = this.ctrl.getTypeQuestion(ressource, notion, textInitiale);

		this.difficulteQuestion = this.ctrl.getDifficulteQuestion(ressource, notion, textInitiale);

		this.textIntitule    = new TextFieldPerso("ex : Quelle est la couleur du cheval blanc d'Henri IV ?");
		this.nbPoints        = new TextFieldPerso("ex : 4.5");
		this.tpsReponses     = new TextFieldPerso("ex : 1:30");
		this.textExplication = new TextFieldPerso("ex : Et oui le cheval blanc est effet gris");

		this.textIntitule.setText(textInitiale);
		this.nbPoints.setText(this.ctrl.getNbPointQuestion(ressource, notion, textInitiale) + "");

		int temps   = this.ctrl.getTempsQuestion(ressource, notion, textInitiale);
		int minute  = temps / 60;
		int seconde = temps % 60;
		this.tpsReponses.setText(minute + ":" + seconde);

		this.textExplication.setText(this.ctrl.getExplicationQuestion(ressource, notion, textInitiale));

		this.btnGroupImg = new ButtonGroup ();
		this.btnTF       = new JRadioButton(new ImageIcon("../img/TF2.PNG"));
		this.btnF        = new JRadioButton(new ImageIcon("../img/F2.PNG" ));
		this.btnM        = new JRadioButton(new ImageIcon("../img/M2.PNG" ));
		this.btnD        = new JRadioButton(new ImageIcon("../img/D2.PNG" ));


		updateDifficultyIcons();

		this.btnTF.setOpaque(false);
		this.btnF .setOpaque(false);
		this.btnM .setOpaque(false);
		this.btnD .setOpaque(false); 

		this.btnGroupImg.add(this.btnTF);
		this.btnGroupImg.add(this.btnF );
		this.btnGroupImg.add(this.btnM );
		this.btnGroupImg.add(this.btnD );

		switch ( this.difficulteQuestion )
		{
			case 1 : this.btnTF.setSelected(true);break;
			case 2 : this.btnF .setSelected(true);break;
			case 3 : this.btnM .setSelected(true);break;
			case 4 : this.btnD .setSelected(true);break;
		}

		this.msgErrNbPts   = new JLabel("");
		this.msgErrTpsRep  = new JLabel("");
		this.msgErrNiv     = new JLabel("");
		this.msgErrTextQue = new JLabel("");
		this.msgErrExpl    = new JLabel("");

		this.btnModifier = new JButton("Modifier");

		this.btnModifier.setOpaque       (true);
		this.btnModifier.setBackground   (new Color(0, 127, 255)); // Steel blue background
		this.btnModifier.setForeground   (Color.WHITE); // White text
		this.btnModifier.setFont         (new Font("Arial", Font.BOLD, 14));
		this.btnModifier.setBorder       (null);
		this.btnModifier.setPreferredSize(new Dimension(100, 30));
		this.btnModifier.setFocusPainted (false);

		this.ajouterPassageSouris(btnModifier);

		this.updateDifficultyIcons();

		// ActionListener / itemListener
		this.btnTF      .addActionListener(this);
		this.btnF       .addActionListener(this);
		this.btnM       .addActionListener(this);
		this.btnD       .addActionListener(this);
		this.btnModifier.addActionListener(this);

		// Layout
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(new JLabel("text question"), gbc);
		gbc.gridx = 1;
		this.add(this.textIntitule, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		this.add(new JLabel("Nombre de points"), gbc);
		gbc.gridx = 1;
		this.add(this.nbPoints, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		this.add(new JLabel("Temps de réponse (m:s)"), gbc);
		gbc.gridx = 1;
		this.add(this.tpsReponses, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		this.add(new JLabel("Explication"), gbc);
		gbc.gridx = 1;
		this.add(this.textExplication, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		this.add(new JLabel("Niveau"), gbc);
		gbc.gridx = 1;
		this.add(createDifficultyPanel(), gbc);


		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 2;
		this.add(createErrorPanel(this.msgErrTextQue), gbc);

		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 2;
		this.add(createErrorPanel(this.msgErrNbPts), gbc);

		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.gridwidth = 2;
		this.add(createErrorPanel(this.msgErrTpsRep), gbc);

		gbc.gridx = 0;
		gbc.gridy = 8;
		gbc.gridwidth = 2;
		this.add(createErrorPanel(this.msgErrExpl), gbc);

		gbc.gridx = 0;
		gbc.gridy = 9;
		gbc.gridwidth = 2;
		this.add(createErrorPanel(this.msgErrNiv), gbc);

		gbc.gridx = 0;
		gbc.gridy = 10;
		gbc.gridwidth = 2;
		this.add(this.btnModifier, gbc);

		this.setVisible(true);
	}

	private JPanel createErrorPanel(JLabel... labels) 
	{
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.setBackground      (Color.LIGHT_GRAY);

		for (JLabel label : labels) 
			panel.add(label);

		return panel;
	}

	private JPanel createDifficultyPanel() 
	{
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.setBackground      (Color.LIGHT_GRAY);

		panel.add (this.btnTF);
		panel.add (this.btnF );
		panel.add (this.btnM );
		panel.add (this.btnD );

		return panel;
	}


	public void actionPerformed(ActionEvent e) 
	{
		boolean peutModifier = true;

		if (e.getSource().equals(this.btnModifier))
		{
			if (this.textIntitule.getText().equals(""))
			{
				this.msgErrTextQue.setForeground(Color.RED);
				this.msgErrTextQue.setText      ("Texte Question : Vous devez rentrer un texte pour la question");
				peutModifier = false;
			} 
			else
			{
				this.textQuestion = this.textIntitule.getText();
				this.msgErrTextQue.setText("");
			}

			if (this.nbPoints.getText().equals("")) 
			{
				this.msgErrNbPts.setForeground(Color.RED);
				this.msgErrNbPts.setText      ("Nombre de point : Vous devez rentrer une nombre de points"	);
				peutModifier = false;
			} 
			else 
			{
				try 
				{
					this.nbPointQuestion = Double.valueOf(this.nbPoints.getText());
					this.msgErrNbPts.setText("");
				} 
				catch (Exception ex) 
				{
					this.msgErrNbPts.setForeground(Color.RED);
					this.msgErrNbPts.setText      ("Nombre de point : Vous devez rentrer un nombre à virgule pour le nombre de points");
					peutModifier = false;
				}
			}
			if (this.tpsReponses.getText().equals("")) 
			{
				this.msgErrTpsRep.setForeground(Color.RED);
				this.msgErrTpsRep.setText      ("Temps question : Vous devez rentrer un temps de réponse");
				peutModifier = false;
			} 
			else 
			{
				try 
				{
					int minute, seconde;

					minute  = Integer.parseInt(this.tpsReponses.getText().substring (0,this.tpsReponses.getText().indexOf(':')));
					seconde = Integer.parseInt(this.tpsReponses.getText().substring (  this.tpsReponses.getText().indexOf(':')+1));

					this.tempsQuestion = minute * 60 + seconde;
					this.msgErrTpsRep.setText("");
				} 
				catch (Exception ex) 
				{
					this.msgErrTpsRep.setForeground(Color.RED);
					this.msgErrTpsRep.setText      ("Temps question : Vous devez respecter le format demandé");
					peutModifier = false;
				}
			}
			if (this.textExplication.getText().equals("")) 
			{
				this.msgErrExpl.setForeground(Color.RED);
				this.msgErrExpl.setText      ("Explication question : Vous devez rentrer une explication");
				peutModifier = false;
			} 
			else
			{
				this.explicationQuestion = this.textExplication.getText();
				this.msgErrExpl.setText("");
			}

			if (!(this.btnTF.isSelected() || this.btnF.isSelected() || this.btnM.isSelected() || this.btnD.isSelected())) 
			{
				this.msgErrNiv.setForeground(Color.RED);
				this.msgErrNiv.setText      ("Difficulte : Vous devez choisir un niveau de difficulté");
				peutModifier = false;
			} 
			else 
				this.msgErrNiv.setText("");

			
			if (peutModifier)
			{

				
				System.out.println(typeQuestion + " " + ressource.substring(0, ressource.indexOf("_")) +  " " + notion + " " +
				textQuestion + " " + explicationQuestion + " " + tempsQuestion + " " + nbPointQuestion + " " + difficulteQuestion);

				this.ctrl.modifierQuestion(typeQuestion, ressource.substring(0, ressource.indexOf("_")), notion, textQuestion, explicationQuestion, tempsQuestion, nbPointQuestion, difficulteQuestion, textInitiale);

				this.paq.Update(ressource, notion);
				this.fr.dispose();
			}

		}

		updateDifficultyIcons();
	}

	private void updateDifficultyIcons() 
	{
		if (this.btnTF.isSelected())
		{
			this.btnTF.setIcon(new ImageIcon("../img/TF1.PNG"));
			this.btnF.setIcon(new ImageIcon("../img/F2.PNG"));
			this.btnM.setIcon(new ImageIcon("../img/M2.PNG"));
			this.btnD.setIcon(new ImageIcon("../img/D2.PNG"));
			this.msgErrNiv.setText("");
		}

		else if (this.btnF.isSelected())
		{
			this.btnTF.setIcon(new ImageIcon("../img/TF2.PNG"));
			this.btnF.setIcon(new ImageIcon("../img/F1.PNG"));
			this.btnM.setIcon(new ImageIcon("../img/M2.PNG"));
			this.btnD.setIcon(new ImageIcon("../img/D2.PNG"));
			this.msgErrNiv.setText("");
		}
		else if (this.btnM.isSelected())
		{
			this.btnTF.setIcon(new ImageIcon("../img/TF2.PNG"));
			this.btnF.setIcon(new ImageIcon("../img/F2.PNG"));
			this.btnM.setIcon(new ImageIcon("../img/M1.PNG"));
			this.btnD.setIcon(new ImageIcon("../img/D2.PNG"));
			this.msgErrNiv.setText("");
		}
		else if (this.btnD.isSelected())
		{
			this.btnTF.setIcon(new ImageIcon("../img/TF2.PNG"));
			this.btnF.setIcon(new ImageIcon("../img/F2.PNG"));
			this.btnM.setIcon(new ImageIcon("../img/M2.PNG"));
			this.btnD.setIcon(new ImageIcon("../img/D1.PNG"));
			this.msgErrNiv.setText("");
		}

		if 		(this.btnTF.isSelected()) 
			this.difficulteQuestion = 1;
		else if (this.btnF .isSelected()) 
			this.difficulteQuestion = 2;
		else if (this.btnM .isSelected()) 
			this.difficulteQuestion = 3;
	 	else if (this.btnD .isSelected()) 
			this.difficulteQuestion = 4;
		
	}

	public void setValeur(String nbPoint, String tempQuestion, String ressource, String notion, String difficulte)
	{
		this.nbPoints.setText(nbPoint	 );
		this.tpsReponses.setText(tempQuestion);

		switch (Integer.parseInt(difficulte))
		{
			case 1 : this.btnTF	.setSelected(true);break;
			case 2 : this.btnF	.setSelected(true);break;
			case 3 : this.btnM	.setSelected(true);break;
			case 4 : this.btnD	.setSelected(true);break;
		}

	}

	private void ajouterPassageSouris(JButton button)
	{
		button.setOpaque(true);
		button.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				button.setBackground(button.getBackground().darker());
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				button.setBackground(button.getBackground().brighter());
			}
		});
	}
}
