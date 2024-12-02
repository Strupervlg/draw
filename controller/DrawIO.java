package controller;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

import exceptions.DrawIOException;
import gui.DrawingCanvas;
import gui.SaveAsDialog;
import shapes.*;

public class DrawIO {

	public void export(File f, DrawingController c, DrawingCanvas drawingCanvas) throws DrawIOException {
		try {
			c.getDrawing().clearSelection();
			BufferedImage bi = drawingCanvas.getImage(); // retrieve image
			ImageIO.write(bi, "png", f);
		}
		catch (IOException e) {
			throw new DrawIOException(e.getMessage());
		}
	}

	public void open(File f, DrawingController c, SaveAsDialog dialog) throws DrawIOException {
		int lineNumber = 1;
		try {
			BufferedReader in = new BufferedReader(new FileReader(f));
			ShapeFactory shapeFactory = new ShapeFactory();
			String str;

			//TODO не ставить новую а очищать старую и undo manager тоже
			c.newDrawing(Drawing.fromString(in.readLine()), dialog);

			while ((str = in.readLine()) != null) {
				try {
					lineNumber++;
					if (str.length() == 0) {
						continue;
					}
					Shape shape = shapeFactory.fromString(str);
					if (shape != null) {
						c.addShape(shape);
					}
				} catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
					throw new DrawIOException("Could not read line " + lineNumber
							+ " in file \"" + f + "\"");
				}
            }
			in.close();
		}
		catch (IOException e) {
			throw new DrawIOException(e.getMessage());
		}
	}

	public void save(File f, DrawingController controller) throws DrawIOException {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(f));
			out.write(controller.getDrawing().toString());
			out.close();
		}
		catch (IOException e) {
			throw new DrawIOException("Could not save the drawing.");
		}
	}
}
