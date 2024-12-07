package events;

public class EnableFillActionEvent extends EnableActionEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public EnableFillActionEvent(Object source) {
        super(source);
    }
}
