package gui;

import controller.DrawIO;
import controller.DrawingController;
import exceptions.DrawIOException;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class ExportPNGDialog extends FileDialog implements Dialog {

    private DrawingCanvas drawingCanvas;

    ExportPNGDialog(DrawIO drawIO, DrawingController controller, DrawingCanvas drawingCanvas) {
        super(drawIO, controller);
        this.drawingCanvas = drawingCanvas;
    }

    @Override
    public void showDialog() {
        this.setFileSelectionMode(JFileChooser.FILES_ONLY);
        this.setDialogType(JFileChooser.CUSTOM_DIALOG);
        FileFilter filter = new FileNameExtensionFilter(
                "Portable Network Graphics", "png");
        this.addChoosableFileFilter(filter);

        this.setSelectedFile(new File("out.png"));
        int result = this.showSaveDialog(null);

        File f = this.getSelectedFile();
        if (f != null && result == JFileChooser.APPROVE_OPTION) {
            try {
                drawIO.export(f, controller, drawingCanvas);
            } catch (DrawIOException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
