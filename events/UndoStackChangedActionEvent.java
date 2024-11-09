package events;

import java.util.EventObject;

public class UndoStackChangedActionEvent extends EventObject {

    private boolean canUndo;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public UndoStackChangedActionEvent(Object source) {
        super(source);
    }

    public boolean canUndo() {
        return canUndo;
    }

    public void setCanUndo(boolean canUndo) {
        this.canUndo = canUndo;
    }
}
