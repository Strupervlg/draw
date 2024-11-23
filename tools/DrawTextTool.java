package tools;

import controller.DrawingController;
import shapes.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class DrawTextTool extends DrawShapeTool {

    public DrawTextTool(DrawingController controller) {
        super(controller, new ImageIcon("img/text.png"), "Create text");
    }

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        Point position = e.getPoint();
        try {
            String text = JOptionPane.showInputDialog("Text to be inserted:");
            if (text == null || text.isEmpty()) {
                throw new IllegalArgumentException("Empty text");
            }
            shape = new Text(position.x, position.y, controller.getToolBox().getFontSize(), text);
            controller.colorShape(shape, controller.getToolBox().getColor());
            controller.addShape(shape);
            shape = null;
        }
        catch (IllegalArgumentException exception) {
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public DrawTextTool(Text text) {
        super(text);
    }

    @Override
    public void drawShape(Graphics g) {
        g.setFont(((Text)shape).getFont());
        int w = g.getFontMetrics().stringWidth(((Text)shape).getText());
        shape.setPoint2(new Point(shape.getPoint1().x + w, shape.getPoint1().y - ((Text)shape).getFont().getSize()));
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.drawString(((Text)shape).getText(), shape.getPoint1().x, shape.getPoint1().y);
    }
}
