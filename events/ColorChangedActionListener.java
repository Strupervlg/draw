package events;

import java.util.EventListener;

public interface ColorChangedActionListener extends EventListener {
    void colorChanged(ColorChangedActionEvent event);

}
