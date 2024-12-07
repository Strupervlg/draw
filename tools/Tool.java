package tools;

import controller.DrawingController;
import events.EnableFillActionEvent;
import events.EnableFillActionListener;
import events.EnableFontActionListener;
import events.EnableFontActionEvent;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public abstract class Tool extends JToggleButton {

    protected DrawingController controller;

    Tool(){}

    Tool(DrawingController controller) {
        this.controller = controller;
    }

    public abstract void mouseDragged(MouseEvent e);

    public abstract void mousePressed(MouseEvent e);

    public abstract void mouseReleased(MouseEvent e);

    public abstract void mouseMoved(MouseEvent e);

    private ArrayList<EnableFillActionListener> enableFillActionListeners = new ArrayList<>();

    public void addEnableFillActionListener(EnableFillActionListener listener) {
        enableFillActionListeners.add(listener);
    }

    public void removeFillActionListener(EnableFillActionListener listener) {
        enableFillActionListeners.remove(listener);
    }

    protected void fireFillEnabled(boolean isEnable) {
        for(EnableFillActionListener listener: enableFillActionListeners) {
            EnableFillActionEvent event = new EnableFillActionEvent(listener);
            event.setEnable(isEnable);
            listener.fillEnabled(event);
        }
    }

    private ArrayList<EnableFontActionListener> enableFontActionListeners = new ArrayList<>();

    public void addEnableFontActionListener(EnableFontActionListener listener) {
        enableFontActionListeners.add(listener);
    }

    public void removeEnableFontActionListener(EnableFontActionListener listener) {
        enableFontActionListeners.remove(listener);
    }

    protected void fireFontEnabled(boolean isEnable) {
        for(EnableFontActionListener listener: enableFontActionListeners) {
            EnableFontActionEvent event = new EnableFontActionEvent(listener);
            event.setEnable(isEnable);
            listener.fontEnabled(event);
        }
    }
}
