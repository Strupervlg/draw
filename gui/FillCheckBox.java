package gui;

import events.FillChangedActionEvent;
import events.FillChangedActionListener;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class FillCheckBox extends JCheckBox implements ItemListener, FillChangedActionListener {
    private boolean fill;

    FillCheckBox() {
        addItemListener(this);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.DESELECTED) {
            setFill(false);
        }
        else {
            setFill(true);
        }
    }

    public boolean isFill() {
        return fill;
    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }

    @Override
    public void fillChanged(FillChangedActionEvent event) {
        this.setSelected(event.getFill());
    }
}
