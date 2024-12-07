package tools;

import javax.swing.*;
import java.util.Enumeration;

public class ButtonGroupTool extends ButtonGroup {

    public Tool getSelected() {
        Enumeration<AbstractButton> buttons = this.getElements();
        while (buttons.hasMoreElements()) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return  (Tool) button;
            }
        }
        return null;
    }

    public AbstractButton getFirstButton() {
        Enumeration<AbstractButton> buttons = this.getElements();
        if(buttons.hasMoreElements()) {
            return buttons.nextElement();
        }
        return null;
    }

    public <T extends Tool> T getTool(Class<T> toolClass) {
        Enumeration<AbstractButton> buttons = this.getElements();
        while (buttons.hasMoreElements()) {
            AbstractButton button = buttons.nextElement();
            if (toolClass.isInstance(button)) {
                return toolClass.cast(button);
            }
        }
        return null;
    }
}
