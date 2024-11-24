package actions;

import java.awt.Point;

import shapes.Selection;

/**
 * MoveAction implements a single undoable action where all the Shapes in a
 * given Selection are moved.
 */
public class MoveAction extends SelectionAction implements DrawAction, MergeAction {

	Point movement;

	boolean isMerged;

	/**
	 * Creates a MoveAction that moves all Shapes in the given Selection in the
	 * direction given by the point. The movement is relative to the shapes
	 * original position.
	 * 
	 * @param selected
	 *            a selection which contains the shapes to be moved
	 * @param m
	 *            the amount the shapes should be moved, relative to the
	 *            original position
	 */
	public MoveAction(Selection selected, Point m) {
		super(selected);
		this.movement = m;
		this.isMerged = true;
	}

	public void execute() {
		selected.move(movement.x, movement.y);
	}

	public String getDescription() {
		return null;
	}

	public void redo() {
		execute();
	}

	public void undo() {
		selected.move(-movement.x, -movement.y);
	}

	public boolean merge(MergeAction other) {
		if (other instanceof MoveAction otherCommand && otherCommand.isMerged && this.isMerged) {
            if (this.selected.equals(otherCommand.selected)) {
				this.movement.translate(otherCommand.movement.x, otherCommand.movement.y);
				return true;
			}
		}
		if (other instanceof EndMoveAction) {
			this.setNonMerged();
			return true;
		}
		return false;
	}

	@Override
	public void setNonMerged() {
		isMerged= false;
	}

	@Override
	public boolean canMerge() {
		return this.isMerged;
	}

}
