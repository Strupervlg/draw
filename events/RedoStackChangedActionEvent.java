package events;

import java.util.EventObject;

public class RedoStackChangedActionEvent extends EventObject {

    private boolean canRedo;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public RedoStackChangedActionEvent(Object source) {
        super(source);
    }

    public boolean canRedo() {
        return canRedo;
    }

    public void setCanRedo(boolean canRedo) {
        this.canRedo = canRedo;
    }
}
