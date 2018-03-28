package lab06;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
    private Dimension dimension = new Dimension(1024, 450);

    public MouseCustomListener getMouseListener() {
        return mouseListener;
    }

    private MouseCustomListener mouseListener;



    Canvas() {
        super();

        GridLayout gridLayout = new GridLayout(2 ,1);
        this.setLayout(gridLayout);
        mouseListener = new MouseCustomListener(this);
        blankArea = new BlankArea();


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
            String imagePath = this.fc.getSelectedFile().getAbsolutePath().endsWith(".png") ?
                    this.fc.getSelectedFile().getAbsolutePath() :
                    this.fc.getSelectedFile().getAbsolutePath() + ".png";

            try {
                ImageIO.write(blankArea.getSurface(),"png", new File(imagePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadCanvas() {
        fc.setCurrentDirectory(new File(Paths.get("").toAbsolutePath().toString()));
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            String imagePath = this.fc.getSelectedFile().getAbsolutePath().endsWith(".png") ?
                    this.fc.getSelectedFile().getAbsolutePath() :
                    this.fc.getSelectedFile().getAbsolutePath() + ".png";

            try {
                this.blankArea.changeSurface(ImageIO.read(new File(imagePath)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void resetCanvas() {
            this.blankArea.changeSurface(new BufferedImage(dimension.width , dimension.height,
                    BufferedImage.TYPE_INT_ARGB));
        }
}
