package events;

public class EnableRedoActionEvent extends EnableActionEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public EnableRedoActionEvent(Object source) {
        super(source);
    }
}
