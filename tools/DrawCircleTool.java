package tools;

import controller.DrawingController;
import shapes.Circle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class DrawCircleTool extends DrawFillableShapeTool {

    DrawCircleTool(DrawingController controller) {
        super(controller);
        this.setIcon(new ImageIcon("img/circle.png"));
        this.setToolTipText("Draw circles and ellipses");
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
        shape = shapeFactory.createCircle(position, controller.getCurrentFill(), controller.getCurrentColor());
        controller.addShape(shape);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        shape = null;
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    DrawCircleTool(Circle circle) {
        super(circle);
    }

    @Override
    public void drawFilled(Graphics g) {
        g.fillOval(shape.getPosition().x, shape.getPosition().y, shape.getSize().x, shape.getSize().y);
    }

    @Override
    public void drawNonFilled(Graphics g) {
        g.drawOval(shape.getPosition().x, shape.getPosition().y, shape.getSize().x, shape.getSize().y);
    }
}
