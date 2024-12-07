package shapes;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class ShapeFactory {

    private HashMap<String, Class<?>> shapes = new HashMap<>();

    public ShapeFactory() {
        shapes.put(Circle.getName(), Circle.class);
        shapes.put(Rectangle.getName(), Rectangle.class);
        shapes.put(Line.getName(), Line.class);
        shapes.put(Text.getName(), Text.class);
    }

    public Shape fromString(String string) {
        Shape shape;
        String[] parts = string.split(";");
        parts[0] = parts[0].trim();
        if(!shapes.containsKey(parts[0])) {
            throw new ArrayIndexOutOfBoundsException();
        }
        try {
            shape = (Shape) shapes.get(parts[0])
                    .getDeclaredMethod("fromString", String.class)
                    .invoke(null, string);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return shape;
    }

    public Circle createCircle(Point position, boolean filled, Color color) {
        Circle circle = new Circle(position.x, position.y, filled);
        circle.setColor(color);
        return circle;
    }

    public Line createLine(Point position, Color color) {
        Line line = new Line(position.x, position.y);
        line.setColor(color);
        return line;
    }

    public Rectangle createRectangle(Point position, boolean filled, Color color) {
        Rectangle rectangle = new Rectangle(position.x, position.y, filled);
        rectangle.setColor(color);
        return rectangle;
    }

    public Text createText(Point position, int fontSize, String stringText, Color color) {
        Text text = new Text(position.x, position.y, fontSize, stringText);
        text.setColor(color);
        return text;
    }
}
