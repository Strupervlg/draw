package events;

public class EnableUndoActionEvent extends EnableActionEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public EnableUndoActionEvent(Object source) {
        super(source);
    }
}
