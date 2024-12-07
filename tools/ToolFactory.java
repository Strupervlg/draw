package tools;

import controller.DrawingController;
import shapes.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ToolFactory {
    private ButtonGroupTool buttons;

    private ArrayList<JComponent> tools = new ArrayList<>();

    private Class<?>[] groupTools = {SelectTool.class, DrawLineTool.class, DrawRectangleTool.class, DrawCircleTool.class, DrawTextTool.class};

    private Map<Shape, DrawShapeTool> shapes = new HashMap<>();

    private Map<Class<?>, Class<?>> shapeToDrawShape = new HashMap<>();

    private void makeShapeToDrawShape() {
        shapeToDrawShape.put(Line.class, DrawLineTool.class);
        shapeToDrawShape.put(Rectangle.class, DrawRectangleTool.class);
        shapeToDrawShape.put(Circle.class, DrawCircleTool.class);
        shapeToDrawShape.put(Text.class, DrawTextTool.class);
    }

    public ToolFactory(DrawingController controller) {
        makeShapeToDrawShape();
        buttons = new ButtonGroupTool();

        for (Class<?> groupTool : groupTools) {
            try {
                Tool tool = (Tool)groupTool.getDeclaredConstructor(DrawingController.class).newInstance(controller);
                buttons.add(tool);
                tools.add(tool);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        buttons.getFirstButton().setSelected(true);
        SelectTool selectTool = buttons.getTool(SelectTool.class);

        createFillTool(controller, selectTool);
        createColorTool(controller, selectTool);
        createFontTool(controller, selectTool);
    }

    private FillTool createFillTool(DrawingController controller, SelectTool selectTool) {
        FillTool fillTool = new FillTool(controller);
        Enumeration<AbstractButton> buttons = this.buttons.getElements();
        while (buttons.hasMoreElements()) {
            AbstractButton button = buttons.nextElement();
            ((Tool) button).addEnableFillActionListener(fillTool);
        }
        selectTool.addFillChangedActionListener(fillTool);
        tools.add(fillTool);
        return fillTool;
    }

    private ColorTool createColorTool(DrawingController controller, SelectTool selectTool) {
        ColorTool colorTool = new ColorTool(controller);
        selectTool.addColorChangedListener(colorTool);
        tools.add(colorTool);
        return colorTool;
    }

    private FontTool createFontTool(DrawingController controller, SelectTool selectTool) {
        FontTool fontTool = new FontTool(controller);
        Enumeration<AbstractButton> buttons = this.buttons.getElements();
        while (buttons.hasMoreElements()) {
            AbstractButton button = buttons.nextElement();
            ((Tool) button).addEnableFontActionListener(fontTool);
        }
        selectTool.addFontChangedActionListener(fontTool);
        tools.add(fontTool);
        return fontTool;
    }

    public ArrayList<JComponent> getTools() {
        return tools;
    }

    public ButtonGroupTool getButtons() {
        return buttons;
    }

    public DrawShapeTool create(Shape shape) {
        if(shapes.containsKey(shape)) {
            return shapes.get(shape);
        }

        DrawShapeTool drawShapeTool = createMappedDrawShapeTool(shape);
        shapes.put(shape, drawShapeTool);
        return drawShapeTool;
    }

    private DrawShapeTool createMappedDrawShapeTool(Shape shape) {
        for (Map.Entry<Class<?>, Class<?>> entry : shapeToDrawShape.entrySet()) {
            Class<?> keyClass = entry.getKey();
            Class<?> valueClass = entry.getValue();

            if (keyClass.isInstance(shape)) {
                try {
                    return (DrawShapeTool) valueClass.getDeclaredConstructor(keyClass).newInstance(shape);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return null;
    }

    public DrawShapeTool getGraphicShape(Shape shape) {
        return shapes.get(shape);
    }

    public void removeGraphicShape(Shape shape) {
        shapes.remove(shape);
    }
}
