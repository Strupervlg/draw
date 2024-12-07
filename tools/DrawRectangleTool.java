package tools;

import controller.DrawingController;
import shapes.Rectangle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class DrawRectangleTool extends DrawFillableShapeTool {

    DrawRectangleTool(DrawingController controller) {
        super(controller);
        this.setIcon(new ImageIcon("img/rectangle.png"));
        this.setToolTipText("Draw squares and rectangles");
        this.addActionListener(e -> {
            controller.clearSelection();
            this.fireFillEnabled(true);
            this.fireFontEnabled(false);
        });
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        controller.resizeShape(shape, e.getPoint());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point position = e.getPoint();
        shape = shapeFactory.createRectangle(position, controller.getCurrentFill(), controller.getCurrentColor());
        controller.addShape(shape);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        shape = null;
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    DrawRectangleTool(Rectangle rectangle) {
        super(rectangle);
    }

    @Override
    public void drawFilled(Graphics g) {
        g.fillRect(shape.getPosition().x, shape.getPosition().y, shape.getSize().x, shape.getSize().y);
    }

    @Override
    public void drawNonFilled(Graphics g) {
        g.drawRect(shape.getPosition().x, shape.getPosition().y, shape.getSize().x, shape.getSize().y);
    }
}
