package events;

public class EnableFontActionEvent extends EnableActionEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public EnableFontActionEvent(Object source) {
        super(source);
    }
}
