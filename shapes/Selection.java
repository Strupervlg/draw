package shapes;

import events.*;

import java.util.ArrayList;
import java.util.Iterator;

public class Selection implements Iterable<Shape> {

	private Drawing drawing;

	private ArrayList<Shape> selected;

	private boolean isClone = false;

	public Selection(Drawing drawing) {
		this.drawing = drawing;
		selected = new ArrayList<Shape>(0);
	}

	public Selection(ArrayList<Shape> list) {
		selected = list;
	}

	public void add(Shape s) {
		if (selected.contains(s)) {
			return;
		}
		if(this.drawing.getSelection().isEmpty()) {
			this.fireSelectedShape(s);
		} else {
			this.fireSelectedManyShapes();
		}
		selected.add(s);
		s.setSelected(true);
		this.fireStateChanged();
	}

	@SuppressWarnings("unchecked")
	public Selection clone() {
		ArrayList<Shape> clone = (ArrayList<Shape>) selected.clone();
		Selection cloneSelection = new Selection(clone);
		cloneSelection.isClone = true;
		cloneSelection.drawing = drawing;
		cloneSelection.clearSelectedShapesActionListeners = clearSelectedShapesActionListeners;
		return cloneSelection;
	}

	public boolean contains(Shape s) {
		return selected.contains(s);
	}

	public void empty() {
		for (Shape s : selected) {
			s.setSelected(false);
			s.clearAllRepaintActionListener();
		}
		selected.clear();
		this.fireStateChanged();
		this.fireClearSelectedShapes();
	}

	public boolean isEmpty() {
		return selected.isEmpty();
	}

	public Iterator<Shape> iterator() {
		return selected.iterator();
	}

	public int nShapes() {
		return selected.size();
	}

	public String toString() {
		String str;
		str = "Selection contents:\n";
		for (Shape s : selected) {
			str += s.toString() + "\n";
		}
		str += "\n";
		return str;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Selection otherSelection) {
			if(this.selected.size() == otherSelection.selected.size()) {
				boolean result = true;
				for(int i = 0; i < this.selected.size(); i++) {
					result &= this.selected.get(i).equals(otherSelection.selected.get(i));
				}
				return result;
			} else {
				return false;
			}
		}
		return super.equals(obj);
	}

	public void move(int x, int y) {
		for (Shape s : selected) {
			s.move(x, y);
		}
	}

	public void toggleFillShapes() {
		for (Shape s : selected) {
			if (s instanceof FillableShape) {
				FillableShape fs = (FillableShape) s;
				fs.setFilled(!(fs).getFilled());
			}
		}
	}

	public void removeShapesFromDrawing() {
		for (Shape s : selected) {
			drawing.removeShape(s);
		}
		this.fireClearSelectedShapes();
	}

	public void insertShapesFromDrawing() {
		for (Shape s : selected) {
			drawing.insertShape(s);
		}
	}

	public boolean isClone() {
		return isClone;
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

	private ArrayList<ClearSelectedShapesActionListener> clearSelectedShapesActionListeners = new ArrayList<>();

	public void addClearSelectedShapesActionListener(ClearSelectedShapesActionListener listener) {
		clearSelectedShapesActionListeners.add(listener);
	}

	public void removeClearSelectedShapesActionListener(ClearSelectedShapesActionListener listener) {
		clearSelectedShapesActionListeners.remove(listener);
	}

	private void fireClearSelectedShapes() {
		for(ClearSelectedShapesActionListener listener: clearSelectedShapesActionListeners) {
			ClearSelectedShapesActionEvent event = new ClearSelectedShapesActionEvent(listener);
			listener.clearSelectedShapes(event);
		}
	}

	private ArrayList<SelectShapeActionListener> selectShapeListListener = new ArrayList<>();

	public void addSelectShapeActionListener(SelectShapeActionListener listener) {
		selectShapeListListener.add(listener);
	}

	public void removeSelectShapeActionListener(SelectShapeActionListener listener) {
		selectShapeListListener.remove(listener);
	}

	private void fireSelectedShape(Shape shape) {
		for(SelectShapeActionListener listener: selectShapeListListener) {
			SelectShapeActionEvent event = new SelectShapeActionEvent(listener);
			event.setShape(shape);
			listener.selectedShape(event);
		}
	}

	private ArrayList<SelectedManyShapesActionListener> selectedManyShapesActionListeners = new ArrayList<>();

	public void addSelectedManyShapesActionListener(SelectedManyShapesActionListener listener) {
		selectedManyShapesActionListeners.add(listener);
	}

	public void removeSelectedManyShapesActionListener(SelectedManyShapesActionListener listener) {
		selectedManyShapesActionListeners.remove(listener);
	}

	private void fireSelectedManyShapes() {
		for(SelectedManyShapesActionListener listener: selectedManyShapesActionListeners) {
			SelectedManyShapesActionEvent event = new SelectedManyShapesActionEvent(listener);
			listener.selectedManyShapes(event);
		}
	}
}
