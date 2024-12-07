package tools;

import controller.DrawingController;
import events.EnableFillActionEvent;
import events.EnableFillActionListener;
import events.FillChangedActionEvent;
import events.FillChangedActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

public class FillTool extends JPanel implements EnableFillActionListener, FillChangedActionListener {

    private JCheckBox fillCheckBox;

    public FillTool(DrawingController controller) {
        fillCheckBox = new JCheckBox();
        fillCheckBox.addItemListener(e -> {
            this.fireFillChanged(e.getStateChange() == ItemEvent.SELECTED);
        });
        fillCheckBox.addActionListener(e -> {
            controller.toggleFilled(this.fillCheckBox.isSelected());
        });
        controller.addFillChangedActionListener(this);
        this.addFillChangedActionListener(controller);
        fillCheckBox.setSelected(false);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createRigidArea(new Dimension(10, 10)));
        this.add(new JLabel("Fill"));
        this.add(fillCheckBox);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.fillCheckBox.setEnabled(enabled);
    }

    public void setFill(boolean fill) {
        fillCheckBox.setSelected(fill);
    }

    @Override
    public void fillEnabled(EnableFillActionEvent event) {
        this.setEnabled(event.isEnable());
    }

    @Override
    public void fillChanged(FillChangedActionEvent event) {
        this.setFill(event.getFill());
    }


    // ------------------------------- EVENTS ---------------------------------

    private ArrayList<FillChangedActionListener> fillChangedActionListeners = new ArrayList<>();

    public void addFillChangedActionListener(FillChangedActionListener listener) {
        fillChangedActionListeners.add(listener);
    }

    public void removeFillChangedActionListener(FillChangedActionListener listener) {
        fillChangedActionListeners.remove(listener);
    }

    private void fireFillChanged(boolean fill) {
        for(FillChangedActionListener listener: fillChangedActionListeners) {
            FillChangedActionEvent event = new FillChangedActionEvent(listener);
            event.setFill(fill);
            listener.fillChanged(event);
        }
    }
}
