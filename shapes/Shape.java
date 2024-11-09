package shapes;

import events.ColorChangedActionEvent;
import events.ColorChangedActionListener;
import events.StateChangedActionEvent;
import events.StateChangedActionListener;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public abstract class Shape {

	private static final Color DEFAULT_COLOR = Color.BLACK;

	private static final double DEFAULT_STROKE_WIDTH = 2;

	private static final int DEFAULT_WIDTH = 25;

	private static final int DEFAULT_HEIGHT = 25;

	protected Point point1;
	protected Point point2;

	protected Color color;
	protected double strokeWidth;
	protected boolean selected;

	public Shape(Point p) {
		point1 = p;
		point2 = new Point(p.x + DEFAULT_WIDTH, p.y + DEFAULT_HEIGHT);
		color = DEFAULT_COLOR;
		selected = false;
		strokeWidth = DEFAULT_STROKE_WIDTH;
	}

	public void draw(Graphics g) {
		g.setColor(color);
		drawShape(g);
		if (selected) {
			drawSelectionIndicator(g);
		}
	}

	public void drawSelectionIndicator(Graphics g) {

		((Graphics2D) g).setStroke(new BasicStroke((float) 1.0));
		g.setColor(new Color(255, 0, 255));

		int len = 10;
		int off = 5;

		Point p1 = getPosition();
		Point p2 = new Point(getPosition().x + getSize().x, getPosition().y
				+ getSize().y);

		g.drawPolyline(
				// left up
				new int[] { p1.x - off, p1.x - off, p1.x - off + len },
				new int[] { p1.y - off + len, p1.y - off, p1.y - off }, 3);

		g.drawPolyline(
				// right down
				new int[] { p2.x + off - len, p2.x + off, p2.x + off },
				new int[] { p2.y + off, p2.y + off, p2.y + off - len }, 3);

		g.drawPolyline(
				// right up
				new int[] { p2.x + off - len, p2.x + off, p2.x + off },
				new int[] { p1.y - off, p1.y - off, p1.y - off + len }, 3);

		g.drawPolyline(
				// left down
				new int[] { p1.x - off, p1.x - off, p1.x - off + len },
				new int[] { p2.y + off - len, p2.y + off, p2.y + off }, 3);

	}

	public abstract void drawShape(Graphics g);

	public Color getColor() {
		return color;
	}

	public Point getPosition() {
		return new Point(Math.min(point1.x, point2.x), Math.min(point1.y,
				point2.y));
	}

	public Point getSize() {
		return new Point(Math.abs(point2.x - point1.x), Math.abs(point2.y
				- point1.y));
	}

	/**
	 * Checks if the Shape contains the given point. Has a 2 pixel margin in all
	 * directions.
	 * 
	 * @param p
	 *            point to check
	 * @return true if shape includes given point
	 */
	public boolean includes(Point p) {
		if ((p.x - getPosition().x > -2)
				&& (p.x - getPosition().x < getSize().x + 2)
				&& (p.y - getPosition().y > -2)
				&& (p.y - getPosition().y < getSize().y + 2)) {
			return true;
		}
		return false;
	}

	public void move(int x, int y) {
		point1.x = point1.x + x;
		point1.y = point1.y + y;
		point2.x = point2.x + x;
		point2.y = point2.y + y;
		this.fireStateChanged();
	}

	public void setColor(Color c) {
		color = c;
		this.fireStateChanged();
		this.fireColorChanged(c);
	}

	public void setPoint1(Point p) {
		this.point1 = p;
	}

	public void setPoint2(Point p) {
		this.point2 = p;
		this.fireStateChanged();
	}

	public void setSelected(boolean b) {
		selected = b;
	}

	public String toString() {
		String str = "" + point1.x;
		str += "," + point1.y;
		str += ";" + point2.x;
		str += "," + point2.y;
		str += ";" + color.getRGB();

		return str;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Shape otherShape) {
			return this.point1.equals(otherShape.point1)
					&& this.point2.equals(otherShape.point2)
					&& this.selected == otherShape.selected
					&& this.color.equals(otherShape.color)
					&& this.strokeWidth == otherShape.strokeWidth;
		}
		return super.equals(obj);
	}

	public Point getPoint2() {
		return this.point2;
	}

	public Point getPoint1() {
		return this.point1;
	}

	public abstract Shape clone();


	// ------------------------------- EVENTS ---------------------------------

	private ArrayList<StateChangedActionListener> repaintActionListener = new ArrayList<>();

	public void addRepaintActionListener(StateChangedActionListener listener) {
		repaintActionListener.add(listener);
	}

	public void removeRepaintActionListener(StateChangedActionListener listener) {
		repaintActionListener.remove(listener);
	}

	protected void fireStateChanged() {
		for(StateChangedActionListener listener: repaintActionListener) {
			StateChangedActionEvent event = new StateChangedActionEvent(listener);
			listener.stateChanged(event);
		}
	}

	private final ArrayList<ColorChangedActionListener> colorChangedActionListeners = new ArrayList<>();

	public void addColorChangedListener(ColorChangedActionListener listener) {
		colorChangedActionListeners.add(listener);
	}

	public void removeRepaintActionListener(ColorChangedActionListener listener) {
		colorChangedActionListeners.remove(listener);
	}

	public void clearAllRepaintActionListener() {
		colorChangedActionListeners.clear();
	}

	private void fireColorChanged(Color color) {
		for(ColorChangedActionListener listener: colorChangedActionListeners) {
			ColorChangedActionEvent event = new ColorChangedActionEvent(listener);
			event.setColor(color);
			listener.colorChanged(event);
		}
	}
}
