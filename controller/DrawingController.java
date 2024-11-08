package controller;

import actions.*;
import events.*;
import gui.DrawGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import shapes.Drawing;
import shapes.FillableShape;
import shapes.Shape;

public class DrawingController implements ColorChangedActionListener {

	private Drawing drawing;
	private UndoManager undoManager;
	private DrawGUI gui;

	public DrawingController(DrawGUI g) {
		drawing = null;
		undoManager = new UndoManager();
		gui = g;
	}

	public void addShape(Shape s) {
		DrawAction add = new AddAction(drawing, s);
		add.execute();
		undoManager.addAction(add);
		this.fireStateChanged();
	}

	public void resizeShape(Shape shape, Point point2) {
		DrawAction add = new ResizeAction(shape, point2);
		add.execute();
		undoManager.addAction(add);
		this.fireStateChanged();
	}

	public void colorSelectedShapes(Color c) {
		for (Shape s : drawing.getSelection()) {
			DrawAction col = new ColorAction(s, c);
			col.execute();
			undoManager.addAction(col);
		}
		this.fireStateChanged();
	}

	public void colorShape(Shape shape, Color color) {
		DrawAction col = new ColorAction(shape, color);
		col.execute();
	}

	public void deleteSelectedShapes() {
		DrawAction del = new DeleteAction(drawing.getSelection());
		del.execute();
		undoManager.addAction(del);
		this.fireStateChanged();
	}

	public Drawing getDrawing() {
		return drawing;
	}

	public void moveSelectedShapes(Point movement) {
		if (!drawing.getSelection().isEmpty()) {
			DrawAction move = new MoveAction(drawing.getSelection(), movement);
			move.execute();
			undoManager.addAction(move);
			this.fireStateChanged();
		}
	}

	public void newDrawing(Dimension size) {
		drawing = new Drawing(size);
		if (gui != null) {
			gui.updateDrawing();
		}
	}

	public void redo() {
		if (this.undoManager.canRedo()) {
			this.undoManager.redo();
		}
		this.fireStateChanged();
	}

	public void selectAll() {
		drawing.clearSelection();
		drawing.selectAll();
		this.fireStateChanged();
	}

	public void toggleFilled() {
		DrawAction toggle = new FillAction(drawing.getSelection());
		toggle.execute();
		undoManager.addAction(toggle);
		this.fireStateChanged();
	}

	public void undo() {
		if (this.undoManager.canUndo()) {
			this.undoManager.undo();
		}
		this.fireStateChanged();
	}

	public void clearSelection() {
		this.drawing.clearSelection();
		this.fireStateChanged();
	}

	public void addSelectionShape(Shape shape) {
		this.drawing.getSelection().add(shape);
		this.fireStateChanged();
		if(shape instanceof FillableShape) {
			for (FillChangedActionListener listener : fillChangedActionListeners) {
				((FillableShape) shape).addFillChangedActionListener(listener);
			}
		}
	}

	@Override
	public void colorChanged(ColorChangedActionEvent event) {
		this.colorSelectedShapes(event.getColor());
	}


	// ------------------------------- EVENTS ---------------------------------
	private ArrayList<StateChangedActionListener> repaintActionListener = new ArrayList<>();

	public void addRepaintActionListener(StateChangedActionListener listener) {
		repaintActionListener.add(listener);
	}

	public void removeRepaintActionListener(StateChangedActionListener listener) {
		repaintActionListener.remove(listener);
	}

	private void fireStateChanged() {
		for(StateChangedActionListener listener: repaintActionListener) {
			StateChangedActionEvent event = new StateChangedActionEvent(listener);
			listener.stateChanged(event);
		}
	}

	private ArrayList<FillChangedActionListener> fillChangedActionListeners = new ArrayList<>();

	public void addFillChangedActionListener(FillChangedActionListener listener) {
		fillChangedActionListeners.add(listener);
	}

	public void removeFillChangedActionListener(FillChangedActionListener listener) {
		fillChangedActionListeners.remove(listener);
	}
}
