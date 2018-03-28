package lab06;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class BlankArea extends JPanel {
    private Dimension dimension = new Dimension(1024, 450);

    public BufferedImage getSurface() {
        return surface;
    }

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
        Graphics2D graphics = surface.createGraphics();
        Polygon polygon;
        if(DrawingFrame.getInstance().getToolbarPanel().getAllRandomBtn().isSelected()) {
            polygon = new RegularPolygon(x, y, random.nextInt(0x40), random.nextInt(0x10));
        }
        else {
            int sides = (int)DrawingFrame.getInstance().getToolbarPanel().getSidesSpinner().getValue();
            int radius = (int)DrawingFrame.getInstance().getToolbarPanel().getRadiusSpinner().getValue();
            polygon = new RegularPolygon(x, y, sides, radius);
        }
        graphics.setColor(new Color(random.nextInt(0xFFFFFF)));

        graphics.drawPolygon(polygon);
        graphics.dispose();
        view.repaint();
    }

    public void drawShapeAtRandomLocation() {
        Graphics2D graphics = surface.createGraphics();
        int maxx = this.view.getSize().width;
        int maxy = this.view.getSize().height;

        int sides = (int)DrawingFrame.getInstance().getToolbarPanel().getSidesSpinner().getValue();
        int radius = (int)DrawingFrame.getInstance().getToolbarPanel().getRadiusSpinner().getValue();

        Polygon polygon = new RegularPolygon(random.nextInt(maxx), random.nextInt(maxy), sides, radius);

        graphics.setColor(new Color(random.nextInt(0xFFFFFF)));

        graphics.drawPolygon(polygon);
        graphics.dispose();
        view.repaint();
    }

    public void drawShapeAllRandom() {
        int maxx = this.view.getSize().width;
        int maxy = this.view.getSize().height;

        Graphics2D graphics = surface.createGraphics();
        Polygon polygon = new RegularPolygon(random.nextInt(maxx), random.nextInt(maxy),
                random.nextInt(0x60), random.nextInt(0x60));
        graphics.setColor(new Color(random.nextInt(0xFFFFFF)));

        graphics.drawPolygon(polygon);
        graphics.dispose();
        view.repaint();
    }

    public void changeSurface(BufferedImage surface) {
        this.surface = surface;
        this.imageIcon = new ImageIcon(this.surface);

        for (Component component : this.getComponents()) {
            if (component == this.view) {
                component.setVisible(false);
                this.remove(component);
            }
        }

        this.view = new JLabel(this.imageIcon);
        this.view.setOpaque(true);
        this.view.setBackground(Color.WHITE);
        this.add(this.view);
    }
}