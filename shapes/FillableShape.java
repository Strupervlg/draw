package shapes;

import events.FillChangedActionEvent;
import events.FillChangedActionListener;

import java.awt.Point;
import java.util.ArrayList;

public abstract class FillableShape extends Shape {

	protected boolean filled;

	FillableShape(int xpos, int ypos) {
		super(new Point(xpos, ypos));
		filled = false;
	}

	@Override
	public void setSelected(boolean b) {
		if(!b) {
			this.clearFillChangedActionListener();
		}
		super.setSelected(b);
	}

	public boolean getFilled() {
		return filled;
	}

	public void setFilled(boolean f) {
		filled = f;
		this.fireFillChanged(f);
		this.fireStateChanged();
	}

	public String toString() {
		return super.toString() + ";" + (filled ? 1 : 0);
	}


	// ------------------------------- EVENTS ---------------------------------

	private ArrayList<FillChangedActionListener> fillChangedActionListeners = new ArrayList<>();

	public void addFillChangedActionListener(FillChangedActionListener listener) {
		fillChangedActionListeners.add(listener);
	}

	public void removeFillChangedActionListener(FillChangedActionListener listener) {
		fillChangedActionListeners.remove(listener);
	}

	public void clearFillChangedActionListener() {
		fillChangedActionListeners.clear();
	}

	private void fireFillChanged(boolean fill) {
		for(FillChangedActionListener listener: fillChangedActionListeners) {
			FillChangedActionEvent event = new FillChangedActionEvent(listener);
			event.setFill(fill);
			listener.fillChanged(event);
		}
	}
}
