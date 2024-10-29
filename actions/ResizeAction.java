package actions;

import shapes.Shape;

import java.awt.*;

public class ResizeAction implements DrawAction, MergeAction {

    Shape shape;

    Point newPoint2;

    Point oldPoint2;

    public ResizeAction(Shape shape, Point point2) {
        this.shape = shape;
        this.newPoint2 = point2;
        this.oldPoint2 = shape.getPoint2();
    }

    @Override
    public void execute() {
        shape.setPoint2(newPoint2);
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void redo() {
        this.execute();
    }

    @Override
    public void undo() {
        shape.setPoint2(oldPoint2);
    }

    @Override
    public boolean merge(MergeAction other) {
        if (other instanceof ResizeAction otherCommand) {
            if (this.shape.equals(otherCommand.shape)) {
                this.newPoint2 = otherCommand.newPoint2;
                return true;
            }
        }
        return false;
    }
}
