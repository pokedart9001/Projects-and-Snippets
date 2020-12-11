import javax.swing.*;
import java.awt.*;

public class Canvas extends JComponent
{
	Color foreground;
	Color background;
	Font font;
	String message;
	int offset;
	int increment;
	int speed;

	public Canvas()
	{
		setVals();
	}

	public Canvas(Color foreground, Color background, Font font, String message, int offset, int increment, int speed)
	{
		setVals(foreground, background, font, message, offset, increment, speed);
	}

	public void setVals()
	{
		setVals(Color.black, Color.white, new Font("Calibri", Font.BOLD, 50), "Message", 0, 1, 1);
	}

	public void setVals(Color foreground, Color background, Font font, String message, int offset, int increment, int speed)
	{
		setColors(foreground, background);
		setMessage(message, font);
		setTimerVals(offset, increment, speed);
	}

	public void setColors(Color foreground, Color background)
	{
		this.foreground = foreground;
		this.background = background;
	}

	public void setMessage(String message, Font font)
	{
		this.message = message;
		this.font = font;
	}

	public void setTimerVals(int offset, int increment, int speed)
	{
		this.offset = offset;
		this.increment = increment;
		this.speed = speed;
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(background);
    	g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(foreground);
		g.setFont(font);
    	FontMetrics fm = g.getFontMetrics();
    	int w = fm.stringWidth(message) + 5;
    	int h = fm.getAscent();

    	offset %= getBounds().width + w;
    	if (offset < 0)
    		offset = getBounds().width + w - 1;

    	g.drawString(message, offset - w, 120 + (h / 4));
	}

	public void update(Graphics g)
	{
		super.update(g);
	}
}