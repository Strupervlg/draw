package controller;

import actions.*;
import events.*;
import gui.DrawGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import gui.ToolBox;
import shapes.Drawing;
import shapes.FillableShape;
import shapes.Shape;

public class DrawingController implements ColorChangedActionListener {

	private Drawing drawing;
	private UndoManager undoManager;
	private DrawGUI gui;

	private ToolBox toolBox;

	public DrawingController(DrawGUI g) {
		drawing = new Drawing(new Dimension(500, 380));
		undoManager = new UndoManager();
		gui = g;
	}

	public void addShape(Shape s) {
		DrawAction add = new AddAction(drawing, s);
		add.execute();
		undoManager.addAction(add);
	}

	public void resizeShape(Shape shape, Point point2) {
		DrawAction add = new ResizeAction(shape, point2);
		add.execute();
		undoManager.addAction(add);
	}

	public void colorSelectedShapes(Color c) {
		for (Shape s : drawing.getSelection()) {
			DrawAction col = new ColorAction(s, c);
			col.execute();
			undoManager.addAction(col);
		}
	}

	public void colorShape(Shape shape, Color color) {
		DrawAction col = new ColorAction(shape, color);
		col.execute();
	}

	public void deleteSelectedShapes() {
		DrawAction del = new DeleteAction(drawing.getSelection());
		del.execute();
		undoManager.addAction(del);
	}

	public Drawing getDrawing() {
		return drawing;
	}

	public UndoManager getUndoManager() {
		return undoManager;
	}

	public void moveSelectedShapes(Point movement) {
		if (!drawing.getSelection().isEmpty()) {
			DrawAction move = new MoveAction(drawing.getSelection(), movement);
			move.execute();
			undoManager.addAction(move);
		}
	}

	public void endMoveSelectedShapes() {
		if (!drawing.getSelection().isEmpty()) {
			DrawAction move = new EndMoveAction();
			move.execute();
			undoManager.addAction(move);
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
	}

	public void selectAll() {
		drawing.clearSelection();
		drawing.selectAll();
	}

	public void toggleFilled() {
		DrawAction toggle = new FillAction(drawing.getSelection());
		toggle.execute();
		undoManager.addAction(toggle);
	}

	public void undo() {
		if (this.undoManager.canUndo()) {
			this.undoManager.undo();
		}
	}

	public void clearSelection() {
		this.drawing.clearSelection();
	}

	public void addSelectionShape(Shape shape) {
		if(shape == null) {
			return;
		}
		if(!this.drawing.getSelection().contains(shape)
				&& this.drawing.getSelection().isEmpty()) {
			shape.addColorChangedListener(toolBox.getColorbutton());
		}
		this.drawing.getSelection().add(shape);
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

	public void setToolBox(ToolBox toolBox) {
		this.toolBox = toolBox;
	}

	public ToolBox getToolBox() {
		return this.toolBox;
	}


	// ------------------------------- EVENTS ---------------------------------

	private ArrayList<FillChangedActionListener> fillChangedActionListeners = new ArrayList<>();

	public void addFillChangedActionListener(FillChangedActionListener listener) {
		fillChangedActionListeners.add(listener);
	}

	public void removeFillChangedActionListener(FillChangedActionListener listener) {
		fillChangedActionListeners.remove(listener);
	}
}
