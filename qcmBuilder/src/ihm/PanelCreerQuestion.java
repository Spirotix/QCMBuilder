import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.event.*;

public class PanelCreerQuestion extends JPanel implements ActionListener, ItemListener
{
	//private 	Controleur 	ctrl 	;
	private 	TestCreerQuestion ctrl;
	private 	JTextField 		nbPoints, tpsReponses;
	private  	Choice 			choixRessource, choixNotion;
	private 	ButtonGroup		btnGroup;
	private 	JRadioButton	btnChoixUnique, btnChoixMult, btnAsso, btnElim;
	private		JButton 		btnCreer;	
	private 	JLabel			msgErrNbPts, msgErrTpsRep, msgErrRess,msgErrNiv,msgErrNot, msgChoixType ;

	public PanelCreerQuestion (/*Controleur ctrl*/ TestCreerQuestion ctrl)
	{
		this.ctrl = ctrl;
		this.setLayout (new GridLayout(9,1));

		//Initialisation
		this.nbPoints    = new JTextField();
		this.tpsReponses = new JTextField();

		this.choixRessource	 = new Choice();
		this.choixRessource	.add("				");

		for (String s : this.ctrl.getChoixRessources())
			this.choixRessource	.add(s);

		this.choixNotion	 = new Choice();
		this.choixNotion	.add("				");

		//this.choixNotion.setEnabled(false);
		/* 
		this.choixNotion	.add("");

		for (String s : this.ctrl.getChoixNotion())
			this.choixNotion.add(s);*/

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

		//Troisieme ligne 
		JPanel ligneerr2 = new JPanel(new FlowLayout(FlowLayout.LEFT,50,0));
		ligneerr2.add(this.msgErrNot );
		ligneerr2.add(this.msgErrNiv);
		this.add(ligne3);


		//Quatrieme ligne 
		JPanel ligne5 = new JPanel(new FlowLayout(FlowLayout.LEFT,50,0));
		ligne5.add(this.choixRessource);
		ligne5.add(this.choixNotion	);
		this.add(ligne5);

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

		//Septième ligne 
		this.add(temp3);
		

		this.setVisible(true);

	}

	public void actionPerformed(ActionEvent e)
	{
		boolean peutCreer=true;

		if (e.getSource().equals(this.btnCreer))
		{
			if (this.nbPoints.getText().equals(""))
			{
				this.msgErrNbPts.setForeground(Color.RED);
				this.msgErrNbPts.setText("Vous devez rentrer une nombre de points");
				peutCreer=false;
			}
			else 
				this.msgErrNbPts.setText("");

			if (this.tpsReponses.getText().equals(""))
			{
				this.msgErrTpsRep.setForeground(Color.RED);
				this.msgErrTpsRep.setText("Vous devez rentrer un temps de réponse");
				peutCreer=false;
			}
			else 
				this.msgErrTpsRep.setText("");

			if (peutCreer)
				System.out.println("Peut creer");
		}
		
	}

	public void itemStateChanged(ItemEvent e)
	{
		System.out.println(	e.paramString());

		if (e.getSource().equals(this.choixRessource))
		{
			this.choixNotion.setEnabled(true);
			
			this.choixNotion.	removeAll();
			this.choixNotion	.add("");

			for (String s : this.ctrl.getChoixNotion(this.choixRessource.getSelectedItem()))
				this.choixNotion.add(s);
		}
		
	}
}