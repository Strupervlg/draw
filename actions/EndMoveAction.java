package actions;

public class EndMoveAction implements DrawAction, MergeAction {
    @Override
    public void execute() {}

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void redo() {}

    @Override
    public void undo() {}

    @Override
    public boolean merge(MergeAction other) {
        if (other instanceof MoveAction otherCommand) {
            otherCommand.setNonMerged();
            return true;
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
