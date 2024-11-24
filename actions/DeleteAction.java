package actions;

import shapes.Selection;

/**
 * DeleteAction implements a single undoable action where all Shapes in a given
 * Selection are added to a Drawing.
 */
public class DeleteAction extends SelectionAction implements DrawAction {

	/**
	 * Creates an DeleteAction that removes all shapes in the given Selection
	 * from the given Drawing.
	 *
	 * @param selection
	 *            the shape to be added.
	 */
	public DeleteAction(Selection selection) {
		super(selection);
	}

	public void execute() {
		selected.removeShapesFromDrawing();
	}

	public String getDescription() {
		return null;
	}

	public void redo() {
		execute();
	}

	public void undo() {
		selected.insertShapesFromDrawing();
	}

}
