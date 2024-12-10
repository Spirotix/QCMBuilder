package src.ihm;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import src.Controleur;

import src.Controleur;

public class PanelCreerQuestion extends JPanel implements ActionListener, ItemListener
{
	private 	Controleur 	ctrl;
	private 	JTextField 		nbPoints, tpsReponses;
	private  	Choice 			choixRessource, choixNotion;
	private 	ButtonGroup		btnGroup, btnGroupImg;
	private 	JRadioButton	btnChoixUnique, btnChoixMult, btnAsso, btnElim;
	private		JButton 		btnCreer;	
	private 	JRadioButton 	btnTF, btnF, btnM, btnD ; 
	private 	JLabel			msgErrNbPts, msgErrTpsRep, msgErrRess,msgErrNiv,msgErrNot, msgChoixType ;
	private 	FrameCreerQuestion fr;

	//Ressources finale
	private		String		typeQuestion;
	private 	String		ressourceQuestion;
	private 	String 		notionQuestion;
	private 	String 		textQuestion;
	private 	int 		tempsQuestion;
	private 	int 		nbPointQuestion;
	private 	int 		difficulteQuestion ;
	private 	String 		explicationQuestion;

	public PanelCreerQuestion (Controleur ctrl, FrameCreerQuestion fr)
	{
		this.ctrl = ctrl;
		this.fr=fr;
		this.setLayout (new GridLayout(11,1));

		//chargement des images
		ImageIcon TresFacile = new ImageIcon("img/TF3.PNG");
		ImageIcon Facile 	 = new ImageIcon("img/F3.PNG" );
		ImageIcon Moyen 	 = new ImageIcon("img/M3.PNG" );
		ImageIcon Difficile  = new ImageIcon("img/D3.PNG" );

		//Initialisation
		this.nbPoints    = new JTextField("5");
		this.tpsReponses = new JTextField("10");

		this.choixRessource	 = new Choice();
		this.choixRessource	.add("				");

		for (String s : this.ctrl.getChoixRessources())
			this.choixRessource	.add(s);

		this.choixNotion	 = new Choice();
		this.choixNotion	.add("				");

		this.choixNotion.setEnabled(false);

		this.btnGroupImg = new ButtonGroup();

		this.btnTF 		 = new JRadioButton (TresFacile	);
		this.btnF 		 = new JRadioButton (Facile		);
		this.btnM 		 = new JRadioButton (Moyen		);
		this.btnD 		 = new JRadioButton (Difficile	);

		this.btnGroupImg.add(this.btnTF);
		this.btnGroupImg.add(this.btnF );
		this.btnGroupImg.add(this.btnM );
		this.btnGroupImg.add(this.btnD );

		this.btnChoixUnique = new JRadioButton ("Choix Unique"	);
		this.btnChoixMult 	= new JRadioButton ("Choix Multiple");
		this.btnAsso 		= new JRadioButton ("Associatif"	);
		this.btnElim 		= new JRadioButton ("elimination"	);

		this.msgErrNbPts	= new JLabel("");
		this.msgErrTpsRep 	= new JLabel("");
		this.msgErrRess	 	= new JLabel("");
		this.msgErrNiv 		= new JLabel("");
		this.msgErrNot 		= new JLabel("");
		this.msgChoixType	= new JLabel("");

		this.btnGroup = new ButtonGroup();

		this.btnGroup.add(this.btnChoixUnique);
		this.btnGroup.add(this.btnChoixMult);
		this.btnGroup.add(this.btnAsso);
		this.btnGroup.add(this.btnElim);

		this.btnCreer = new JButton("Creer");

		//ActionListener / itemListener
		this.nbPoints   	.addActionListener(this);
		this.tpsReponses	.addActionListener(this);

		this.btnTF 			.addActionListener(this);
		this.btnF 			.addActionListener(this);
		this.btnM 			.addActionListener(this);
		this.btnD 			.addActionListener(this);

		this.btnChoixUnique	.addActionListener(this);
		this.btnChoixMult	.addActionListener(this);
		this.btnAsso		.addActionListener(this);
		this.btnElim		.addActionListener(this);

		this.choixRessource .addItemListener(this);
		this.choixNotion 	.addItemListener(this);

		this.btnCreer		.addActionListener(this);

		//autres
		JPanel temp = new JPanel();
		temp.add(this.nbPoints, BorderLayout.WEST);

		JPanel temp2 = new JPanel();
		temp2.add(this.tpsReponses, BorderLayout.WEST);

		JPanel temp3 = new JPanel();
		temp3.add(this.btnCreer);

		this.nbPoints	.setColumns(5);
		this.tpsReponses.setColumns(5);

		//Premiere ligne 
		JPanel ligne1 = new JPanel(new FlowLayout(FlowLayout.LEFT,50,10));
		ligne1.add(new JLabel("nombre de points" ));
		ligne1.add(new JLabel("temps de répones "));
		this.add(ligne1);
		

		//Deuxieme ligne 
		JPanel ligne2 = new JPanel(new FlowLayout(FlowLayout.LEFT,50,0));
		ligne2.add(temp );
		ligne2.add(temp2);
		this.add(ligne2);

		//Troisieme ligne 
		JPanel ligne3 = new JPanel(new FlowLayout(FlowLayout.LEFT,50,0));
		ligne3.add(this.msgErrNbPts );
		ligne3.add(this.msgErrTpsRep);
		this.add(ligne3);

		//Troisieme ligne 
		JPanel ligne4 = new JPanel(new FlowLayout(FlowLayout.LEFT,50,0));
		ligne4.add(new JLabel("Ressource"	));
		ligne4.add(new JLabel("Notion"		));
		ligne4.add(new JLabel("Niveau"		));
		this.add(ligne4);

		//Quatrieme ligne 
		JPanel ligne5 = new JPanel(new FlowLayout(FlowLayout.LEFT,50,0));
		JPanel ligne5Img = new JPanel();

		ligne5		.add(this.choixRessource);
		ligne5		.add(this.choixNotion	);
		ligne5Img	.add(this.btnTF			);
		ligne5Img	.add(this.btnF			);
		ligne5Img	.add(this.btnM			);
		ligne5Img	.add(this.btnD			);
		ligne5.add(ligne5Img);
		this.add(ligne5);

		//Troisieme ligne 
		JPanel ligneerr2 = new JPanel(new FlowLayout(FlowLayout.LEFT,50,0));
		ligneerr2.add(this.msgErrRess );
		ligneerr2.add(this.msgErrNot  );
		ligneerr2.add(this.msgErrNiv  );
		this.add(ligneerr2);


		//Cinquième ligne 
		JPanel ligne6 = new JPanel(new FlowLayout(FlowLayout.LEFT,60,0));
		ligne6.add(new JLabel("Choix tu type de question"));
		this.add(ligne6);


		//Sixième ligne 
		JPanel ligne7 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		ligne7.add(this.btnChoixUnique);
		ligne7.add(this.btnChoixMult	);
		ligne7.add(this.btnAsso);
		ligne7.add(this.btnElim);
		this.add(ligne7);

		JPanel ligneerr3 = new JPanel(new FlowLayout(FlowLayout.LEFT,50,0));
		ligneerr3.add(this.msgChoixType );
		this.add(ligneerr3);

		//Septième ligne 
		this.add(temp3);
		

		this.setVisible(true);

	}

	public void actionPerformed(ActionEvent e)
	{
		boolean peutCreer=true;

		//System.out.println(	e.paramString());
		if (e.getSource().equals(this.btnCreer))
		{
			if (this.nbPoints.getText().equals(""))
			{
				this.msgErrNbPts.setForeground(Color.RED);
				this.msgErrNbPts.setText("Vous devez rentrer une nombre de points");
				peutCreer=false;
			}
			else 
			{
				try 
				{
					this.nbPointQuestion = Integer.parseInt(this.nbPoints.getText());
					this.msgErrNbPts.setText("");

				}
				catch(Exception ex) 
				{
					System.out.println("Entrez un entier");
					this.msgErrNbPts.setForeground(Color.RED);
					this.msgErrNbPts.setText("Vous devez rentrer un entier pour le nombre de points");
					peutCreer=false;
				}
			}

			if (this.tpsReponses.getText().equals(""))
			{
				this.msgErrTpsRep.setForeground(Color.RED);
				this.msgErrTpsRep.setText("Vous devez rentrer un temps de réponse");
				peutCreer=false;
			}
			else 
			{
				try 
				{
					this.tempsQuestion = Integer.parseInt(this.tpsReponses.getText());
					this.msgErrTpsRep.setText("");

				}
				catch(Exception ex) 
				{
					System.out.println("Entrez un entier");
					this.msgErrTpsRep.setForeground(Color.RED);
					this.msgErrTpsRep.setText("Vous devez rentrer un entier pour le temps des réponses");
					peutCreer=false;
				}
			}
			
			if (this.choixRessource.getSelectedItem().equals("				"))
			{
				this.msgErrRess.setForeground(Color.RED);
				this.msgErrRess.setText("Vous devez choisir une ressource");
				peutCreer=false;
			}
			else 
				this.msgErrRess.setText("");
			
			if (this.choixNotion.getSelectedItem().equals(""))
			{
				this.msgErrNot.setForeground(Color.RED);
				this.msgErrNot.setText("Vous devez choisir une notion");
				peutCreer=false;
			}
			else 
				this.msgErrNot.setText("");
			
			if (!(this.btnChoixUnique.isSelected() || this.btnChoixMult.isSelected() || this.btnAsso.isSelected() || this.btnElim.isSelected()))
			{
				this.msgChoixType.setForeground(Color.RED);
				this.msgChoixType.setText("Vous devez choisir un type");
				peutCreer=false;
			}
			else 
				this.msgChoixType.setText("");
			
			if (!(this.btnTF.isSelected() || this.btnF.isSelected() || this.btnM.isSelected() || this.btnD.isSelected()))
			{
				this.msgErrNiv.setForeground(Color.RED);
				this.msgErrNiv.setText("Vous devez choisir un niveau de difficulté");
				peutCreer=false;
			}
			else 
				this.msgErrNiv.setText("");

			if (peutCreer)
			{
				new FrameCreerQCMRepUnique(this);
				this.fr.dispose();
			}
				
		}

		if (this.btnTF.isSelected() && !this.choixNotion.getSelectedItem().equals("")&& !this.choixRessource.getSelectedItem().equals("				"))
		{
			this.btnTF.setIcon(new ImageIcon("img/TF1.PNG"));
			this.btnF .setIcon(new ImageIcon("img/F2.PNG" ));
			this.btnM .setIcon(new ImageIcon("img/M2.PNG" ));
			this.btnD .setIcon(new ImageIcon("img/D2.PNG" ));
			this.difficulteQuestion = 1;
			this.msgErrNiv.setText("");
		}

		else if (this.btnF.isSelected() && !this.choixNotion.getSelectedItem().equals("") && !this.choixRessource.getSelectedItem().equals("				"))
		{
			this.btnTF.setIcon(new ImageIcon("img/TF2.PNG"));
			this.btnF .setIcon(new ImageIcon("img/F1.PNG" ));
			this.btnM .setIcon(new ImageIcon("img/M2.PNG" ));
			this.btnD .setIcon(new ImageIcon("img/D2.PNG" ));
			this.difficulteQuestion = 2;
			this.msgErrNiv.setText("");
		}

		else if (this.btnM.isSelected() && !this.choixNotion.getSelectedItem().equals("")&& !this.choixRessource.getSelectedItem().equals("				"))
			{
			this.btnTF.setIcon(new ImageIcon("img/TF2.PNG"));
			this.btnF .setIcon(new ImageIcon("img/F2.PNG" ));
			this.btnM .setIcon(new ImageIcon("img/M1.PNG" ));
			this.btnD .setIcon(new ImageIcon("img/D2.PNG" ));
			this.difficulteQuestion = 3;
			this.msgErrNiv.setText("");
		}

		else if (this.btnD.isSelected() && !this.choixNotion.getSelectedItem().equals("")&& !this.choixRessource.getSelectedItem().equals("				"))
		{
			this.btnTF.setIcon(new ImageIcon("img/TF2.PNG"));
			this.btnF .setIcon(new ImageIcon("img/F2.PNG" ));
			this.btnM .setIcon(new ImageIcon("img/M2.PNG" ));
			this.btnD .setIcon(new ImageIcon("img/D1.PNG" ));
			this.difficulteQuestion = 4;
			this.msgErrNiv.setText("");
		}
		
	}

	public void itemStateChanged(ItemEvent e)
	{
		//System.out.println(	e.paramString());

		if (e.getSource().equals(this.choixRessource) && !this.choixRessource.getSelectedItem().equals("				"))
		{
			this.choixNotion.setEnabled(true);
			
			this.choixNotion.removeAll() ;
			this.choixNotion.add("");

			this.ressourceQuestion = this.choixRessource.getSelectedItem();

			for (String s : this.ctrl.getChoixNotion(this.choixRessource.getSelectedItem()))
				this.choixNotion.add(s);
		}

		if (e.getSource().equals(this.choixNotion) && !this.choixRessource.getSelectedItem().equals(""))
		{
			this.notionQuestion=this.choixRessource.getSelectedItem();
			this.btnGroupImg.clearSelection();
			this.btnTF.setIcon(new ImageIcon("img/TF2.PNG"));
			this.btnF .setIcon(new ImageIcon("img/F2.PNG" ));
			this.btnM .setIcon(new ImageIcon("img/M2.PNG" ));
			this.btnD .setIcon(new ImageIcon("img/D2.PNG" ));
		}
		
	}

	public void creer(String explication, String intituleQuestion, ArrayList<String> lstReponses) // penser a ajouter explication
	{
		this.typeQuestion="QCM";
		this.textQuestion = intituleQuestion;
		this.explicationQuestion=explication;
		//creerQuestion(String type, String nomRessource, String nomNotion, String text, String explication, int timer, int nbPoint, ArrayList<String> lstReponse, int difficulte)
		this.ctrl.creerQuestion(this.typeQuestion, this.ressourceQuestion, this.notionQuestion, this.textQuestion, this.explicationQuestion, this.tempsQuestion, this.nbPointQuestion, lstReponses, this.difficulteQuestion);
	}

}