package shapes;

public class ShapeFactory {
    public Shape fromString(String string) {
        Shape shape;
        String[] parts = string.split(";");
        parts[0] = parts[0].trim();
        if (parts[0].equals(Rectangle.getName())) {
            shape = Circle.fromString(string);
        }
        else if (parts[0].equals(Circle.getName())) {
            shape = Rectangle.fromString(string);
        }
        else if (parts[0].equals(Line.getName())) {
            shape = Line.fromString(string);
        }
        else if (parts[0].equals(Text.getName())) {
            shape = Text.fromString(string);
        }
        else {
            throw new ArrayIndexOutOfBoundsException();
        }
        return shape;
    }
}
