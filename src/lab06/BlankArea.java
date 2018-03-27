package lab06;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class BlankArea extends JPanel {
    private Dimension dimension = new Dimension(1024, 450);
    private BufferedImage surface = new BufferedImage(dimension.width , dimension.height, BufferedImage.TYPE_INT_ARGB);
    private ImageIcon imageIcon = new ImageIcon(surface);
    private Random random = new Random();

    private JLabel view;

    BlankArea() {
        GridLayout gridLayout = new GridLayout(0, 1);
        this.setLayout(gridLayout);

        this.view = new JLabel(imageIcon);
        this.view.setOpaque(true);
        this.view.setBackground(Color.WHITE);
        this.add(this.view);
        this.setPreferredSize(dimension);



        this.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public void drawShapeAt(int x, int y) {
        Graphics graphics = surface.getGraphics();
        Polygon polygon = new RegularPolygon(x, y, random.nextInt(0x40), random.nextInt(0x10));
        graphics.setColor(new Color(random.nextInt(0xFFFFFF)));
        graphics.drawPolygon(polygon);
        graphics.dispose();
        view.repaint();
    }
}