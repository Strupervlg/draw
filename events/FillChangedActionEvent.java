package events;

import java.util.EventObject;

public class FillChangedActionEvent extends EventObject {

    private boolean fill;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public FillChangedActionEvent(Object source) {
        super(source);
    }

    public boolean getFill() {
        return fill;
    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }
}
