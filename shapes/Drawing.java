package shapes;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Drawing implements Iterable<Shape> {

	private ArrayList<Shape> shapes;

	private Dimension size;

	private Selection selection;

	public Drawing(Dimension size) {
		shapes = new ArrayList<Shape>(0);
		selection = new Selection(this);

		this.size = size;
	}

	public Shape getShapeAt(Point p) {
		int index = shapes.size() - 1;
		while (index >= 0) {

			if (shapes.get(index).includes(p)) {
				return shapes.get(index);
			}
			index--;
		}
		return null;

	}

	public void insertShape(Shape s) {
		shapes.add(s);
	}

	@Override
	public Iterator<Shape> iterator() {
		return shapes.iterator();
	}

	public void listShapes() {
		System.out.println("---");
		for (Shape s : shapes) {
			System.out.println(s);
		}
		System.out.println("---");
	}

	public void lower(Shape s) {
		int index = shapes.indexOf(s);
		if (index < shapes.size() - 1) {
			shapes.remove(s);
			shapes.add(index, s);
		}
	}

	public int nShapes() {
		return shapes.size();
	}

	public void raise(Shape s) {
		int index = shapes.indexOf(s);
		if (index > 0) {
			shapes.remove(s);
			shapes.add(--index, s);
		}
	}

	public void removeShape(Shape s) {
		shapes.remove(s);
	}

	public Dimension getSize() {
		return size;
	}

	public Selection getSelection() {
		return selection;
	}

	public void clearSelection() {
		selection.empty();
	}

	public void selectAll() {
		for (Shape sh : shapes) {
			selection.add(sh);
		}
	}

	public void drawShapes(Graphics g) {
		for (Shape s : shapes) {
			s.draw(g);
		}
	}
}