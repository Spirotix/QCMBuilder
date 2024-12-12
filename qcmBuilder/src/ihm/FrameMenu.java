//package src.ihm;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class FrameMenu extends JFrame implements ActionListener
{
	
	private JButton				creerQuestion, creerQuestionnaire, creerNotion ;
	private TestCreerQuestion 	ctrl;
	private JPanel 				panel;
	
	public FrameMenu (TestCreerQuestion ctrl)
	{
		this.ctrl=ctrl;

		this.setLayout(new BorderLayout());
		this.panel = new JPanel(new GridLayout(3, 1, 20, 20));

		
		this.setTitle   ("Menu principale"	);
		this.setSize    ( 500,500  			);

		this.creerQuestion 		= new JButton("Creer Question"		);
		this.creerQuestionnaire = new JButton("Creer Questionnaire"	);
		this.creerNotion 		= new JButton("Creer Notion"		);

		this.creerQuestion		.addActionListener(this);
		this.creerQuestionnaire .addActionListener(this);
		this.creerNotion		.addActionListener(this);

		this.panel.add(this.creerQuestion);
		this.panel.add(this.creerQuestionnaire	);
		this.panel.add(this.creerNotion			);

		this.add (new JPanel(), BorderLayout.SOUTH);
		this.add (new JPanel(), BorderLayout.NORTH);
		this.add (new JPanel(), BorderLayout.WEST);
		this.add (new JPanel(), BorderLayout.EAST);

		this.add (this.panel, BorderLayout.CENTER);

		this.setVisible(true);

	}

	

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(this.creerQuestion))
		{
			new FrameCreerQuestion(this.ctrl);
			this.dispose();
		}

		if (e.getSource().equals(this.creerQuestionnaire))
		{
			new FrameCreerQuestionnaire(this.ctrl);
			this.dispose();
		}
	}


}