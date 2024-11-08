package tools;

import controller.DrawingController;
import events.SelectShapeActionEvent;
import events.SelectShapeActionListener;
import shapes.Shape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class SelectTool extends Tool {

    private DrawingController controller;

    protected Point lastPos;

    private boolean isDragged = false;

    public SelectTool(DrawingController controller) {
        this.imageIcon = new ImageIcon("img/cursor.png");
        this.tipText = "Select and move shapes";
        this.controller = controller;
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

        if ((tmp != null) && (!controller.getDrawing().getSelection().contains(tmp))) {

            // empty the selection before selecting a new shape if shift is
            // not down
            if(controller.getDrawing().getSelection().isEmpty()) {
                this.fireSelectedShape(tmp);
            } else {
                //TODO испускать событие на множественное выделение,
                // после чего буду блокироваться fill size и color в toolBox
            }
            controller.addSelectionShape(tmp);
        }
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

    @Override
    public boolean isFillable() {
        return true;
    }


    // ------------------------------- EVENTS ---------------------------------
    private ArrayList<SelectShapeActionListener> selectShapeListListener = new ArrayList<>();

    public void addSelectShapeActionListener(SelectShapeActionListener listener) {
        selectShapeListListener.add(listener);
    }

    public void removeSelectShapeActionListener(SelectShapeActionListener listener) {
        selectShapeListListener.remove(listener);
    }

    private void fireSelectedShape(Shape shape) {
        for(SelectShapeActionListener listener: selectShapeListListener) {
            SelectShapeActionEvent event = new SelectShapeActionEvent(listener);
            event.setShape(shape);
            listener.selectedShape(event);
        }
    }
}
