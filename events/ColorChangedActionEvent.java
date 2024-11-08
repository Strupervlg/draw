package events;

import java.awt.*;
import java.util.EventObject;

public class ColorChangedActionEvent extends EventObject {

    private Color color;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ColorChangedActionEvent(Object source) {
        super(source);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
