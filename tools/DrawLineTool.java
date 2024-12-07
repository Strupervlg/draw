package tools;

import controller.DrawingController;
import shapes.Line;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class DrawLineTool extends DrawShapeTool {

    DrawLineTool(DrawingController controller) {
        super(controller);
        this.setIcon(new ImageIcon("img/line.png"));
        this.setToolTipText("Draw lines");
        this.addActionListener(e -> {
            controller.clearSelection();
            this.fireFillEnabled(false);
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
        shape = shapeFactory.createLine(position, controller.getCurrentColor());
        controller.addShape(shape);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        shape = null;
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    DrawLineTool(Line line) {
        super(line);
    }

    @Override
    public void drawShape(Graphics g) {
        ((Graphics2D) g).setStroke(new BasicStroke((float) strokeWidth));

        g.drawLine(shape.getPoint1().x, shape.getPoint1().y, shape.getPoint2().x, shape.getPoint2().y);
    }
}
