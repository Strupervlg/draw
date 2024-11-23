package gui;

import gui.MainMenu.NewDrawingDialog;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.DrawIO;
import controller.DrawingController;

/**
 * Listens to actions from the buttons in a menu and modifies the Drawing
 * through a DrawingController
 * 
 * @author Alex Lagerstedt
 * 
 */
public class MenuListener implements ActionListener {

	DrawingController controller;
	JFileChooser fileDialog;

	DrawGUI.DrawingContainer drawingContainer;

	private HashMap<String, Runnable> actions = new HashMap<>();

	private DrawIO fio;

	public MenuListener(DrawingController c, DrawGUI.DrawingContainer drawingContainer) {
		this.controller = c;
		this.drawingContainer = drawingContainer;
		fio = new DrawIO();
		this.makeActions();
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(!actions.containsKey(cmd)) {
			JOptionPane.showMessageDialog(null, "Not implemented.",
					"Not implemented", JOptionPane.ERROR_MESSAGE);
			return;
		}
		actions.get(cmd).run();
	}

	private void makeActions() {
		actions.put("Quit", () -> System.exit(0));
		actions.put("Undo", () -> controller.undo());
		actions.put("Redo", () -> controller.redo());
		actions.put("Select all", () -> controller.selectAll());
		actions.put("Clear selection", () -> controller.clearSelection());
		actions.put("Delete", () -> controller.deleteSelectedShapes());
		actions.put("Open", () -> {fileDialog = new JFileChooser();
			fileDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
			FileFilter filter = new FileNameExtensionFilter("Draw files",
					"draw");
			fileDialog.addChoosableFileFilter(filter);
			fileDialog.setFileFilter(filter);

			fileDialog.showOpenDialog(null);
			File f = fileDialog.getSelectedFile();
			if (f != null) {
				fio.open(f, controller);
			}
		});
		actions.put("Save as", () -> {
			fileDialog = new JFileChooser();
			fileDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);

			fileDialog.setSelectedFile(new File("new.draw"));
			FileFilter filter = new FileNameExtensionFilter("Draw files",
					"draw");
			fileDialog.addChoosableFileFilter(filter);
			fileDialog.setFileFilter(filter);

			fileDialog.showSaveDialog(null);

			File f = fileDialog.getSelectedFile();
			if (f != null) {
				fio.save(f, controller);
			}
		});
		actions.put("Export PNG", () -> {
			fileDialog = new JFileChooser();
			fileDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fileDialog.setDialogType(JFileChooser.CUSTOM_DIALOG);
			FileFilter filter = new FileNameExtensionFilter(
					"Portable Network Graphics", "png");
			fileDialog.addChoosableFileFilter(filter);

			fileDialog.setSelectedFile(new File("out.png"));
			fileDialog.showSaveDialog(null);

			File f = fileDialog.getSelectedFile();
			if (f != null) {
				fio.export(f, controller, drawingContainer.getDrawingCanvas());
			}
		});
		actions.put("New", () -> {
			NewDrawingDialog diag = new NewDrawingDialog();
			Dimension size = diag.getNewSize();
			System.out.println(size);
			if (size != null) {
				controller.newDrawing(size);
			}
		});
	}
}
