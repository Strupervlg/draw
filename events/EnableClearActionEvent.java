package events;

public class EnableClearActionEvent extends EnableActionEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public EnableClearActionEvent(Object source) {
        super(source);
    }
}
