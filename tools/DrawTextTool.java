package tools;

import controller.DrawingController;
import shapes.ReadOnlyText;
import shapes.Shape;
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
            controller.addShape((Shape) shape);
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
        g.setFont(((ReadOnlyText)shape).getFont());
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.drawString(((ReadOnlyText)shape).getText(), shape.getPoint1().x, shape.getPoint1().y);
    }
}
