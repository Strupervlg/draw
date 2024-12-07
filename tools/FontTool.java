package tools;

import controller.DrawingController;
import events.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FontTool extends JPanel implements EnableFontActionListener, FontChangedActionListener {

    private static final Dimension DEFAULT_PREFERRED_SIZE = new Dimension(0, 20);

    private static final Dimension DEFAULT_MAXIMUM_SIZE = new Dimension(0, 20);

    private static final Dimension DEFAULT_MINIMUM_SIZE = new Dimension(0, 20);

    private static final int DEFAULT_VALUE = 12;

    private static final int DEFAULT_MIN = 6;

    private static final int DEFAULT_MAX = 96;

    private static final int DEFAULT_STEP = 1;

    private JSpinner fontSpinner;

    FontTool(DrawingController controller) {
        fontSpinner = new JSpinner(new SpinnerNumberModel(DEFAULT_VALUE, DEFAULT_MIN, DEFAULT_MAX, DEFAULT_STEP));
        fontSpinner.setPreferredSize(DEFAULT_PREFERRED_SIZE);
        fontSpinner.setMaximumSize(DEFAULT_MAXIMUM_SIZE);
        fontSpinner.setMinimumSize(DEFAULT_MINIMUM_SIZE);
        this.addFontChangedActionListener(controller);
        fontSpinner.addChangeListener(e -> {
            this.fireFontChanged((int)fontSpinner.getValue());
        });
        fontSpinner.setValue(12);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createRigidArea(new Dimension(10, 10)));
        this.add(new JLabel("Font"));
        this.add(Box.createRigidArea(new Dimension(10, 10)));

        JPanel jp = new JPanel(new BorderLayout());
        jp.add(fontSpinner, BorderLayout.NORTH);
        this.add(jp);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.fontSpinner.setEnabled(enabled);
    }

    public void setFontSize(int size) {
        fontSpinner.setValue(size);
    }

    @Override
    public void fontEnabled(EnableFontActionEvent event) {
        this.setEnabled(event.isEnable());
    }

    @Override
    public void fontChanged(FontChangedActionEvent event) {
        this.setFontSize(event.getFontSize());
    }


    // ------------------------------- EVENTS ---------------------------------

    private ArrayList<FontChangedActionListener> fontChangedActionListeners = new ArrayList<>();

    public void addFontChangedActionListener(FontChangedActionListener listener) {
        fontChangedActionListeners.add(listener);
    }

    public void removeFontChangedActionListener(FontChangedActionListener listener) {
        fontChangedActionListeners.remove(listener);
    }

    private void fireFontChanged(int fontSize) {
        for(FontChangedActionListener listener: fontChangedActionListeners) {
            FontChangedActionEvent event = new FontChangedActionEvent(listener);
            event.setFontSize(fontSize);
            listener.fontChanged(event);
        }
    }
}
