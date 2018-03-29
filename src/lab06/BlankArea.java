package lab06;

import net.objecthunter.exp4j.ExpressionBuilder;
import org.apache.commons.jexl3.JexlException;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class BlankArea extends JPanel {
    private Dimension dimension = new Dimension(1024, 450);

    public BufferedImage getSurface() {
        return surface;
    }

    private BufferedImage surface = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB);
    private ImageIcon imageIcon = new ImageIcon(surface);
    private Random random = new Random();

    private JLabel view;

    private double[] x;
    private double[] y;
    private double xMin;
    private double xMax;
    private double yMin;
    private double yMax;
    private final int PAD = 100;
    private int length;

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
        if (DrawingFrame.getInstance().getToolbarPanel().getAllRandomBtn().isSelected()) {
            polygon = new RegularPolygon(x, y, random.nextInt(0x40), random.nextInt(0x10));
        } else {
            int sides = (int) DrawingFrame.getInstance().getToolbarPanel().getSidesSpinner().getValue();
            int radius = (int) DrawingFrame.getInstance().getToolbarPanel().getRadiusSpinner().getValue();
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

        int sides = (int) DrawingFrame.getInstance().getToolbarPanel().getSidesSpinner().getValue();
        int radius = (int) DrawingFrame.getInstance().getToolbarPanel().getRadiusSpinner().getValue();

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

    public void drawFunctionGraph(String expStr, int functionLength) {
        try {
            Graphics2D graphics = surface.createGraphics();
            graphics.setColor(Color.BLACK);
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // actual computing
            this.length = functionLength;
            this.x = new double[this.length];
            this.y = new double[this.length];
            for (int pointOnXAxis = 0; pointOnXAxis < this.length; pointOnXAxis++) {
                this.x[pointOnXAxis] = pointOnXAxis;
                this.y[pointOnXAxis] = computeExp(expStr, pointOnXAxis);
            }

            double[] xVals = getExtremeValues(x);
            xMin = xVals[0];
            xMax = xVals[1];
            System.out.printf("xMin = %5.1f  xMax = %5.1f%n", xMin, xMax);

            double[] yVals = getExtremeValues(y);
            yMin = yVals[0];
            yMax = yVals[1];
            System.out.printf("yMin = %5.1f  yMax = %5.1f%n", yMin, yMax);

            drawFunctionGraph(graphics);

            graphics.setColor(Color.red);
            graphics.dispose();
            view.repaint();

        } catch (JexlException e) {
            JOptionPane.showMessageDialog(DrawingFrame.getInstance(),
                    "Invalid function syntax!",
                    "Function Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private double computeExp(String expStr, int x) {
        net.objecthunter.exp4j.Expression e = new ExpressionBuilder(expStr)
                .variables("x")
                .build()
                .setVariable("x", x);
        return e.evaluate();
    }

    private void drawFunctionGraph(Graphics2D g2) {
        int w = getWidth();
        int h = getHeight();

        double xScale = (w - 2 * PAD) / (xMax - xMin);
        double yScale = (h - 2 * PAD) / (yMax - yMin);

        System.out.printf("xScale = %.1f  yScale = %.1f%n", xScale, yScale);

        Point2D.Double origin = new Point2D.Double(); // Axes origin.
        Point2D.Double offset = new Point2D.Double(); // Locate data.
        if (xMax < 0) {
            origin.x = w - PAD;
            offset.x = origin.x - xScale * xMax;
        } else if (xMin < 0) {
            origin.x = PAD - xScale * xMin;
            offset.x = origin.x;
        } else {
            origin.x = PAD;
            offset.x = PAD - xScale * xMin;
        }
        if (yMax < 0) {
            origin.y = h - PAD;
            offset.y = origin.y - yScale * yMax;
        } else if (yMin < 0) {
            origin.y = PAD - yScale * yMin;
            offset.y = origin.y;
        } else {
            origin.y = PAD;
            offset.y = PAD - yScale * yMin;
        }

        System.out.printf("origin = [%6.1f, %6.1f]%n", origin.x, origin.y);
        System.out.printf("offset = [%6.1f, %6.1f]%n", offset.x, offset.y);

        // Draw abcissa.
        g2.draw(new Line2D.Double(PAD, origin.y, w - PAD, origin.y));
        // Draw ordinate.
        g2.draw(new Line2D.Double(origin.x, PAD, origin.x, h - PAD));
        g2.setPaint(Color.red);
        // Mark origin.
        g2.fill(new Ellipse2D.Double(origin.x - 2, origin.y - 2, 4, 4));

        g2.setPaint(Color.red);

        // Plot data.
        Path2D p = new Path2D.Double();
        p.moveTo(offset.x + xScale * x[0], offset.y + yScale * y[0]);
        for (int i = 1; i < x.length - 1; i++) {
            double x1 = offset.x + xScale * x[i];
            double y1 = offset.y + yScale * y[i];
            p.lineTo(x1, y1);
            System.out.printf("i = %d  x1 = %6.1f  y1 = %.1f%n", i, x1, y1);
            if (this.length < 1000) {
                g2.fill(new Ellipse2D.Double(x1 - 2, y1 - 2, 4, 4));
                g2.drawString(String.valueOf(i), (float) x1 + 3, (float) y1 - 3);
            }
        }

        //p.closePath(); // don t close it!
        g2.draw(p);

        // Draw extreme data values.
        g2.setPaint(Color.black);

        Font font = g2.getFont();
        FontRenderContext frc = g2.getFontRenderContext();
        LineMetrics lm = font.getLineMetrics("0", frc);

        String s = String.format("%.1f", xMin);
        double x = offset.x + xScale * xMin;
        g2.drawString(s, (float) x, (float) origin.y + lm.getAscent());

        s = String.format("%.1f", xMax);
        float width = (float) font.getStringBounds(s, frc).getWidth();
        x = offset.x + xScale * xMax;
        g2.drawString(s, (float) x - width, (float) origin.y + lm.getAscent());

        s = String.format("%.1f", yMin);
        double y = offset.y + yScale * yMin;
        g2.drawString(s, (float) origin.x + 1, (float) y + lm.getAscent());

        s = String.format("%.1f", yMax);
        y = offset.y + yScale * yMax;
        g2.drawString(s, (float) origin.x + 1, (float) y);

        System.out.println("------------------------------");
    }

    private double[] getExtremeValues(double[] d) {
        double min = Double.MAX_VALUE;
        double max = -min;
        for (double aD : d) {
            if (aD < min) {
                min = aD;
            }
            if (aD > max) {
                max = aD;
            }
        }
        return new double[]{min, max};
    }


    // TO DO: Lagrange interpolation function
    // TO DO: SVG Export
}