package actions;

import shapes.Selection;

/**
 * FillAction implements a undoable action where the fill status of all the
 * Shapes in a given Selection are toggled.
 */
public class FillAction extends SelectionAction implements DrawAction {

	/**
	 * Creates a FillAction that filps the fill status of all FillableShape
	 * instances in the given selection.
	 * 
	 * @param selected
	 *            a selection which contains the shapes to be modified
	 */
	public FillAction(Selection selected) {
		super(selected);
	}

	public void execute() {
		selected.toggleFillShapes();
	}

	public String getDescription() {
		return null;
	}

	public void redo() {
		execute();
	}

	public void undo() {
		execute();
	}

}
