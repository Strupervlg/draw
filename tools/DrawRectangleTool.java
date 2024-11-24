package tools;

import controller.DrawingController;
import shapes.Rectangle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class DrawRectangleTool extends DrawFillableShapeTool implements ColorableTool {

    public DrawRectangleTool(DrawingController controller) {
        super(controller, new ImageIcon("img/rectangle.png"), "Draw squares and rectangles");
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        controller.resizeShape(shape, e.getPoint());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point position = e.getPoint();
        shape = shapeFactory.createRectangle(position, controller.getToolBox().getFill(), controller.getToolBox().getColor());
        controller.addShape(shape);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        shape = null;
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public DrawRectangleTool(Rectangle rectangle) {
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
