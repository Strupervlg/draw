package events;

import shapes.Shape;

import java.util.EventObject;

public class SelectShapeActionEvent extends EventObject {

    private Shape shape;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public SelectShapeActionEvent(Object source) {
        super(source);
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }
}
