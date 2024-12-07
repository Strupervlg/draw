package events;

import java.util.EventObject;

public class FontChangedActionEvent extends EventObject {

    private int fontSize;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public FontChangedActionEvent(Object source) {
        super(source);
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
}
