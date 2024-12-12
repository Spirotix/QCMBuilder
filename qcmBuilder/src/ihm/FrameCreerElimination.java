//package src.ihm;

import javax.swing.*;

public class FrameCreerElimination extends JFrame
{
	private PanelCreerQuestion 		panelQ;
	private PanelCreerElim 			panel ;

	public FrameCreerElimination (PanelCreerQuestion panelQ)
	{
		this.panelQ=panelQ;

		
		this.setTitle   ("Creation d'un QCM a choix unique");
		this.setSize    ( 500,500  );

		this.panel=new PanelCreerElim(this.panelQ);

		this.add (this.panel);

		this.setVisible(true);
	}


}