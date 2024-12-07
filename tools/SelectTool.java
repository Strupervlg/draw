package tools;

import controller.DrawingController;
import events.*;
import shapes.FillableShape;
import shapes.Shape;
import shapes.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class SelectTool extends Tool {

    protected Point lastPos;

    private boolean isDragged = false;

    SelectTool(DrawingController controller) {
        super(controller);
        this.setIcon(new ImageIcon("img/cursor.png"));
        this.setToolTipText("Select and move shapes");
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point position = e.getPoint();
        controller.moveSelectedShapes(new Point(position.x - lastPos.x, position.y - lastPos.y));
        lastPos = position;
        isDragged = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point position = e.getPoint();
        Shape tmp = controller.getDrawing().getShapeAt(position);

        if (((e.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) == 0)
                && !controller.getDrawing().getSelection().contains(tmp)) {
            controller.clearSelection();
        }

        if(tmp != null) {
            fireFillEnabled(tmp instanceof FillableShape);
            fireFontEnabled(false);
            fireColorChanged(tmp.getColor());
            fireFillChanged(tmp instanceof FillableShape && ((FillableShape) tmp).getFilled());
            if (tmp instanceof Text) {
                this.fireFontChanged(((Text) tmp).getFont().getSize());
            }
        }

        controller.addSelectionShape(tmp);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(isDragged) {
            controller.endMoveSelectedShapes();
            isDragged = false;
        }
    }

    public void mouseMoved(MouseEvent e) {
        lastPos = e.getPoint();
    }


    // ------------------------------- EVENTS ---------------------------------

    private final ArrayList<ColorChangedActionListener> listeners = new ArrayList<>();

    public void addColorChangedListener(ColorChangedActionListener listener) {
        listeners.add(listener);
    }

    public void removeRepaintActionListener(ColorChangedActionListener listener) {
        listeners.remove(listener);
    }

    private void fireColorChanged(Color color) {
        for(ColorChangedActionListener listener: listeners) {
            ColorChangedActionEvent event = new ColorChangedActionEvent(listener);
            event.setColor(color);
            listener.colorChanged(event);
        }
    }


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


    private ArrayList<FontChangedActionListener> fontChangedActionListeners = new ArrayList<>();

    public void addFontChangedActionListener(FontChangedActionListener listener) {
        fontChangedActionListeners.add(listener);
    }

    public void removeFontChangedActionListener(FontChangedActionListener listener) {
        fontChangedActionListeners.remove(listener);
    }

    private void fireFontChanged(int fontSize) {
        for(FontChangedActionListener listener: fontChangedActionListeners) {
            FontChangedActionEvent event = new FontChangedActionEvent(listener);
            event.setFontSize(fontSize);
            listener.fontChanged(event);
        }
    }
}
