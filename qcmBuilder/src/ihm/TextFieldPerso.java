package src.ihm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class TextFieldPerso extends JTextField 
{

	private String chaine;

	public TextFieldPerso(String chaine) 
	{
		this.chaine = chaine;

		this.addFocusListener(new FocusAdapter() 
		{
			@Override
			public void focusGained(FocusEvent e) 
			{
				if (getText().isEmpty()) 
					repaint();
			}

			@Override
			public void focusLost(FocusEvent e) 
			{
				if (getText().isEmpty()) 
					repaint();
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);

		if (getText().isEmpty() && !isFocusOwner()) 
		{
			Graphics2D g2 = (Graphics2D) g;

			g2.setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor			(Color.GRAY); 
			g2.setFont 			(getFont().deriveFont(Font.ITALIC)); 

			Insets insets = getInsets();
			g2.drawString(this.chaine, insets.left, getHeight() / 2 + g2.getFontMetrics().getAscent() / 2 - 2);
		}
	}
}
