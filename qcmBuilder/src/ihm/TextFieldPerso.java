package src.ihm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.RoundRectangle2D;

public class TextFieldPerso extends JTextField 
{

	private String chaine;

	public TextFieldPerso(String chaine)
	{
		this.chaine = chaine;
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Background
		g2.setColor(getBackground());
		g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));

		// Placeholder text
		if (getText().isEmpty() && !isFocusOwner())
		{
			g2.setColor(Color.GRAY);
			g2.setFont(getFont().deriveFont(Font.ITALIC));
			g2.drawString(chaine, getInsets().left, g.getFontMetrics().getMaxAscent() + getInsets().top);
		}
	}

	@Override
	protected void paintBorder(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.LIGHT_GRAY);
		g2.draw(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
	}
}
