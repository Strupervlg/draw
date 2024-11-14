package events;

import java.util.EventListener;

public interface ShapeIsDeletedActionListener extends EventListener {

    void shapeIsDeleted(ShapeIsDeletedActionEvent event);
}
