package src.ihm.question;

import java.awt.*					;
import java.awt.event.*				;
import java.util.*					;
import javax.swing.*				;
import javax.swing.border.LineBorder;
import src.Controleur				;
import src.ihm.*					;

public class PanelCreerQuestion extends JPanel implements ActionListener, ItemListener 
{
	private Controleur 			ctrl																		;
	private JTextField 			nbPoints, tpsReponses														;
	private Choice 				choixRessource, choixNotion													;
	private ButtonGroup 		btnGroup, btnGroupImg														;
	private JRadioButton 		btnChoixUnique, btnChoixMult, btnAsso, btnElim								;
	private JButton 			btnCreer																	;
	private JRadioButton 		btnTF, btnF, btnM, btnD														;
	private JLabel 				msgErrNbPts, msgErrTpsRep, msgErrRess, msgErrNiv, msgErrNot, msgChoixType	;
	private FrameCreerQuestion 	fr																			;

	// Ressources finale
	private String 	typeQuestion		;
	private String 	ressourceQuestion	;
	private String 	notionQuestion		;
	private String 	textQuestion		;
	private int 	tempsQuestion		;
	private double 	nbPointQuestion		;
	private int 	difficulteQuestion	;
	private String	explicationQuestion	;

	public PanelCreerQuestion(Controleur ctrl, FrameCreerQuestion fr) 
	{
		this.ctrl = ctrl;
		this.fr   = fr	;

		FileHandler.supprimerFichiersTemp();

		this.setLayout		(new GridBagLayout());
		this.setBackground	(Color.LIGHT_GRAY	);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets 	= new Insets(5, 5, 5, 5)			 ;
		gbc.fill	= GridBagConstraints.HORIZONTAL		 ;

		// Initialisation
		this.nbPoints 	 = new TextFieldPerso("ex : 4.5");
		this.tpsReponses = new TextFieldPerso("ex : 1:30");

		this.choixRessource = new Choice(	);
		this.choixRessource.add			(" ");

		for (String s : this.ctrl.getChoixRessources())
			this.choixRessource.add(s);

		this.choixNotion = new Choice	(		);
		this.choixNotion.add			(" "  	);
		this.choixNotion.setEnabled		(false	);

		this.btnGroupImg = new ButtonGroup (							   );
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

		this.btnChoixUnique = new JRadioButton("Choix Unique"	);
		this.btnChoixMult 	= new JRadioButton("Choix Multiple" );
		this.btnAsso 		= new JRadioButton("Associatif"		);
		this.btnElim 		= new JRadioButton("Elimination"	);

		this.btnChoixUnique	.setOpaque(false);
		this.btnChoixMult	.setOpaque(false);
		this.btnAsso		.setOpaque(false);
		this.btnElim		.setOpaque(false);

		this.msgErrNbPts  = new JLabel("");
		this.msgErrTpsRep = new JLabel("");
		this.msgErrRess   = new JLabel("");
		this.msgErrNiv    = new JLabel("");
		this.msgErrNot    = new JLabel("");
		this.msgChoixType = new JLabel("");

		this.btnGroup = new ButtonGroup(		);
		this.btnGroup.add (this.btnChoixUnique	);
		this.btnGroup.add (this.btnChoixMult	);
		this.btnGroup.add (this.btnAsso			);
		this.btnGroup.add (this.btnElim			);

		this.btnCreer = new JButton("Créer");

		this.btnCreer.setOpaque				(true						);
		this.btnCreer.setBackground			(new Color(0, 127, 255)	); // Steel blue background
		this.btnCreer.setForeground			(Color.WHITE				); // White text
		this.btnCreer.setFont				(new Font("Arial", Font.BOLD, 14));
		this.btnCreer.setBorder				(null);
		this.btnCreer.setPreferredSize		(new Dimension(100, 30)		);
		this.btnCreer.setFocusPainted		(false						);

		this.ajouterPassageSouris(btnCreer);


		// ActionListener / itemListener
		this.nbPoints		.addActionListener	(this);
		this.tpsReponses	.addActionListener	(this);
		this.btnTF			.addActionListener	(this);
		this.btnF			.addActionListener	(this);
		this.btnM			.addActionListener	(this);
		this.btnD			.addActionListener	(this);
		this.btnChoixUnique	.addActionListener	(this);
		this.btnChoixMult	.addActionListener	(this);
		this.btnAsso		.addActionListener	(this);
		this.btnElim		.addActionListener	(this);
		this.choixRessource	.addItemListener	(this);
		this.choixNotion	.addItemListener	(this);
		this.btnCreer		.addActionListener	(this);

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
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		this.add(new JLabel("Ressource")		, gbc);
		gbc.gridx = 1;
		this.add(this.choixRessource			, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		this.add(new JLabel("Notion")			, gbc);
		gbc.gridx = 1;
		this.add(this.choixNotion				, gbc);

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
		gbc.gridy = 8;
		gbc.gridwidth = 1;
		this.add(new JLabel("Type de question")	, gbc);
		gbc.gridx = 1;
		this.add(createTypePanel()				, gbc);

		gbc.gridx = 0;
		gbc.gridy = 9;
		gbc.gridwidth = 2;
		this.add(createErrorPanel(this.msgChoixType), gbc);

		gbc.gridx = 0;
		gbc.gridy = 10;
		gbc.gridwidth = 2;
		this.add(this.btnCreer					, gbc);

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

	private JPanel createTypePanel() 
	{
		JPanel panel = new JPanel	(new FlowLayout(FlowLayout.LEFT));
		panel.setBackground			(Color.LIGHT_GRAY				);

		panel.add (this.btnChoixUnique	);
		panel.add (this.btnChoixMult	);
		panel.add (this.btnAsso			);
		panel.add (this.btnElim			);

		return panel;
	}

	public void actionPerformed(ActionEvent e) 
	{
		boolean peutCreer = true;

		if (e.getSource().equals(this.btnCreer)) 
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
			if (this.choixRessource.getSelectedItem().equals(" ")) 
			{
				this.msgErrRess.setForeground	(Color.RED						   );
				this.msgErrRess.setText			("Vous devez choisir une ressource");
				peutCreer = false;
			} 
			else 
				this.msgErrRess.setText("");

			if (this.choixNotion.getSelectedItem().equals(" ")) 
			{
				this.msgErrNot.setForeground(Color.RED						);
				this.msgErrNot.setText		("Vous devez choisir une notion");
				peutCreer = false;
			} 
			else 
				this.msgErrNot.setText("");

			if (!(this.btnChoixUnique.isSelected() || this.btnChoixMult.isSelected() || this.btnAsso.isSelected() || this.btnElim.isSelected())) 
			{
				this.msgChoixType.setForeground	(Color.RED					 );
				this.msgChoixType.setText		("Vous devez choisir un type");
				peutCreer = false;
			} 
			else 
				this.msgChoixType.setText("");

			if (!(this.btnTF.isSelected() || this.btnF.isSelected() || this.btnM.isSelected() || this.btnD.isSelected())) 
			{
				this.msgErrNiv.setForeground(Color.RED									 );
				this.msgErrNiv.setText		("Vous devez choisir un niveau de difficulté");
				peutCreer = false;
			} 
			else 
				this.msgErrNiv.setText("");

			if (peutCreer) 
			{
				if (this.btnChoixMult.isSelected())
					new FrameCreerQCMRepUnique(this, this.ctrl, "Mult");
					
				if (this.btnChoixUnique.isSelected())
					new FrameCreerQCMRepUnique(this, this.ctrl, "Unique");

				if (this.btnAsso.isSelected())
					new FrameCreerAssociation(this, this.ctrl);
	
				if (this.btnElim.isSelected())
					new FrameCreerElimination(this, this.ctrl);
	
				this.fr.dispose();
			}
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

	public void itemStateChanged(ItemEvent e) 
	{
		if (e.getSource().equals(this.choixRessource) && !this.choixRessource.getSelectedItem().equals(" ")) 
		{
			this.choixNotion.setEnabled	(true			);
			this.choixNotion.removeAll	(				);
			this.choixNotion.add		(" "			);

			this.ressourceQuestion = this.choixRessource.getSelectedItem();
			for (String s : this.ctrl.getChoixNotion(this.choixRessource.getSelectedItem()))
				this.choixNotion.add(s);
		}

		if (e.getSource().equals(this.choixRessource) && this.choixRessource.getSelectedItem().equals(" ")) 
		{
			this.choixNotion.removeAll	(		);
			this.choixNotion.add		(" "	);
			this.choixNotion.setEnabled	(false	);

			this.btnTF	.setEnabled(false);
			this.btnF	.setEnabled(false);
			this.btnM	.setEnabled(false);
			this.btnD	.setEnabled(false);
			this.btnTF	.setIcon(new ImageIcon("../img/TF2.PNG"));
			this.btnF	.setIcon(new ImageIcon("../img/F2.PNG" ));
			this.btnM	.setIcon(new ImageIcon("../img/M2.PNG" ));
			this.btnD	.setIcon(new ImageIcon("../img/D2.PNG" ));
		}

		if (e.getSource().equals(this.choixNotion) && !this.choixNotion.getSelectedItem().equals(" ")) 
		{
			this.notionQuestion = this.choixNotion.getSelectedItem();

			this.btnGroupImg.clearSelection();
			this.btnTF		.setEnabled(true);
			this.btnF		.setEnabled(true);
			this.btnM		.setEnabled(true);
			this.btnD		.setEnabled(true);
		}

		if (e.getSource().equals(this.choixNotion) && this.choixNotion.getSelectedItem().equals(" ")) 
		{
			this.btnTF	.setIcon(new ImageIcon("../img/TF2.PNG"));
			this.btnF	.setIcon(new ImageIcon("../img/F2.PNG" ));
			this.btnM	.setIcon(new ImageIcon("../img/M2.PNG" ));
			this.btnD	.setIcon(new ImageIcon("../img/D2.PNG" ));

			this.btnTF.setEnabled(false);
			this.btnF .setEnabled(false);
			this.btnM .setEnabled(false);
			this.btnD .setEnabled(false);
		}
	}

	public void setValeur(String nbPoint, String tempQuestion, String ressource, String notion, String difficulte)
	{
		this.nbPoints.setText(nbPoint	 );
		this.tpsReponses.setText(tempQuestion);

		this.choixRessource.select(ressource);

		this.choixNotion.setEnabled	(true			);
		this.choixNotion.removeAll	(				);
		this.choixNotion.add		(" "			);

		this.ressourceQuestion = this.choixRessource.getSelectedItem();
		for (String s : this.ctrl.getChoixNotion(this.choixRessource.getSelectedItem()))
			this.choixNotion.add(s);
		
		this.choixNotion.select(notion);

		switch (Integer.parseInt(difficulte))
		{
			case 1 : this.btnTF	.setSelected(true);break;
			case 2 : this.btnF	.setSelected(true);break;
			case 3 : this.btnM	.setSelected(true);break;
			case 4 : this.btnD	.setSelected(true);break;
		}

	}


	public boolean creerQuestion(String explication, String intituleQuestion, ArrayList<TypeReponse> reponses)
	{
		if (this.btnChoixMult.isSelected() || this.btnChoixUnique.isSelected())
			this.typeQuestion = "QCM"		 ;
		if (this.btnAsso.isSelected())
			this.typeQuestion = "Association";
		if (this.btnElim.isSelected())
			this.typeQuestion = "Elimination";

		this.textQuestion 		 = intituleQuestion	;
		this.explicationQuestion = explication		;

		return this.ctrl.creerQuestion(this.typeQuestion, this.ressourceQuestion, this.notionQuestion, this.textQuestion, this.explicationQuestion, this.tempsQuestion, this.nbPointQuestion, reponses, this.difficulteQuestion);
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
