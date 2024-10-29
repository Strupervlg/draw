package events;

import java.util.EventObject;

public class RepaintActionEvent extends EventObject {

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public RepaintActionEvent(Object source) {
        super(source);
    }
}
