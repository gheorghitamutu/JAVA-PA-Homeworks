package lab06;

import net.objecthunter.exp4j.ExpressionBuilder;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.commons.jexl3.JexlException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.io.*;
import java.util.Random;

public class BlankArea extends JPanel {
    private Dimension dimension = new Dimension(1024, 450);

    private Random random = new Random();

    private JSVGCanvas jsvgCanvas = new JSVGCanvas();

    private double[] x;
    private double[] y;
    private double xMin;
    private double xMax;
    private double yMin;
    private double yMax;
    private final int PAD = 100;
    private int length;

    public void setLagrangeX(double[] lagrangeX) {
        this.lagrangeX = lagrangeX;
    }

    private double[] lagrangeX;

    public void setLagrangeY(double[] lagrangeY) {
        this.lagrangeY = lagrangeY;
    }

    private double[] lagrangeY;

    public void setLagrangeLength(int lagrangeLength) {
        this.lagrangeLength = lagrangeLength;
    }

    private int lagrangeLength;

    public void setLagrangeGraphLength(int lagrangeGraphLength) {
        this.lagrangeGraphLength = lagrangeGraphLength;
    }

    private int lagrangeGraphLength;

    private double[] lagrangeXPoints;
    private double[] lagrangeYPoints;

    // Get a DOMImplementation.
    private DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();

    // Create an instance of org.w3c.dom.Document.
    private String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
    private String svgRootElement = "svg";
    private SVGDocument document;

    public SVGGraphics2D getSvgGenerator() {
        return svgGenerator;
    }

    // Create an instance of the SVG Generator.
    private SVGGraphics2D svgGenerator;

    private Canvas canvas;

    BlankArea(Canvas canvas) {
        this.canvas = canvas;

        GridLayout gridLayout = new GridLayout(0, 1);
        this.setLayout(gridLayout);

        changeSurface();

        this.setPreferredSize(dimension);

        this.setBorder(BorderFactory.createLineBorder(Color.black));

    }

    public void drawShapeAt(int x, int y) {
        if(DrawingFrame.getInstance().getToolbarPanel().getStorePoints().isSelected()) {
            addLagrangePoint(x, y);
        }

        Polygon polygon;
        if (DrawingFrame.getInstance().getToolbarPanel().getAllRandomBtn().isSelected()) {
            polygon = new RegularPolygon(x, y, random.nextInt(0x40), random.nextInt(0x10));
        } else {
            int sides = (int) DrawingFrame.getInstance().getToolbarPanel().getSidesSpinner().getValue();
            int radius = (int) DrawingFrame.getInstance().getToolbarPanel().getRadiusSpinner().getValue();
            polygon = new RegularPolygon(x, y, sides, radius);
        }

        svgGenerator.setColor(new Color(random.nextInt(0xFFFFFF)));
        Element polygonElement = document.createElementNS(svgNS, "polygon");
        svgGenerator.drawPolygon(polygon);
        Element svgRoot = document.getDocumentElement();
        svgRoot.appendChild(polygonElement);
        // Populate the document root with the generated SVG content.
        svgGenerator.getRoot(document.getDocumentElement());
        // repaint
        jsvgCanvas.setSVGDocument(document);
    }

    public void drawShapeAtRandomLocation() {
        int maxx = this.jsvgCanvas.getSize().width;
        int maxy = this.jsvgCanvas.getSize().height;

        int sides = (int) DrawingFrame.getInstance().getToolbarPanel().getSidesSpinner().getValue();
        int radius = (int) DrawingFrame.getInstance().getToolbarPanel().getRadiusSpinner().getValue();

        Polygon polygon = new RegularPolygon(random.nextInt(maxx), random.nextInt(maxy), sides, radius);

        svgGenerator.setColor(new Color(random.nextInt(0xFFFFFF)));
        svgGenerator.drawPolygon(polygon);

        // Populate the document root with the generated SVG content.
        svgGenerator.getRoot(document.getDocumentElement());
        // repaint
        jsvgCanvas.setSVGDocument(document);
    }

    public void drawShapeAllRandom() {
        int maxx = this.jsvgCanvas.getSize().width;
        int maxy = this.jsvgCanvas.getSize().height;

        Polygon polygon = new RegularPolygon(random.nextInt(maxx), random.nextInt(maxy),
                random.nextInt(0x60), random.nextInt(0x60));
        svgGenerator.setColor(new Color(random.nextInt(0xFFFFFF)));

        svgGenerator.drawPolygon(polygon);
        // Populate the document root with the generated SVG content.
        svgGenerator.getRoot(document.getDocumentElement());
        // repaint
        jsvgCanvas.setSVGDocument(document);
    }

    public void changeSurface() {
        // just don't remove AFTER you added the component...
        for (Component component : this.getComponents()) {
            if (component == this.jsvgCanvas) {
                component.setVisible(false);
                this.remove(component);
            }
        }

        // Create an SVG document.
        impl = SVGDOMImplementation.getDOMImplementation();
        svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
        document = (SVGDocument) impl.createDocument(svgNS, svgRootElement, null);

        // Create a converter for this document.
        svgGenerator = new SVGGraphics2D(document);
        svgGenerator.setSVGCanvasSize(dimension);

        jsvgCanvas = new JSVGCanvas();
        this.add(jsvgCanvas);
        // Populate the document root with the generated SVG content.
        svgGenerator.getRoot(document.getDocumentElement());
        // repaint
        jsvgCanvas.setSVGDocument(document);

        this.jsvgCanvas.addMouseListener(canvas.getMouseListener());
        this.jsvgCanvas.addMouseMotionListener(canvas.getMouseListener());
    }

    public void drawFunctionGraph(String expStr, int functionLength) {
        try {
            if(expStr.equals("")) {
                return;
            }

            svgGenerator.setColor(Color.BLACK);
            svgGenerator.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

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

            drawFunctionGraph(svgGenerator);

            svgGenerator.setColor(Color.red);
            // Populate the document root with the generated SVG content.
            svgGenerator.getRoot(document.getDocumentElement());
            // repaint
            jsvgCanvas.setSVGDocument(document);

        } catch (JexlException e) {
            JOptionPane.showMessageDialog(DrawingFrame.getInstance(),
                    "Invalid function syntax!",
                    "Function Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void drawLagrangeGraph() {
        svgGenerator.setColor(Color.BLACK);
        svgGenerator.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // actual computing
        this.lagrangeXPoints = new double[this.lagrangeGraphLength];
        this.lagrangeYPoints = new double[this.lagrangeGraphLength];
        for (int pointOnXAxis = 0; pointOnXAxis < this.lagrangeGraphLength; pointOnXAxis++) {
            this.lagrangeXPoints[pointOnXAxis] = pointOnXAxis;
            this.lagrangeYPoints[pointOnXAxis] = computeLagrange(pointOnXAxis);
        }

        double[] xVals = getExtremeValues(this.lagrangeXPoints);
        xMin = xVals[0];
        xMax = xVals[1];
        System.out.printf("xMin = %5.1f  xMax = %5.1f%n", xMin, xMax);

        double[] yVals = getExtremeValues(this.lagrangeYPoints);
        yMin = yVals[0];
        yMax = yVals[1];
        System.out.printf("yMin = %5.1f  yMax = %5.1f%n", yMin, yMax);

        drawFunctionGraph(svgGenerator);

        svgGenerator.setColor(Color.red);
        // Populate the document root with the generated SVG content.
        svgGenerator.getRoot(document.getDocumentElement());
        // repaint
        jsvgCanvas.setSVGDocument(document);
    }

    private double computeLagrange(double x) {
        double result = 0; // Initialize result

        for (int index01 = 0; index01 < this.lagrangeLength; index01++)
        {
            // Compute individual terms of above formula
            double term = lagrangeY[index01];
            for (int index02 = 0; index02 < this.lagrangeLength; index02++)
            {
                if (index02 != index01 && (lagrangeX[index01] - lagrangeX[index02]) != 0)
                    term *= ((x - lagrangeY[index02]) / (lagrangeX[index01] - lagrangeX[index02]));
            }
            // Add current term to result
            result += term;
        }

        return result;
    }

    private double computeExp(String expStr, int x) {
        net.objecthunter.exp4j.Expression e = new ExpressionBuilder(expStr)
                .variables("x")
                .build()
                .setVariable("x", x);
        return e.evaluate();
    }

    private void drawFunctionGraph(Graphics2D graphics2D) {
        int length;
        double[] xAx;
        double[] yAx;
        if(!DrawingFrame.getInstance().getToolbarPanel().getStorePoints().isSelected()) {
            length = this.x.length;
            xAx = this.x;
            yAx = this.y;
        }
        else {
            length = this.lagrangeGraphLength;
            xAx = this.lagrangeXPoints;
            yAx = this.lagrangeYPoints;
        }
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
        graphics2D.draw(new Line2D.Double(PAD, origin.y, w - PAD, origin.y));
        // Draw ordinate.
        graphics2D.draw(new Line2D.Double(origin.x, PAD, origin.x, h - PAD));
        graphics2D.setPaint(Color.red);
        // Mark origin.
        graphics2D.fill(new Ellipse2D.Double(origin.x - 2, origin.y - 2, 4, 4));

        graphics2D.setPaint(Color.red);

        // Plot data.
        Path2D p = new Path2D.Double();
        p.moveTo(offset.x + xScale * xAx[0], offset.y + yScale * yAx[0]);
        for (int i = 1; i < length - 1; i++) {
            double x1 = offset.x + xScale * xAx[i];
            double y1 = offset.y + yScale * yAx[i];
            p.lineTo(x1, y1);
            System.out.printf("i = %d  x1 = %6.1f  y1 = %.1f%n", i, x1, y1);
            if (length < 100) {
                graphics2D.fill(new Ellipse2D.Double(x1 - 2, y1 - 2, 4, 4));
                graphics2D.drawString(String.valueOf(i), (float) x1 + 3, (float) y1 - 3);
            }
        }

        //p.closePath(); // don t close it!
        graphics2D.draw(p);

        // Draw extreme data values.
        graphics2D.setPaint(Color.black);

        Font font = graphics2D.getFont();
        FontRenderContext frc = graphics2D.getFontRenderContext();
        LineMetrics lm = font.getLineMetrics("0", frc);

        String s = String.format("%.1f", xMin);
        double x = offset.x + xScale * xMin;
        graphics2D.drawString(s, (float) x, (float) origin.y + lm.getAscent());

        s = String.format("%.1f", xMax);
        float width = (float) font.getStringBounds(s, frc).getWidth();
        x = offset.x + xScale * xMax;
        graphics2D.drawString(s, (float) x - width, (float) origin.y + lm.getAscent());

        s = String.format("%.1f", yMin);
        double y = offset.y + yScale * yMin;
        graphics2D.drawString(s, (float) origin.x + 1, (float) y + lm.getAscent());

        s = String.format("%.1f", yMax);
        y = offset.y + yScale * yMax;
        graphics2D.drawString(s, (float) origin.x + 1, (float) y);

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

    private void addLagrangePoint(double x, double y) {
        this.lagrangeLength += 1;
        this.lagrangeX[this.lagrangeLength] = x;
        this.lagrangeY[this.lagrangeLength] = y;
    }

    public void saveSVG(String path) {
        try {
            // Finally, stream out SVG to the standard output using
            // UTF-8 encoding.
            Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(path), "utf-8"));

            svgGenerator.stream(document.getDocumentElement(), out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSVG(String path) {
        InputStream in = null;
        try {
            in = new FileInputStream(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (in != null) {
            try {
                String parser = XMLResourceDescriptor.getXMLParserClassName();
                SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
                String uri = "http://www.w3.org/2000/svg";
                document = f.createSVGDocument(uri, in);
            } catch (IOException ex) {
                System.out.println("" + ex);
            }
        }

        svgGenerator = new SVGGraphics2D(document);
        svgGenerator.setSVGCanvasSize(dimension);
        svgGenerator.getRoot(document.getDocumentElement());
        // repaint
        jsvgCanvas.setSVGDocument(document);
    }
}