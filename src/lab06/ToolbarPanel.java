package lab06;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolbarPanel
        extends JPanel
        implements ActionListener {
    private final JButton drawBtn = new JButton("Draw shape");

    JCheckBox getAllRandomBtn() {
        return allRandomBtn;
    }

    private final JCheckBox allRandomBtn = new JCheckBox("All random");
    private final JCheckBox drawWhileMouseDraggedBtn = new JCheckBox("Draw while dragged");

    JSpinner getSidesSpinner() {
        return sidesSpinner;
    }

    private final JSpinner sidesSpinner = new JSpinner(new SpinnerNumberModel(3, 3, 60, 1));

    JSpinner getRadiusSpinner() {
        return radiusSpinner;
    }

    private final JSpinner radiusSpinner = new JSpinner(new SpinnerNumberModel(3, 3, 60, 1));

    private Canvas canvas;

    private final JButton drawFunctionGraphBtn = new JButton("Draw Function Graph");
    private final JTextField functionTF = new JTextField();
    private final JSpinner functionLengthSpinner = new JSpinner(new SpinnerNumberModel(3, 3, 10000, 1));

    private final JButton drawLagrange = new JButton("Draw Lagrange");
    private final JCheckBox storePoints = new JCheckBox("Store points");

    ToolbarPanel(Canvas canvas) {
        this.canvas = canvas;
        init();
    }

    private void init() {
        this.assignListeners();
        this.setMinimumSize();
        this.addDocumentLayout();
    }

    private void addDocumentLayout() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        this.setPreferredSize(new Dimension(800, 200));
        this.setLayout(gridBagLayout);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;
        gbc.ipadx = 140;

        gbc.gridx = 0;
        gbc.insets = new Insets(10, 10, 0, 10); // this crap makes gaps
        gridBagLayout.setConstraints(this.drawBtn, gbc);
        this.add(this.drawBtn, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.ipadx = 10;
        gridBagLayout.setConstraints(this.allRandomBtn, gbc);
        this.add(this.allRandomBtn, gbc);

        gbc.gridx = 2;
        gridBagLayout.setConstraints(this.drawWhileMouseDraggedBtn, gbc);
        this.add(this.drawWhileMouseDraggedBtn, gbc);

        gbc.gridx = 3;
        gridBagLayout.setConstraints(this.sidesSpinner, gbc);
        this.add(this.sidesSpinner, gbc);

        gbc.gridx = 4;
        gridBagLayout.setConstraints(this.radiusSpinner, gbc);
        this.add(this.radiusSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gridBagLayout.setConstraints(this.drawFunctionGraphBtn, gbc);
        this.add(this.drawFunctionGraphBtn, gbc);

        gbc.gridx = 1;
        gridBagLayout.setConstraints(this.functionTF, gbc);
        this.add(this.functionTF, gbc);

        gbc.gridx = 2;
        gridBagLayout.setConstraints(this.functionLengthSpinner, gbc);
        this.add(this.functionLengthSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gridBagLayout.setConstraints(this.drawLagrange, gbc);
        this.add(this.drawLagrange, gbc);

        gbc.gridx = 1;
        gridBagLayout.setConstraints(this.storePoints, gbc);
        this.add(this.storePoints, gbc);
    }

    JCheckBox getStorePoints() {
        return storePoints;
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.drawBtn){
            if(allRandomBtn.isSelected()) {
                this.canvas.getBlankArea().drawShapeAllRandom();
            }
            else {
                this.canvas.getBlankArea().drawShapeAtRandomLocation();
            }
        }
        else if(e.getSource() == this.allRandomBtn) {
            this.sidesSpinner.setEnabled(!this.allRandomBtn.isSelected());
            this.radiusSpinner.setEnabled(!this.allRandomBtn.isSelected());
        }
        else if (e.getSource() == this.drawWhileMouseDraggedBtn) {
            this.canvas.getMouseListener().setDrawWhileDragged(this.drawWhileMouseDraggedBtn.isSelected());
        }
        else if (e.getSource() == this.drawFunctionGraphBtn) {
            String exp = this.functionTF.getText();
            String length = this.functionLengthSpinner.getValue().toString();
            this.canvas.getBlankArea().drawFunctionGraph(exp, Integer.parseInt(length) + 1);
        }
        else if (e.getSource() == this.storePoints) {
            this.drawLagrange.setEnabled(this.storePoints.isSelected());
            int maxPointsLagrange = 1000;
            int graphLength = Integer.parseInt(this.functionLengthSpinner.getValue().toString()) + 1;
            DrawingFrame.getInstance().getCanvas().getBlankArea().setLagrangeX(new double[maxPointsLagrange]);
            DrawingFrame.getInstance().getCanvas().getBlankArea().setLagrangeY(new double[maxPointsLagrange]);
            DrawingFrame.getInstance().getCanvas().getBlankArea().setLagrangeGraphLength(graphLength);
            
            // first point will add to lagrangeLength +1
            DrawingFrame.getInstance().getCanvas().getBlankArea().initLagrangeLength();
        }
        else if (e.getSource() == this.drawLagrange) {
            DrawingFrame.getInstance().getCanvas().resetCanvas();
            this.canvas.getBlankArea().drawLagrangeGraph();
            this.storePoints.setSelected(false);
            this.drawLagrange.setEnabled(false);
        }
    }

    private void assignListeners() {
        this.drawBtn.addActionListener(this);
        this.allRandomBtn.addActionListener(this);
        this.drawWhileMouseDraggedBtn.addActionListener(this);
        this.drawFunctionGraphBtn.addActionListener(this);
        this.functionTF.addActionListener(this);
        this.drawLagrange.addActionListener(this);
        this.storePoints.addActionListener(this);
    }

    private void setMinimumSize(){
        this.drawBtn.setMinimumSize(new Dimension(40, 20));
        this.allRandomBtn.setMinimumSize(new Dimension(40, 20));
        this.drawWhileMouseDraggedBtn.setMinimumSize(new Dimension(40, 20));
        this.sidesSpinner.setMinimumSize(new Dimension(40,20));
        this.radiusSpinner.setMinimumSize(new Dimension(40, 20));
        this.drawFunctionGraphBtn.setMinimumSize(new Dimension(40, 20));
        this.functionTF.setMinimumSize(new Dimension(40, 20));
        this.functionLengthSpinner.setMinimumSize(new Dimension(20, 20));
        this.storePoints.setMinimumSize(new Dimension(40, 40));
        this.drawLagrange.setMinimumSize(new Dimension(40, 40));
        this.drawLagrange.setEnabled(false);
    }
}
