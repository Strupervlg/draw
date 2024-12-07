package events;

import java.util.EventListener;

public interface FontChangedActionListener extends EventListener {
    void fontChanged(FontChangedActionEvent event);
}
