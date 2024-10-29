package actions;

import shapes.Selection;

/**
 * DeleteAction implements a single undoable action where all Shapes in a given
 * Selection are added to a Drawing.
 */
public class DeleteAction implements DrawAction {
	Selection selection;

	/**
	 * Creates an DeleteAction that removes all shapes in the given Selection
	 * from the given Drawing.
	 *
	 * @param selection
	 *            the shape to be added.
	 */
	public DeleteAction(Selection selection) {
		// The selection need to be hard-copied because the selection behind the
		// reference will change while editing the drawing.
		this.selection = selection.clone();
	}

	public void execute() {
		selection.removeShapesFromDrawing();
	}

	public String getDescription() {
		return null;
	}

	public void redo() {
		execute();
	}

	public void undo() {
		selection.insertShapesFromDrawing();
	}

}
