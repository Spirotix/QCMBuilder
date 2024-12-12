//package src.ihm;

import java.awt.*;
import javax.swing.*;
//import src.Controleur;

public class FrameCreerQuestion extends JFrame
{
	private TestCreerQuestion 		ctrl 	;
	private PanelCreerQuestion 	panelQ;

	public FrameCreerQuestion (TestCreerQuestion ctrl)
	{
		this.ctrl=ctrl;

		
		this.setTitle   ("Creation de question");
		this.setSize    ( 630,350  );
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setBackground(Color.LIGHT_GRAY);

		this.panelQ=new PanelCreerQuestion(this.ctrl, this);

		this.add (this.panelQ);

		this.setVisible(true);

	}
}