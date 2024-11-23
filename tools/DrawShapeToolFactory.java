package tools;

import shapes.*;

import java.util.HashMap;
import java.util.Map;

public class DrawShapeToolFactory {
    private Map<Shape, DrawShapeTool> shapes = new HashMap<>();

    public DrawShapeTool create(Shape shape) {
        if(shapes.containsKey(shape)) {
            return shapes.get(shape);
        }

        return switch (shape) {
            case Circle circle -> create((Circle) shape);
            case Line line -> create((Line) shape);
            case Rectangle rectangle -> create((Rectangle) shape);
            case Text text -> create((Text) shape);
            case null, default -> null;
        };
    }

    public DrawCircleTool create(Circle circle) {
        if(shapes.containsKey(circle)) {
            return (DrawCircleTool) shapes.get(circle);
        }
        DrawCircleTool graphicCircle = new DrawCircleTool(circle);
        shapes.put(circle, graphicCircle);
        return graphicCircle;
    }

    public DrawLineTool create(Line line) {
        if(shapes.containsKey(line)) {
            return (DrawLineTool) shapes.get(line);
        }
        DrawLineTool graphicLine = new DrawLineTool(line);
        shapes.put(line, graphicLine);
        return graphicLine;
    }

    public DrawRectangleTool create(Rectangle rectangle) {
        if(shapes.containsKey(rectangle)) {
            return (DrawRectangleTool) shapes.get(rectangle);
        }
        DrawRectangleTool graphicRectangle = new DrawRectangleTool(rectangle);
        shapes.put(rectangle, graphicRectangle);
        return graphicRectangle;
    }

    public DrawTextTool create(Text text) {
        if(shapes.containsKey(text)) {
            return (DrawTextTool) shapes.get(text);
        }
        DrawTextTool graphicText = new DrawTextTool(text);
        shapes.put(text, graphicText);
        return graphicText;
    }

    public DrawShapeTool getGraphicShape(Shape shape) {
        return shapes.get(shape);
    }

    public void removeGraphicShape(Shape shape) {
        shapes.remove(shape);
    }
}
