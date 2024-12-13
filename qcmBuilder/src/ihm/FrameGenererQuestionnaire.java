package src.ihm;

import javax.swing.*;
import src.Controleur;

public class FrameGenererQuestionnaire extends JFrame
{
	private PanelGenererQuestionnaire panelQ	;
	private Controleur				  ctrl		;

	public FrameGenererQuestionnaire (Controleur ctrl)
	{
		this.ctrl=ctrl;

		this.setTitle   ("Creation d'un questionnaire"	);
		this.setSize    ( 500,600  						);

		this.panelQ=new PanelGenererQuestionnaire(this.ctrl);

		this.add (this.panelQ);

		this.setVisible(true);
	}
}