package events;

import java.util.EventListener;

public interface EnableFillActionListener extends EventListener {
    void fillEnabled(EnableFillActionEvent event);
}
