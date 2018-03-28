package lab06;

import org.apache.commons.jexl3.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;

public class BlankArea extends JPanel {
    private Dimension dimension = new Dimension(1024, 450);
    private static final JexlEngine jexl = new JexlBuilder().cache(512).strict(true).silent(false).create();

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

    public boolean drawFunctionGraph(String expStr) {
        int offsetX = DrawingFrame.getInstance().getCanvas().getMouseListener().getOffsetX();
        int offsetY = DrawingFrame.getInstance().getCanvas().getMouseListener().getOffsetY();
        int offset20 = 20;
        int startX = offsetY + offset20;
        int length = (this.view.getSize().width + offsetX + offset20 * 2) - startX;


        try {
            checkExpression(expStr, startX);
        }
        catch (JexlException e) {
            JOptionPane.showMessageDialog(DrawingFrame.getInstance(),
                    "Invalid function syntax!",
                    "Function Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        Graphics2D graphics = surface.createGraphics();
        graphics.setColor(Color.BLACK);

        // X line
        graphics.drawLine(
                offsetY + offset20,                                      // leftX
                (this.view.getSize().height + offsetY * 2) / 2,         // topY
                this.view.getSize().width - + offsetX + offset20 ,      // rightX
                (this.view.getSize().height + offsetY * 2) / 2);        // botY

        // Y line
        graphics.drawLine(
                (this.view.getSize().width + offsetX) / 2,
                offsetY + offset20 * 2,
                (this.view.getSize().width + offsetX) / 2,
                (this.view.getSize().height + offsetY) - offset20);

        // X arrow
        graphics.drawLine(
                this.view.getSize().width - + offsetX + offset20 ,
                (this.view.getSize().height + offsetY * 2) / 2,
                this.view.getSize().width - + offsetX + offset20 / 2 ,
                (this.view.getSize().height + offsetY * 2) / 2 - offset20 / 2);
        graphics.drawLine(
                this.view.getSize().width - + offsetX + offset20 ,
                (this.view.getSize().height + offsetY * 2) / 2,
                this.view.getSize().width - + offsetX + offset20 / 2,
                (this.view.getSize().height + offsetY * 2) / 2 + offset20 / 2);

        // Y arrow
        graphics.drawLine(
                (this.view.getSize().width + offsetX) / 2,
                offsetY + offset20 * 2,
                (this.view.getSize().width + offsetX) / 2 - offset20 / 2,
                offsetY + offset20 * 2 + offset20 / 2);
        graphics.drawLine(
                (this.view.getSize().width + offsetX) / 2,
                offsetY + offset20 * 2,
                (this.view.getSize().width + offsetX) / 2 + offset20 / 2,
                offsetY + offset20 * 2 + offset20 / 2);

        // arrow names
        graphics.drawString("X",
                this.view.getSize().width - offset20 + offsetX,
                (this.view.getSize().height + offsetY) / 2);
        graphics.drawString("Y",
                (this.view.getSize().width + offsetX) / 2 + offset20,
                offset20 + offsetY);

        Polygon p = new Polygon();
        Polygon p2 = new Polygon();

        // bring the starting point to the visible area of the graph
        // just scaling factor with the minium need for highest Y point
        // THIS IS NOT CORRECT!
        double y = computeExp(expStr, startX);
        double scalingFactor = 1;
        int windowSize = (this.view.getSize().height + offsetY * 2);

        if(y < 0) {
            scalingFactor = -1;
        }

        for (int pointOnXAxis = startX; pointOnXAxis <= length; pointOnXAxis++) {
            y = computeExp(expStr, pointOnXAxis);
            while (y * scalingFactor > windowSize) {
                scalingFactor *= 0.95;
            }
        }

        // actual computing
        for (int pointOnXAxis = startX; pointOnXAxis <= length; pointOnXAxis++) {
            y = computeExp(expStr, pointOnXAxis);
            y = y * scalingFactor;
            //double x = pointOnXAxis * scalingFactor;
            p.addPoint(pointOnXAxis, (int)y);
        }

        graphics.setColor(Color.red);
        graphics.drawPolyline(p.xpoints, p.ypoints, p.npoints);
        graphics.setColor(Color.blue);
        graphics.drawPolyline(p2.xpoints, p2.ypoints, p2.npoints);
        graphics.dispose();
        view.repaint();

        return true;
    }

    private double f(double x) {
        return sin(x);
    }

    private double gCos(double y) {
        return cos(y);
    }

    // "custom" sin and cos function are scaling the results to a wider Y in order to see something on the canvas
    private int getSin(int X) {
        int offsetY = DrawingFrame.getInstance().getCanvas().getMouseListener().getOffsetY();
        return (this.view.getSize().height + offsetY * 2) / 2 - (int) (50 * f((X / 100.0) * 2 * Math.PI));
    }

    private int getCos(int X) {
        int offsetY = DrawingFrame.getInstance().getCanvas().getMouseListener().getOffsetY();
        return (this.view.getSize().height + offsetY * 2) / 2 - (int) (50 * gCos((X / 100.0) * 2 * Math.PI));
    }

    private double computeExp(String expStr, int x) {
        expStr = expStr.replace(" ", "");
        expStr = expStr.replace("sin(x)", Integer.toString(getSin(x)));
        expStr = expStr.replace("cos(x)", Integer.toString(getCos(x)));
        expStr = expStr.replace("x", Integer.toString(x));

        ArrayList<String> allMatches = new ArrayList<>();
        Matcher m = Pattern.compile("([0-9 ]+\\^[0-9 ]+)")
                .matcher(expStr);
        while (m.find()) {
            allMatches.add(m.group());
        };

        for(String match: allMatches) {
            ArrayList<String> terms = new ArrayList<>();
            Matcher matchTerms = Pattern.compile("([0-9 ]+)")
                    .matcher(expStr);
            while (matchTerms.find()) {
                terms.add(matchTerms.group());
            };
            double first = Double.parseDouble(terms.get(0));
            double second = Double.parseDouble(terms.get(1));
            expStr = expStr.replace(match, Double.toString(pow(first, second)));
        }



        JexlExpression exp = jexl.createExpression( expStr );
        JexlContext context = new MapContext();

        Number result = (Number) exp.evaluate(context);

        return result.doubleValue();
    }

    private JexlExpression checkExpression(String exp, int x) {
        exp = exp.replace(" ", "");
        exp = exp.replace("sin(x)", Integer.toString(getSin(x)));
        exp = exp.replace("cos(x)", Integer.toString(getCos(x)));
        return jexl.createExpression( exp );
    }
}