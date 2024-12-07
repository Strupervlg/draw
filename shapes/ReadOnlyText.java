package shapes;

import java.awt.*;

public interface ReadOnlyText extends ReadOnlyShape {
    Font getFont();
    String getText();
}
