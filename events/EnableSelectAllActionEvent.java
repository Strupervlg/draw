package events;

public class EnableSelectAllActionEvent extends EnableActionEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public EnableSelectAllActionEvent(Object source) {
        super(source);
    }
}
