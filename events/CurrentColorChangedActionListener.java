package events;

import java.util.EventListener;

public interface CurrentColorChangedActionListener extends EventListener {
    void currentColorChanged(ColorChangedActionEvent event);
}
