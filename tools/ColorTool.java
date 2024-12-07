package tools;

import controller.DrawingController;
import events.ColorChangedActionEvent;
import events.ColorChangedActionListener;
import events.CurrentColorChangedActionListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ColorTool extends JPanel implements ColorChangedActionListener {
    private JButton colorbutton;

    public ColorTool(DrawingController controller) {
        colorbutton = new JButton();
        colorbutton.setPreferredSize(new Dimension(44, 44));
        colorbutton.setMaximumSize(new Dimension(44, 44));
        colorbutton.setToolTipText("Click to change drawing color");
        colorbutton.setOpaque(true);
        colorbutton.setBorderPainted(false);
        colorbutton.setFocusPainted(false);
        this.addColorChangedListener(controller);
        this.addCurrentColorChangedActionListener(controller);
        controller.addColorChangedListener(this);
        colorbutton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(null, "Choose a color", colorbutton.getBackground());
            this.setSelectedColor(newColor);
        });

        this.setSelectedColor(Color.BLACK);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createRigidArea(new Dimension(10, 10)));
        add(new JLabel("Color"));
        add(Box.createRigidArea(new Dimension(10, 10)));
        add(colorbutton);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.colorbutton.setEnabled(enabled);
    }

    @Override
    public void colorChanged(ColorChangedActionEvent event) {
        this.setSelectedColor(event.getColor(), false);
    }

    public Color getSelectedColor() {
        return this.colorbutton.getBackground();
    }

    public void setSelectedColor(Color newColor) {
        setSelectedColor(newColor, true);
    }

    public void setSelectedColor(Color newColor, boolean notify) {
        if (newColor == null) return;

        this.colorbutton.setBackground(newColor);
        this.colorbutton.setForeground(newColor);

        this.colorbutton.repaint();
        fireCurrentColorChanged();
        if (notify) {
            fireColorChanged();
        }
    }


    // ------------------------------- EVENTS ---------------------------------

    private final ArrayList<ColorChangedActionListener> listeners = new ArrayList<>();

    public void addColorChangedListener(ColorChangedActionListener listener) {
        listeners.add(listener);
    }

    public void removeColorChangedActionListener(ColorChangedActionListener listener) {
        listeners.remove(listener);
    }

    private void fireColorChanged() {
        for(ColorChangedActionListener listener: listeners) {
            ColorChangedActionEvent event = new ColorChangedActionEvent(listener);
            event.setColor(this.getSelectedColor());
            listener.colorChanged(event);
        }
    }

    private final ArrayList<CurrentColorChangedActionListener> currentColorChangedActionListeners = new ArrayList<>();

    public void addCurrentColorChangedActionListener(CurrentColorChangedActionListener listener) {
        currentColorChangedActionListeners.add(listener);
    }

    public void removeCurrentColorChangedActionListener(CurrentColorChangedActionListener listener) {
        currentColorChangedActionListeners.remove(listener);
    }

    private void fireCurrentColorChanged() {
        for(CurrentColorChangedActionListener listener: currentColorChangedActionListeners) {
            ColorChangedActionEvent event = new ColorChangedActionEvent(listener);
            event.setColor(this.getSelectedColor());
            listener.currentColorChanged(event);
        }
    }
}
