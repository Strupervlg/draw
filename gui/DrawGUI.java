package gui;

import java.awt.*;

import javax.swing.*;

import shapes.Drawing;
import controller.DrawingController;
import tools.ToolFactory;

import static javax.swing.SwingConstants.VERTICAL;

/**
 * Graphical user interface for the Drawing editor "Draw"
 * 
 * @author Alex Lagerstedt
 * 
 */

public class DrawGUI extends JFrame {

	/**
	 * A simple container that contains a Drawing instance and keeps it
	 * centered.
	 * 
	 * @author Alex Lagerstedt
	 * 
	 */
	public class DrawingContainer extends JPanel {

		private static final long serialVersionUID = 0;

		private DrawingCanvas drawingCanvas;

		public DrawingContainer() {
			super(new GridBagLayout());
		}

		public void setDrawing(Drawing drawing) {
			this.removeAll();

			drawingCanvas = new DrawingCanvas(drawing, toolFactory);
			this.add(drawingCanvas);
			drawing.addRepaintActionListener(drawingCanvas);
			drawing.addShapeIsInsertedActionListener(drawingCanvas);
			drawing.addShapeIsDeletedActionListener(drawingCanvas);
			setPreferredSize(drawingCanvas.getPreferredSize());

			pack();
		}

		public DrawingCanvas getDrawingCanvas() {
			return drawingCanvas;
		}
	}

	private DrawingController controller;
	private DrawingContainer drawingContainer;
	private ToolFactory toolFactory;
	private MainMenu mainMenu;
	private JScrollPane scrollpane;

	private static final long serialVersionUID = 0;

	public static void main(String[] args) {

		new DrawGUI();

	}

	/**
	 * Constructs a new graphical user interface for the program and shows it.
	 */
	public DrawGUI() {

		this.setTitle("Draw 0.2");
		setIconImage(Toolkit.getDefaultToolkit().getImage("img/logo.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		drawingContainer = new DrawingContainer();
		scrollpane = new JScrollPane(drawingContainer);

		controller = new DrawingController(this);
		toolFactory = new ToolFactory(controller);

		JToolBar toolBar = new JToolBar("Tools", VERTICAL);
		toolBar.setFloatable(false);
		toolBar.add(Box.createRigidArea(new Dimension(10, 10)));
		toolBar.add(new JLabel("Tools"));
		toolBar.add(Box.createRigidArea(new Dimension(10, 10)));
		for(JComponent tool : toolFactory.getTools()) {
			toolBar.add(tool);
		}

		getContentPane().add(toolBar, BorderLayout.WEST);
		getContentPane().add(scrollpane, BorderLayout.CENTER);

		mainMenu = new MainMenu(controller, drawingContainer);
		controller.addEnableClearActionListener(mainMenu);
		controller.addEnableSelectAllActionListener(mainMenu);
		controller.addEnableDeleteActionListener(mainMenu);
		controller.addEnableUndoActionListener(mainMenu);
		controller.addEnableRedoActionListener(mainMenu);
		this.setJMenuBar(mainMenu);

		this.updateDrawing();
		pack();
		setVisible(true);

	}

	/**
	 * Updates the GUI to show the Drawing instance that is currently controlled
	 * by the DrawingController.
	 */
	public void updateDrawing() {

		drawingContainer.setDrawing(controller.getDrawing());

		scrollpane.setPreferredSize(new Dimension(drawingContainer
				.getPreferredSize().width + 100, drawingContainer
				.getPreferredSize().height + 100));
		pack();
		repaint();
	}
}
