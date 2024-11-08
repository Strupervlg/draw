package shapes;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

public class Text extends Shape {

	private String text;
	private Font font;

	public Text(Text text) {
		this(text.point1.x, text.point1.y, text.font.getSize(), text.text);
	}

	/**
	 * Constructs a new Text shape with the given string as the text.
	 * 
	 * @param x
	 *            x-coordinate
	 * @param y
	 *            y-coordinate
	 * @param fontSize
	 *            font size
	 * @param str
	 *            the text for the shape
	 */
	public Text(int x, int y, int fontSize, String str) {
		super(new Point(x, y));
		font = new Font(null, Font.PLAIN, fontSize);
		text = str;
	}

	public void drawShape(Graphics g) {

		g.setFont(font);
		int w = g.getFontMetrics().stringWidth(text);
		setPoint2(new Point(point1.x + w, point1.y - font.getSize()));
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.drawString(text, point1.x, point1.y);
	}

	public Font getFont() {
		return font;
	}

	public String toString() {
		return "text;" + super.toString() + ";" + font.getSize() + ";"
				+ text.replace(';', '?');
	}

	@Override
	public Shape clone() {
		return new Text(this);
	}

}
