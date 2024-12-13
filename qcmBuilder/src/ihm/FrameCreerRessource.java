//package src.ihm;

import javax.swing.*;
//import src.Controleur;

public class FrameCreerRessource extends JFrame
{
	private PanelCreerRessource 	panelC	;
	private TestCreerQuestion		ctrl	;

	public FrameCreerRessource (TestCreerQuestion ctrl)
	{
		this.ctrl=ctrl;

		this.setTitle   ("Creation de Ressource et de Notion"	);
		this.setSize    ( 500,500  								);

		this.panelC=new PanelCreerRessource(this.ctrl);

		this.add (this.panelC);

		this.setVisible(true);
	}
}