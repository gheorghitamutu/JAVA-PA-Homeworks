package lab06;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Paths;

public class Canvas
            extends JPanel {

    public BlankArea getBlankArea() {
        return blankArea;
    }

    private BlankArea blankArea;

    public JTextArea getTextArea() {
        return textArea;
    }

    private JTextArea textArea;

    private JFileChooser fc = new JFileChooser();

    public MouseCustomListener getMouseListener() {
        return mouseListener;
    }

    private MouseCustomListener mouseListener;



    Canvas() {
        super();

        GridLayout gridLayout = new GridLayout(2 ,1);
        this.setLayout(gridLayout);
        mouseListener = new MouseCustomListener(this); // you need this before blankArea init
        blankArea = new BlankArea(this);
        add(blankArea);


        textArea = new JTextArea();
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(200, 75));

        add(scrollPane);

        //Register for mouse events on blankArea and the panel.
        blankArea.addMouseListener(mouseListener);
        blankArea.addMouseMotionListener(mouseListener);
        setPreferredSize(new Dimension(1024, 700));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        this.fc.setCurrentDirectory(new File(Paths.get("").toAbsolutePath().toString()));
    }



    public void saveCanvas() {
        fc.setCurrentDirectory(new File(Paths.get("").toAbsolutePath().toString()));
        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            String imagePath = this.fc.getSelectedFile().getAbsolutePath().endsWith(".svg") ?
                    this.fc.getSelectedFile().getAbsolutePath() :
                    this.fc.getSelectedFile().getAbsolutePath() + ".svg";
            this.getBlankArea().saveSVG(imagePath);
        }
    }

    public void loadCanvas() {
        fc.setCurrentDirectory(new File(Paths.get("").toAbsolutePath().toString()));
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            String file = this.fc.getSelectedFile().getAbsolutePath();

            this.getBlankArea().loadSVG(file);
        }

    }

    public void resetCanvas() {
            this.blankArea.changeSurface();
        }
}
