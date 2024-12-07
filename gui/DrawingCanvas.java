package gui;

import events.*;
import shapes.Drawing;
import shapes.Shape;
import tools.DrawShapeTool;
import tools.ToolFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DrawingCanvas extends JPanel implements StateChangedActionListener,
        ShapeIsDeletedActionListener, ShapeIsInsertedActionListener {

    private static final long serialVersionUID = 0;

    private ArrayList<DrawShapeTool> graphicShapes;

    private ToolFactory toolFactory;

    public DrawingCanvas(Drawing drawing, ToolFactory toolFactory) {
        graphicShapes = new ArrayList<DrawShapeTool>(0);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                toolFactory.getButtons().getSelected().mousePressed(e);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                toolFactory.getButtons().getSelected().mouseReleased(e);
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                toolFactory.getButtons().getSelected().mouseMoved(e);
            }
            @Override
            public void mouseDragged(MouseEvent e){
                super.mouseDragged(e);
                toolFactory.getButtons().getSelected().mouseDragged(e);
            }
        });

        this.toolFactory = toolFactory;

        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.WHITE);
        setPreferredSize(drawing.getSize());
    }

    public void addShape(Shape shape) {
        graphicShapes.add(toolFactory.create(shape));
    }

    public void removeShape(Shape shape) {
        graphicShapes.remove(toolFactory.getGraphicShape(shape));
        toolFactory.removeGraphicShape(shape);
    }

    public BufferedImage getImage() {
        BufferedImage bi = new BufferedImage(getPreferredSize().width,
                getPreferredSize().height, BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.createGraphics();
        this.print(g);
        return bi;
    }

    public void drawShapes(Graphics g) {
        for (DrawShapeTool graphicShape : graphicShapes) {
            graphicShape.draw(g);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.drawShapes(g);
    }

    @Override
    public void stateChanged(StateChangedActionEvent event) {
        this.repaint();
    }

    @Override
    public void shapeIsDeleted(ShapeIsDeletedActionEvent event) {
        removeShape(event.getShape());
    }

    @Override
    public void shapeIsInserted(ShapeIsInsertedActionEvent event) {
        addShape(event.getShape());
    }
}
