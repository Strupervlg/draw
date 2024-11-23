package tools;

import controller.DrawingController;

import javax.swing.*;
import java.awt.event.MouseEvent;

public abstract class Tool {

    protected DrawingController controller;

    private ImageIcon imageIcon;

    private String tipText;

    public Tool(){}

    public Tool(DrawingController controller, ImageIcon imageIcon, String tipText) {
        this.controller = controller;
        this.imageIcon = imageIcon;
        this.tipText = tipText;
    }

    public abstract void mouseDragged(MouseEvent e);

    public abstract void mousePressed(MouseEvent e);

    public abstract void mouseReleased(MouseEvent e);

    public abstract void mouseMoved(MouseEvent e);

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public String getTipText() {
        return tipText;
    }
}
