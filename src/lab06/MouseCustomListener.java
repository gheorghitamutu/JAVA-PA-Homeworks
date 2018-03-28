package lab06;

import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;

public class MouseCustomListener extends MouseInputAdapter {

    private Canvas canvas;

    private static final String NEWLINE = System.getProperty("line.separator");

    public void setDrawWhileDragged(boolean drawWhileDragged) {
        this.drawWhileDragged = drawWhileDragged;
    }

    private boolean drawWhileDragged = false;

    MouseCustomListener(Canvas canvas) {
        super();
        this.canvas = canvas;
    }

    private void eventOutput(String eventDescription, MouseEvent e) {
        this.canvas.getTextArea().append(eventDescription + " detected on "
                + e.getComponent().getClass().getName()
                + "." + NEWLINE);
        this.canvas.getTextArea().setCaretPosition(this.canvas.getTextArea().getDocument().getLength());
    }

    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            eventOutput("Mouse pressed (# of clicks: "
                + e.getClickCount() + ")", e);

            this.canvas.getBlankArea().drawShapeAt(e.getX() + 20, e.getY() + 60);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            eventOutput("Mouse released (# of clicks: "
                    + e.getClickCount() + ")", e);
        }
    }

    public void mouseEntered(MouseEvent e) {
        eventOutput("Mouse entered", e);
    }

    public void mouseExited(MouseEvent e) {
        eventOutput("Mouse exited", e);
    }

    public void mouseClicked(MouseEvent e) {
        eventOutput("Mouse clicked (# of clicks: "
                + e.getClickCount() + ")", e);
    }

    public void mouseMoved(MouseEvent e) {
        // eventOutput("Mouse moved", e);
    }

    public void mouseDragged(MouseEvent e) {
        if(this.drawWhileDragged) {
            this.canvas.getBlankArea().drawShapeAt(e.getX() + 20, e.getY() + 60);
        }
        eventOutput("Mouse dragged", e);
    }
}
