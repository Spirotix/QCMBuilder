//package src.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class PaintComponentAsso extends JPanel
{
	private PanelCreerQuestionAsso panelQ;

	private Graphics2D g2	;

	public PaintComponentAsso (PanelCreerQuestionAsso panelQ)
	{
		this.panelQ=panelQ;

	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		this.g2 = (Graphics2D) g;
		PanelReponseGaucheAsso pg = null;

		System.out.println("Ca affiche");

		this.g2.setColor (Color.BLACK);

		for (int j=0; j<this.panelQ.getLstReponses().size(); j++)
		{
			System.out.println("Ca affiche2");
			pg = this.panelQ.getLstReponses().get(j).getPanelGauche();
			if (pg!=null && pg.getListe().size()!=0)
			{
				System.out.println("Ca affiche3");
				for (int i=0; i<pg.getListe().size(); i++)
					this.g2.drawLine (pg.getBoutton().getX(), pg.getBoutton().getY(), pg.getListe().get(i).getX(),pg.getListe().get(i).getY());
			}
		}
			
				
	}

	
}