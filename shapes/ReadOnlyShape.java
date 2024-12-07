package shapes;

import java.awt.*;

public interface ReadOnlyShape {
    Point getPosition();
    Color getColor();
    Point getSize();
    boolean getSelected();
    Point getPoint2();
    Point getPoint1();
}
