import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class FrameCreerQuestion extends JFrame
{
	//private Controleur 		ctrl 	;
	private TestCreerQuestion ctrl;
	private PanelCreerQuestion 	panelQ;

	public FrameCreerQuestion (/*Controleur ctrl*/TestCreerQuestion ctrl)
	{
		this.ctrl=ctrl;

		
		this.setTitle   ("Creation de question");
		this.setSize    ( 500,500  );

		this.panelQ=new PanelCreerQuestion(this.ctrl, this);

		this.add (this.panelQ);

		this.setVisible(true);

	}
}