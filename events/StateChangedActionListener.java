package events;

import java.util.EventListener;

public interface StateChangedActionListener extends EventListener {
    void stateChanged(StateChangedActionEvent event);

}
