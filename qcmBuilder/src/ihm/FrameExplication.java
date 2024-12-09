import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class FrameExplication extends JFrame implements ActionListener
{
	
	private PanelCreerQCMRepUnique  panelQCM ;
	private JPanel					composant;
	private JTextArea 				texts	 ;
	private JButton 				valider  ;

	public FrameExplication (PanelCreerQCMRepUnique panelQCM)
	{
		this.panelQCM=panelQCM;

		
		this.setTitle   ("Creation d'une explications");
		this.setSize    ( 500,500  );

		this.composant	= new JPanel	(			);
		this.texts 		= new JTextArea (this.panelQCM.getTextExplication(),20,30);
		this.valider 	= new JButton 	("valider"	);

		this.composant.setLayout(new BorderLayout());
		this.composant.add(new JLabel("Explications"), BorderLayout.NORTH);
		this.composant.add(this.texts, BorderLayout.CENTER);
		this.composant.add(this.valider, BorderLayout.SOUTH);

		this.valider.addActionListener(this);

		this.add (this.composant);

		this.setVisible(true);

	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(this.valider))
		{
			System.out.println(this.texts.getText());
			this.panelQCM.setTxtExplication(this.texts.getText());
			this.dispose();
		}
	}


}