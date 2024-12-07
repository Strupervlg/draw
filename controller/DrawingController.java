package controller;

import actions.*;
import events.*;
import gui.DrawGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import gui.SaveAsDialog;
import shapes.Drawing;
import shapes.FillableShape;
import shapes.Shape;

public class DrawingController implements ColorChangedActionListener,
		ClearSelectedShapesActionListener, SelectShapeActionListener,
		RedoStackChangedActionListener, UndoStackChangedActionListener,
		ShapeIsDeletedActionListener, ShapeIsInsertedActionListener,
		FontChangedActionListener, FillChangedActionListener, CurrentColorChangedActionListener {

	private Drawing drawing;
	private UndoManager undoManager;
	private DrawGUI gui;

	private Color currentColor;

	private boolean currentFill;

	private int currentFontSize;

	public DrawingController(DrawGUI g) {
		this.setDrawing(new Drawing(new Dimension(500, 380)));
		undoManager = new UndoManager();
		undoManager.addUndoStackChangedActionListener(this);
		undoManager.addRedoStackChangedActionListener(this);
		gui = g;
		currentColor = Color.BLACK;
		currentFill = false;
		currentFontSize = 12;
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

	public void deleteSelectedShapes() {
		DrawAction del = new DeleteAction(drawing.getSelection());
		del.execute();
		undoManager.addAction(del);
	}

	private void setDrawing(Drawing drawing) {
		this.drawing = drawing;
		drawing.addShapeIsDeletedActionListener(this);
		drawing.addShapeIsInsertedActionListener(this);
		drawing.getSelection().addClearSelectedShapesActionListener(this);
		drawing.getSelection().addSelectShapeActionListener(this);
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

	public void newDrawing(Dimension size, SaveAsDialog dialog) {
		if(!this.drawing.isEmpty()) {
			dialog.showDialogWithAsk();
		}
		this.undoManager.clear();
		this.setDrawing(new Drawing(size));
		if (gui != null) {
			gui.updateDrawing();
		}
	}

	public void newDrawing(Drawing drawing, SaveAsDialog dialog) {
		if(!this.drawing.isEmpty()) {
			dialog.showDialogWithAsk();
		}
		this.undoManager.clear();
		this.setDrawing(drawing);
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

	public void toggleFilled(boolean filled) {
		if (!drawing.getSelection().isEmpty()) {
			DrawAction toggle = new FillAction(drawing.getSelection(), filled);
			toggle.execute();
			undoManager.addAction(toggle);
		}
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
			for (ColorChangedActionListener listener : colorChangedActionListeners) {
				shape.addColorChangedListener(listener);
			}
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

	public Color getCurrentColor() {
		return currentColor;
	}

	private void setCurrentColor(Color currentColor) {
		this.currentColor = currentColor;
	}
	public boolean getCurrentFill() {
		return currentFill;
	}

	private void setCurrentFill(boolean currentFill) {
		this.currentFill = currentFill;
	}

	public int getCurrentFontSize() {
		return currentFontSize;
	}

	private void setCurrentFontSize(int currentFontSize) {
		this.currentFontSize = currentFontSize;
	}

	@Override
	public void clearSelectedShapes(ClearSelectedShapesActionEvent event) {
		this.fireClearEnabled(false);
		this.fireDeleteEnabled(false);
		this.fireSelectAllEnabled(!drawing.isEmpty());
	}

	@Override
	public void redoStackChanged(RedoStackChangedActionEvent event) {
		this.fireRedoEnabled(event.canRedo());
	}

	@Override
	public void selectedShape(SelectShapeActionEvent event) {
		this.fireClearEnabled(true);
		this.fireDeleteEnabled(true);
		this.fireSelectAllEnabled(!drawing.getSelection().isSelectedAll());
	}

	@Override
	public void shapeIsDeleted(ShapeIsDeletedActionEvent event) {
		this.fireClearEnabled(false);
		this.fireDeleteEnabled(false);
		this.fireSelectAllEnabled(!drawing.isEmpty());
	}

	@Override
	public void undoStackChanged(UndoStackChangedActionEvent event) {
		this.fireUndoEnabled(event.canUndo());
	}

	@Override
	public void shapeIsInserted(ShapeIsInsertedActionEvent event) {
		this.fireSelectAllEnabled(!drawing.getSelection().isSelectedAll());
	}

	@Override
	public void fillChanged(FillChangedActionEvent event) {
		this.setCurrentFill(event.getFill());
	}

	@Override
	public void fontChanged(FontChangedActionEvent event) {
		this.setCurrentFontSize(event.getFontSize());
	}

	@Override
	public void currentColorChanged(ColorChangedActionEvent event) {
		this.setCurrentColor(event.getColor());
	}

	// ------------------------------- EVENTS ---------------------------------

	private ArrayList<FillChangedActionListener> fillChangedActionListeners = new ArrayList<>();

	public void addFillChangedActionListener(FillChangedActionListener listener) {
		fillChangedActionListeners.add(listener);
	}

	public void removeFillChangedActionListener(FillChangedActionListener listener) {
		fillChangedActionListeners.remove(listener);
	}

	private final ArrayList<ColorChangedActionListener> colorChangedActionListeners = new ArrayList<>();

	public void addColorChangedListener(ColorChangedActionListener listener) {
		colorChangedActionListeners.add(listener);
	}

	public void removeColorChangedActionListener(ColorChangedActionListener listener) {
		colorChangedActionListeners.remove(listener);
	}


	private ArrayList<EnableClearActionListener> enableClearActionListeners = new ArrayList<>();

	public void addEnableClearActionListener(EnableClearActionListener listener) {
		enableClearActionListeners.add(listener);
	}

	public void removeEnableClearActionListener(EnableClearActionListener listener) {
		enableClearActionListeners.remove(listener);
	}

	private void fireClearEnabled(boolean isEnable) {
		for(EnableClearActionListener listener: enableClearActionListeners) {
			EnableClearActionEvent event = new EnableClearActionEvent(listener);
			event.setEnable(isEnable);
			listener.clearEnabled(event);
		}
	}


	private ArrayList<EnableSelectAllActionListener> enableSelectAllActionListeners = new ArrayList<>();

	public void addEnableSelectAllActionListener(EnableSelectAllActionListener listener) {
		enableSelectAllActionListeners.add(listener);
	}

	public void removeEnableSelectAllActionListener(EnableSelectAllActionListener listener) {
		enableSelectAllActionListeners.remove(listener);
	}

	private void fireSelectAllEnabled(boolean isEnable) {
		for(EnableSelectAllActionListener listener: enableSelectAllActionListeners) {
			EnableSelectAllActionEvent event = new EnableSelectAllActionEvent(listener);
			event.setEnable(isEnable);
			listener.selectAllEnabled(event);
		}
	}


	private ArrayList<EnableDeleteActionListener> enableDeleteActionListeners = new ArrayList<>();

	public void addEnableDeleteActionListener(EnableDeleteActionListener listener) {
		enableDeleteActionListeners.add(listener);
	}

	public void removeEnableDeleteActionListener(EnableDeleteActionListener listener) {
		enableDeleteActionListeners.remove(listener);
	}

	private void fireDeleteEnabled(boolean isEnable) {
		for(EnableDeleteActionListener listener: enableDeleteActionListeners) {
			EnableDeleteActionEvent event = new EnableDeleteActionEvent(listener);
			event.setEnable(isEnable);
			listener.deleteEnabled(event);
		}
	}


	private ArrayList<EnableRedoActionListener> enableRedoActionListeners = new ArrayList<>();

	public void addEnableRedoActionListener(EnableRedoActionListener listener) {
		enableRedoActionListeners.add(listener);
	}

	public void removeEnableRedoActionListener(EnableRedoActionListener listener) {
		enableRedoActionListeners.remove(listener);
	}

	private void fireRedoEnabled(boolean isEnable) {
		for(EnableRedoActionListener listener: enableRedoActionListeners) {
			EnableRedoActionEvent event = new EnableRedoActionEvent(listener);
			event.setEnable(isEnable);
			listener.redoEnabled(event);
		}
	}


	private ArrayList<EnableUndoActionListener> enableUndoActionListeners = new ArrayList<>();

	public void addEnableUndoActionListener(EnableUndoActionListener listener) {
		enableUndoActionListeners.add(listener);
	}

	public void removeEnableUndoActionListener(EnableUndoActionListener listener) {
		enableUndoActionListeners.remove(listener);
	}

	private void fireUndoEnabled(boolean isEnable) {
		for(EnableUndoActionListener listener: enableUndoActionListeners) {
			EnableUndoActionEvent event = new EnableUndoActionEvent(listener);
			event.setEnable(isEnable);
			listener.undoEnabled(event);
		}
	}
}
