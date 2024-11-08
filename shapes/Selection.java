package shapes;

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
		if (!selected.contains(s)) {
			selected.add(s);
			s.setSelected(true);
		}
	}

	@SuppressWarnings("unchecked")
	public Selection clone() {
		ArrayList<Shape> clone = (ArrayList<Shape>) selected.clone();
		Selection cloneSelection = new Selection(clone);
		cloneSelection.isClone = true;
		return cloneSelection;
	}

	public boolean contains(Shape s) {
		return selected.contains(s);
	}

	public void empty() {
		for (Shape s : selected) {
			s.setSelected(false);
		}
		selected.clear();
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

	public void removeShapesFromDrawing() {
		for (Shape s : selected) {
			drawing.removeShape(s);
		}
	}

	public void insertShapesFromDrawing() {
		for (Shape s : selected) {
			drawing.insertShape(s);
		}
	}

	public boolean isClone() {
		return isClone;
	}
}
