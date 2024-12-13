package src.ihm.question;
//package src.ihm;

import javax.swing.*;

public class FrameCreerQCMRepUnique extends JFrame
{
	private PanelCreerQuestion 		panelQ;
	private PanelCreerQCMRepUnique 	panel ;

	public FrameCreerQCMRepUnique (PanelCreerQuestion panelQ)
	{
		this.panelQ=panelQ;

		
		this.setTitle   ("Creation d'un QCM a choix unique");
		this.setSize    ( 500,500  );

		this.panel=new PanelCreerQCMRepUnique(this.panelQ);

		this.add (this.panel);

		this.setVisible(true);
	}


}