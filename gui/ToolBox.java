package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import events.*;
import controller.DrawingController;
import shapes.FillableShape;
import shapes.Shape;
import shapes.Text;
import tools.*;

public class ToolBox extends JToolBar implements SelectShapeActionListener,
		SelectedManyShapesActionListener, ClearSelectedShapesActionListener {

	private static final long serialVersionUID = 1L;
	private DrawingController controller;
	private ButtonGroup buttons;
	private ColorButton colorbutton;
	private FillCheckBox fillCheckBox;
	private FontSpinner fontSpinner;
	private Tool selectedTool;

	public static final ToolEnum[] toolNames = new ToolEnum[]{ ToolEnum.SELECT, ToolEnum.LINE, ToolEnum.RECTANGLE, ToolEnum.CIRCLE, ToolEnum.TEXT};

	private HashMap<ToolEnum, Tool> toolsList = new HashMap<>();

	public ToolBox(DrawingController controller) {
		super("Tools", VERTICAL);
		this.controller = controller;
		this.controller.setToolBox(this);
		makeToolsList();
		selectedTool = toolsList.get(ToolEnum.SELECT);
		buttons = new ButtonGroup();

		add(Box.createRigidArea(new Dimension(10, 10)));
		add(new JLabel("Tools"));
		add(Box.createRigidArea(new Dimension(10, 10)));

		for(ToolEnum tool : toolNames) {
			JToggleButton btn = new JToggleButton(toolsList.get(tool).getImageIcon());
			btn.addActionListener(e -> {
				if(tool != ToolEnum.SELECT) {
					controller.clearSelection();
				}
				fillCheckBox.setEnabled(toolsList.get(tool).isFillable());
				setSelectedTool(toolsList.get(tool));
			});
			btn.setToolTipText(toolsList.get(tool).getTipText());
			add(btn);
			buttons.add(btn);
		}

		fillCheckBox = new FillCheckBox();
		fillCheckBox.addActionListener(e -> {
			controller.toggleFilled();
		});
		this.controller.addFillChangedActionListener(fillCheckBox);

		colorbutton = new ColorButton(Color.BLACK);
		colorbutton.addColorChangedListener(controller);

		fontSpinner = new FontSpinner();

		add(Box.createRigidArea(new Dimension(10, 10)));
		add(new JLabel("Fill"));
		add(fillCheckBox);

		add(Box.createRigidArea(new Dimension(10, 10)));
		add(new JLabel("Color"));
		add(Box.createRigidArea(new Dimension(10, 10)));
		add(colorbutton);

		add(Box.createRigidArea(new Dimension(10, 10)));
		add(new JLabel("Font"));
		add(Box.createRigidArea(new Dimension(10, 10)));

		JPanel jp = new JPanel(new BorderLayout());
		jp.add(fontSpinner, BorderLayout.NORTH);
		add(jp);

	}

	public void makeToolsList() {
		toolsList.put(ToolEnum.SELECT, new SelectTool(controller));
		toolsList.put(ToolEnum.LINE, new DrawLineTool(controller, this));
		toolsList.put(ToolEnum.RECTANGLE, new DrawRectangleTool(controller, this));
		toolsList.put(ToolEnum.CIRCLE, new DrawCircleTool(controller, this));
		toolsList.put(ToolEnum.TEXT, new DrawTextTool(controller, this));
	}

	public ColorButton getColorbutton() {
		return this.colorbutton;
	}

	public Color getColor() {
		return colorbutton.getSelectedColor();
	}

	public boolean getFill() {
		return fillCheckBox.isFill();
	}

	public int getFontSize() {
		return (Integer) fontSpinner.getValue();
	}

	public void setColor(Color color) {
		colorbutton.setSelectedColor(color);
	}

	public void setFill(boolean f) {
		fillCheckBox.setSelected(f);
	}

	public void setFontSize(int size) {
		fontSpinner.setValue(size);
	}

	public Tool getSelectedTool() {
		return selectedTool;
	}

	public void setSelectedTool(Tool tool) {
		this.selectedTool = tool;
	}

	@Override
	public void selectedShape(SelectShapeActionEvent event) {
		Shape selectedShape = event.getShape();

		colorbutton.setEnabled(true);
		colorbutton.setSelectedColor(selectedShape.getColor());

		if (selectedShape instanceof FillableShape) {
			fillCheckBox.setEnabled(true);
			this.setFill(((FillableShape) selectedShape).getFilled());
		} else {
			fillCheckBox.setEnabled(false);
			this.setFill(false);
		}

		if (selectedShape instanceof Text) {
			fontSpinner.setEnabled(true);
			this.setFontSize(((Text) selectedShape).getFont().getSize());
		} else {
			fontSpinner.setEnabled(false);
		}
	}

	@Override
	public void selectedManyShapes(SelectedManyShapesActionEvent event) {
		fillCheckBox.setEnabled(false);
		colorbutton.setEnabled(false);
		fontSpinner.setEnabled(false);
	}

	@Override
	public void clearSelectedShapes(ClearSelectedShapesActionEvent event) {
		fillCheckBox.setEnabled(true);
		colorbutton.setEnabled(true);
		fontSpinner.setEnabled(true);
	}
}
