package tools;

import controller.DrawingController;
import shapes.FillableShape;

import java.awt.*;

public abstract class DrawFillableShapeTool extends DrawShapeTool {

    DrawFillableShapeTool(DrawingController controller) {
        super(controller);
    }

    DrawFillableShapeTool(FillableShape fillableShape) {
        super(fillableShape);
    }

    public abstract void drawFilled(Graphics g);

    public abstract void drawNonFilled(Graphics g);

    public void drawShape(Graphics g) {
        ((Graphics2D) g).setStroke(new BasicStroke((float) strokeWidth));

        if (((FillableShape)shape).getFilled()) {
            drawFilled(g);
        } else {
            drawNonFilled(g);
        }
    }
}
