package actions;

import shapes.Drawing;
import shapes.Shape;

/**
 * AddAction implements a single undoable action where a Shape is added to a
 * Drawing.
 */
public class AddAction extends ShapeAction implements DrawAction, MergeAction {

	private Drawing drawing;

	/**
	 * Creates an AddAction that adds the given Shape to the given Drawing.
	 * 
	 * @param drawing
	 *            the drawing into which the shape should be added.
	 * @param shape
	 *            the shape to be added.
	 */
	public AddAction(Drawing drawing, Shape shape) {
		super(shape);
		this.drawing = drawing;
	}

	public void execute() {
		drawing.insertShape(shape);
	}

	public String getDescription() {
		return null;
	}

	public void redo() {
		this.execute();
	}

	public void undo() {
		drawing.removeShape(shape);
	}

	@Override
	public boolean merge(MergeAction other) {
		if (other instanceof ResizeAction otherCommand) {
			if (this.shape.equals(otherCommand.shape)) {
				this.shape.setPoint2(otherCommand.getNewPoint2());
				return true;
			}
		}
		return false;
	}

	@Override
	public void setNonMerged() {}

	@Override
	public boolean canMerge() {
		return true;
	}
}
