package actions;

import java.awt.Color;

import shapes.Shape;

/**
 * DeleteAction implements a single undoable action where the color of a Shape
 * are changed.
 */

public class ColorAction extends ShapeAction implements DrawAction {

	private Color oldColor;
	private Color newColor;

	/**
	 * Creates an ColorAction that changes the color of a given Shape.
	 * 
	 * @param shape
	 *            the shape to be modified.
	 * @param newColor
	 *            the new color for the shape.
	 */
	public ColorAction(Shape shape, Color newColor) {
		super(shape);
		this.oldColor = shape.getColor();
		this.newColor = newColor;
	}

	public void execute() {
		shape.setColor(newColor);
	}

	public String getDescription() {
		return null;
	}

	public void redo() {
		this.execute();
	}

	public void undo() {
		shape.setColor(oldColor);
	}

}
