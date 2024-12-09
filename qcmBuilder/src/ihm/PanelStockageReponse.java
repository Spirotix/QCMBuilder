import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class PanelStockageReponse extends JPanel
{

	public PanelStockageReponse (ArrayList<PanelReponse> lstReponse)
	{
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 

		for (PanelReponse p : lstReponse)
			this.add(p);

		this.setVisible(true);

	}

	public void mettreAJour(ArrayList<PanelReponse> lstReponse) 
	{
		this.removeAll(); 
		this.setLayout(new GridLayout(lstReponse.size(), 1)); 

		for (PanelReponse p : lstReponse) 
			this.add(p);

		this.revalidate	(); 
		this.repaint	();
	}

	
}