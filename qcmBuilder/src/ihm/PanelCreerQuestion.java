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

	public PanelCreerQuestion (/*Controleur ctrl*/ TestCreerQuestion ctrl)
	{
		this.ctrl = ctrl;
		this.setLayout (new GridLayout(7,4));

		//Initialisation
		this.nbPoints    = new JTextField();
		this.tpsReponses = new JTextField();

		this.choixRessource	 = new Choice();
		this.choixRessource	.add("");

		for (String s : this.ctrl.getChoixRessources())
			this.choixRessource	.add(s);

		this.choixNotion	 = new Choice();
		this.choixNotion.setEnabled(false);
		/* 
		this.choixNotion	.add("");

		for (String s : this.ctrl.getChoixNotion())
			this.choixNotion.add(s);*/

		this.btnChoixUnique = new JRadioButton ("Choix Unique"	);
		this.btnChoixMult 	= new JRadioButton ("Choix Multiple");
		this.btnAsso 		= new JRadioButton ("Associatif"	);
		this.btnElim 		= new JRadioButton ("elimination"	);

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

		this.nbPoints.setColumns(5);
		this.tpsReponses.setColumns(5);

		//Premiere ligne 
		this.add(new JLabel("nombre de points"));
		this.add(new JLabel("temps de répones "));
		this.add(new JPanel());
		this.add(new JPanel());

		//Deuxieme ligne 
		this.add(temp);
		this.add(temp2);
		this.add(new JPanel());
		this.add(new JPanel());

		//Troisieme ligne 
		this.add(new JLabel("Ressource"));
		this.add(new JLabel("Niveau"));
		this.add(new JLabel("Notion"));
		this.add(new JPanel());

		//Quatrieme ligne 
		this.add(this.choixRessource);
		this.add(this.choixNotion);
		this.add(new JPanel());
		this.add(new JPanel());

		//Cinquième ligne 
		this.add(new JLabel("Choix tu type de question"));
		this.add(new JPanel());
		this.add(new JPanel());
		this.add(new JPanel());

		//Sixième ligne 
		this.add(this.btnChoixUnique);
		this.add(this.btnChoixMult);
		this.add(this.btnAsso);
		this.add(this.btnElim);

		//Septième ligne 
		this.add(temp3);

		this.setVisible(true);

	}

	public void actionPerformed(ActionEvent e)
	{
		System.out.println(	e.paramString());
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