package events;

public class EnableDeleteActionEvent extends EnableActionEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public EnableDeleteActionEvent(Object source) {
        super(source);
    }
}
