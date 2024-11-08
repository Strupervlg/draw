package gui;

import events.ColorChangedActionEvent;
import events.ColorChangedActionListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ColorButton extends JButton {

    private Color currentColor;

    public ColorButton(Color color) {
        super();
        setPreferredSize(new Dimension(44, 44));
        setMaximumSize(new Dimension(44, 44));
        setBackground(color);
        setForeground(color);
        setSelectedColor(color);
        setToolTipText("Click to change drawing color");
        setOpaque(true);
        setBorderPainted(false);
        setFocusPainted(false);

        addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(null, "Choose a color", currentColor);
            setSelectedColor(newColor);
        });
    }

    public Color getSelectedColor() {
        return currentColor;
    }

    public void setSelectedColor(Color newColor) {
        setSelectedColor(newColor, true);
    }

    public void setSelectedColor(Color newColor, boolean notify) {

        if (newColor == null) return;

        currentColor = newColor;
        setBackground(newColor);
        setForeground(newColor);

        repaint();

        if (notify) {
            fireColorChanged();
        }
    }

    private final ArrayList<ColorChangedActionListener> listeners = new ArrayList<>();

    public void addColorChangedListener(ColorChangedActionListener listener) {
        listeners.add(listener);
    }

    public void removeRepaintActionListener(ColorChangedActionListener listener) {
        listeners.remove(listener);
    }

    private void fireColorChanged() {
        for(ColorChangedActionListener listener: listeners) {
            ColorChangedActionEvent event = new ColorChangedActionEvent(listener);
            event.setColor(this.getSelectedColor());
            listener.colorChanged(event);
        }
    }
}
