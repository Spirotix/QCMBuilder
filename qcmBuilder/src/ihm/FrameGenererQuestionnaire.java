//package src.ihm;

import javax.swing.*;

public class FrameCreerQuestionnaire extends JFrame
{
	private PanelCreerQuestionnaire panelQ	;
	private TestCreerQuestion		ctrl	;

	public FrameCreerQuestionnaire (TestCreerQuestion ctrl)
	{
		this.ctrl=ctrl;

		this.setTitle   ("Creation d'un questionnaire"	);
		this.setSize    ( 500,500  						);

		this.panelQ=new PanelCreerQuestionnaire(this.ctrl);

		this.add (this.panelQ);

		this.setVisible(true);
	}
}