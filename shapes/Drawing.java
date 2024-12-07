package shapes;

import events.*;

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

	public Shape getByIndex(int i) {
		return shapes.get(i);
	}

	public void insertShape(Shape shape) {
		shapes.add(shape);
		this.fireShapeIsInserted(shape);
		for(StateChangedActionListener listener: repaintActionListener) {
			shape.addRepaintActionListener(listener);
		}
		this.fireStateChanged();
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

	public String toString() {
		String result = "";
		result += STR."\{this.getSize().width},\{this.getSize().height}\n";
		for (Shape s : shapes) {
			result += s.toString() + "\n";
		}
		return result;
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

	public void removeShape(Shape shape) {
		for(StateChangedActionListener listener: repaintActionListener) {
			shape.removeColorChangedActionListener(listener);
		}
		shapes.remove(shape);
		this.fireShapeIsDeleted(shape);
		this.fireStateChanged();
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

	public static Drawing fromString(String string) {
		String[] pointString = string.split(",");
		Point point = new Point(Integer.parseInt(pointString[0].trim()), Integer.parseInt(pointString[1].trim()));
		return new Drawing(new Dimension(point.x, point.y));
	}

	public boolean isEmpty() {
		return shapes.isEmpty();
	}


	// ------------------------------- EVENTS ---------------------------------

	private ArrayList<StateChangedActionListener> repaintActionListener = new ArrayList<>();

	public void addRepaintActionListener(StateChangedActionListener listener) {
		repaintActionListener.add(listener);
		selection.addRepaintActionListener(listener);
	}

	public void removeRepaintActionListener(StateChangedActionListener listener) {
		repaintActionListener.remove(listener);
		selection.removeRepaintActionListener(listener);
	}

	private void fireStateChanged() {
		for(StateChangedActionListener listener: repaintActionListener) {
			StateChangedActionEvent event = new StateChangedActionEvent(listener);
			listener.stateChanged(event);
		}
	}

	private ArrayList<ShapeIsInsertedActionListener> shapeIsInsertedActionListeners = new ArrayList<>();

	public void addShapeIsInsertedActionListener(ShapeIsInsertedActionListener listener) {
		shapeIsInsertedActionListeners.add(listener);
	}

	public void removeShapeIsInsertedActionListener(ShapeIsInsertedActionListener listener) {
		shapeIsInsertedActionListeners.remove(listener);
	}

	private void fireShapeIsInserted(Shape shape) {
		for(ShapeIsInsertedActionListener listener: shapeIsInsertedActionListeners) {
			ShapeIsInsertedActionEvent event = new ShapeIsInsertedActionEvent(listener);
			event.setShape(shape);
			listener.shapeIsInserted(event);
		}
	}

	private ArrayList<ShapeIsDeletedActionListener> shapeIsDeletedActionListeners = new ArrayList<>();

	public void addShapeIsDeletedActionListener(ShapeIsDeletedActionListener listener) {
		shapeIsDeletedActionListeners.add(listener);
	}

	public void removeShapeIsDeletedActionListener(ShapeIsDeletedActionListener listener) {
		shapeIsDeletedActionListeners.remove(listener);
	}

	private void fireShapeIsDeleted(Shape shape) {
		for(ShapeIsDeletedActionListener listener: shapeIsDeletedActionListeners) {
			ShapeIsDeletedActionEvent event = new ShapeIsDeletedActionEvent(listener);
			event.setShape(shape);
			listener.shapeIsDeleted(event);
		}
	}
}
