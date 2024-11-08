package events;

import java.util.EventListener;

public interface FillChangedActionListener extends EventListener {
    void fillChanged(FillChangedActionEvent event);
}
