package src.ihm.question;

import src.ihm.*;
import javax.swing.*;

public class FrameCreerAssociation extends JFrame
{
	private PanelCreerQuestion 		panelQ;
	private PanelCreerQuestionAsso 	panel ;

	public FrameCreerAssociation (PanelCreerQuestion panelQ)
	{
		this.panelQ=panelQ;

		
		this.setTitle   ("Creation d'une Question par association");
		this.setSize    ( 500,500  );

		this.panel=new PanelCreerQuestionAsso(this.panelQ);

		this.add (this.panel);

		this.setVisible(true);
	}


}