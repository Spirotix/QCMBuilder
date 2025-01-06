package src.ihm.question;

import java.awt.*					;
import java.awt.event.*				;
import java.util.*					;
import javax.swing.*				;
import src.Controleur				;
import src.ihm.*					;

public class PanelModifierQuestion extends JPanel implements ActionListener
{
	private Controleur            ctrl                                                                      ;
	private JTextField            nbPoints, tpsReponses                                                     ;
	private ButtonGroup           btnGroup, btnGroupImg                                                     ;
	private JButton               btnModifier                                                               ;
	private JRadioButton          btnTF, btnF, btnM, btnD	                                                ;
	private JLabel                msgErrNbPts, msgErrTpsRep, msgErrRess, msgErrNiv, msgErrNot, msgChoixType ;
	private FrameModifierQuestion fr                                                                        ;

	// Ressources finale
	private String textQuestion        ;
	private int    tempsQuestion       ;
	private double nbPointQuestion     ;
	private int    difficulteQuestion  ;
	private String explicationQuestion ;

	public PanelModifierQuestion(Controleur ctrl, FrameModifierQuestion fr) 
	{
		this.ctrl = ctrl;
		this.fr   = fr  ;

		FileHandler.supprimerFichiersTemp();

		this.setLayout		(new GridBagLayout());
		this.setBackground	(Color.LIGHT_GRAY	);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets 	= new Insets(5, 5, 5, 5) ;
		gbc.fill	= GridBagConstraints.HORIZONTAL ;

		// Initialisation
		this.nbPoints 	 = new TextFieldPerso("ex : 4.5");
		this.tpsReponses = new TextFieldPerso("ex : 1:30");

		this.btnGroupImg = new ButtonGroup (                                        );
		this.btnTF		 = new JRadioButton(new ImageIcon("../img/TF2.PNG"));
		this.btnF		 = new JRadioButton(new ImageIcon("../img/F2.PNG" ));
		this.btnM		 = new JRadioButton(new ImageIcon("../img/M2.PNG" ));
		this.btnD 		 = new JRadioButton(new ImageIcon("../img/D2.PNG" ));

		this.btnTF.setOpaque(false);
		this.btnF .setOpaque(false);
		this.btnM .setOpaque(false);
		this.btnD .setOpaque(false); 

		this.btnTF.setEnabled(false);
		this.btnF .setEnabled(false);
		this.btnM .setEnabled(false);
		this.btnD .setEnabled(false);

		this.btnGroupImg.add(this.btnTF);
		this.btnGroupImg.add(this.btnF );
		this.btnGroupImg.add(this.btnM );
		this.btnGroupImg.add(this.btnD );


		this.msgErrNbPts  = new JLabel("");
		this.msgErrTpsRep = new JLabel("");
		this.msgErrRess   = new JLabel("");
		this.msgErrNiv    = new JLabel("");
		this.msgErrNot    = new JLabel("");
		this.msgChoixType = new JLabel("");

		this.btnModifier = new JButton("Modifier");

		this.btnModifier.setOpaque				(true						);
		this.btnModifier.setBackground			(new Color(0, 127, 255)	); // Steel blue background
		this.btnModifier.setForeground			(Color.WHITE				); // White text
		this.btnModifier.setFont				(new Font("Arial", Font.BOLD, 14));
		this.btnModifier.setBorder				(null);
		this.btnModifier.setPreferredSize		(new Dimension(100, 30)		);
		this.btnModifier.setFocusPainted		(false						);

		this.ajouterPassageSouris(btnModifier);

		// ActionListener / itemListener
		this.nbPoints		.addActionListener	(this);
		this.tpsReponses	.addActionListener	(this);
		this.btnTF			.addActionListener	(this);
		this.btnF			.addActionListener	(this);
		this.btnM			.addActionListener	(this);
		this.btnD			.addActionListener	(this);
		this.btnModifier.addActionListener	(this);

		// Layout
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(new JLabel("Nombre de points")	, gbc);
		gbc.gridx = 1;
		this.add(this.nbPoints					, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		this.add(new JLabel("Temps de réponse (m:s)")	, gbc);
		gbc.gridx = 1;
		this.add(this.tpsReponses				, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		this.add(createErrorPanel(this.msgErrNbPts, this.msgErrTpsRep), gbc);

		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 2;
		this.add(createErrorPanel(this.msgErrRess, this.msgErrNot), gbc);

		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 1;
		this.add(new JLabel("Niveau")			, gbc);
		gbc.gridx = 1;
		this.add(createDifficultyPanel()		, gbc);

		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.gridwidth = 2;
		this.add(createErrorPanel(this.msgErrNiv), gbc);

		gbc.gridx = 0;
		gbc.gridy = 9;
		gbc.gridwidth = 2;
		this.add(createErrorPanel(this.msgChoixType), gbc);

		gbc.gridx = 0;
		gbc.gridy = 10;
		gbc.gridwidth = 2;
		this.add(this.btnModifier		, gbc);

		this.setVisible(true);
	}

	private JPanel createErrorPanel(JLabel... labels) 
	{
		JPanel panel = new JPanel	(new FlowLayout(FlowLayout.LEFT));
		panel.setBackground			(Color.LIGHT_GRAY				);

		for (JLabel label : labels) 
			panel.add(label);

		return panel;
	}

	private JPanel createDifficultyPanel() 
	{
		JPanel panel = new JPanel	(new FlowLayout(FlowLayout.LEFT));
		panel.setBackground			(Color.LIGHT_GRAY				);

		panel.add (this.btnTF);
		panel.add (this.btnF );
		panel.add (this.btnM );
		panel.add (this.btnD );

		return panel;
	}


	public void actionPerformed(ActionEvent e) 
	{
		boolean peutCreer = true;

		if (e.getSource().equals(this.btnModifier))
		{
			if (this.nbPoints.getText().equals("")) 
			{
				this.msgErrNbPts.setForeground	(Color.RED									);
				this.msgErrNbPts.setText		("Vous devez rentrer une nombre de points"	);
				peutCreer = false;
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
					this.msgErrNbPts.setForeground	(Color.RED												);
					this.msgErrNbPts.setText		("Vous devez rentrer un nombre à virgule pour le nombre de points");
					peutCreer = false;
				}
			}
			if (this.tpsReponses.getText().equals("")) 
			{
				this.msgErrTpsRep.setForeground	(Color.RED								 );
				this.msgErrTpsRep.setText		("Vous devez rentrer un temps de réponse");
				peutCreer = false;
			} 
			else 
			{
				try 
				{
					int minute, seconde;

					minute  = Integer.parseInt(this.tpsReponses.getText().substring (0,this.tpsReponses.getText().indexOf(':')));
					seconde = Integer.parseInt(this.tpsReponses.getText().substring (  this.tpsReponses.getText().indexOf(':')+1));

					this.tempsQuestion = minute*60 + seconde;
					this.msgErrTpsRep.setText("");
				} 
				catch (Exception ex) 
				{
					this.msgErrTpsRep.setForeground	(Color.RED												  );
					this.msgErrTpsRep.setText		("Vous devez respecter le format demandé");
					peutCreer = false;
				}
			}

			if (!(this.btnTF.isSelected() || this.btnF.isSelected() || this.btnM.isSelected() || this.btnD.isSelected())) 
			{
				this.msgErrNiv.setForeground(Color.RED									 );
				this.msgErrNiv.setText		("Vous devez choisir un niveau de difficulté");
				peutCreer = false;
			} 
			else 
				this.msgErrNiv.setText("");


		}

		if (this.btnTF.isSelected() )
		{
			this.btnTF.setIcon(new ImageIcon("../img/TF1.PNG"));
			this.btnF .setIcon(new ImageIcon("../img/F2.PNG" ));
			this.btnM .setIcon(new ImageIcon("../img/M2.PNG" ));
			this.btnD .setIcon(new ImageIcon("../img/D2.PNG" ));
			this.msgErrNiv.setText("");
		}

		else if (this.btnF.isSelected() )
		{
			this.btnTF.setIcon(new ImageIcon("../img/TF2.PNG"));
			this.btnF .setIcon(new ImageIcon("../img/F1.PNG" ));
			this.btnM .setIcon(new ImageIcon("../img/M2.PNG" ));
			this.btnD .setIcon(new ImageIcon("../img/D2.PNG" ));
			this.msgErrNiv.setText("");
		}
		else if (this.btnM.isSelected() )
			{
			this.btnTF.setIcon(new ImageIcon("../img/TF2.PNG"));
			this.btnF .setIcon(new ImageIcon("../img/F2.PNG" ));
			this.btnM .setIcon(new ImageIcon("../img/M1.PNG" ));
			this.btnD .setIcon(new ImageIcon("../img/D2.PNG" ));
			this.msgErrNiv.setText("");
		}
		else if (this.btnD.isSelected() )
		{
			this.btnTF.setIcon(new ImageIcon("../img/TF2.PNG"));
			this.btnF .setIcon(new ImageIcon("../img/F2.PNG" ));
			this.btnM .setIcon(new ImageIcon("../img/M2.PNG" ));
			this.btnD .setIcon(new ImageIcon("../img/D1.PNG" ));
			this.msgErrNiv.setText("");
		}

		updateDifficultyIcons();
	}

	private void updateDifficultyIcons() 
	{
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


	public boolean modifiQuestion(String explication, String intituleQuestion, ArrayList<TypeReponse> reponses)
	{
		this.textQuestion 		 = intituleQuestion	;
		this.explicationQuestion = explication		;

		return false;
		//return this.ctrl.creerQuestion(this.typeQuestion, this.ressourceQuestion, this.notionQuestion, this.textQuestion, this.explicationQuestion, this.tempsQuestion, this.nbPointQuestion, reponses, this.difficulteQuestion);
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
