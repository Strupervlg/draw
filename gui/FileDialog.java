package gui;

import controller.DrawIO;
import controller.DrawingController;

import javax.swing.*;

public abstract class FileDialog extends JFileChooser {

    protected DrawIO drawIO;

    protected DrawingController controller;

    FileDialog(DrawIO drawIO, DrawingController controller) {
        this.drawIO = drawIO;
        this.controller = controller;
    }
}
