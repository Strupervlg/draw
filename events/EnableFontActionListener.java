package events;

import java.util.EventListener;

public interface EnableFontActionListener extends EventListener {
    void fontEnabled(EnableFontActionEvent event);
}
