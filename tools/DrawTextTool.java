package tools;

import controller.DrawingController;
import shapes.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class DrawTextTool extends DrawShapeTool {

    DrawTextTool(DrawingController controller) {
        super(controller);
        this.setIcon(new ImageIcon("img/text.png"));
        this.setToolTipText("Create text");
        this.addActionListener(e -> {
            controller.clearSelection();
            this.fireFillEnabled(false);
            this.fireFontEnabled(true);
        });
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
            shape = shapeFactory.createText(position, controller.getCurrentFontSize(), text, controller.getCurrentColor());
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

    DrawTextTool(Text text) {
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
