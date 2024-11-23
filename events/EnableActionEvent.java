package events;

import java.util.EventObject;

public abstract class EnableActionEvent extends EventObject {

    private boolean isEnable;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public EnableActionEvent(Object source) {
        super(source);
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }
}
