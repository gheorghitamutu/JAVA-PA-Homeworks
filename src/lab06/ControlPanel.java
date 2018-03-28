package lab06;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class ControlPanel
        extends JPanel
        implements ActionListener {
    private JButton loadBtn = new JButton("Load");
    private JButton saveBtn = new JButton("Save");
    private JButton resetBtn = new JButton("Reset");
    private JButton exitBtn = new JButton("Exit");

    private final KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);

    ControlPanel(){
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
        this.setPreferredSize(new Dimension(800, 100));
        this.setLayout(gridBagLayout);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;
        gbc.ipadx = 120;

        gbc.gridx = 0;
        gbc.insets = new Insets(10, 10, 0, 10); // this crap makes gaps
        gridBagLayout.setConstraints(this.loadBtn, gbc);
        this.add(this.loadBtn, gbc);

        gbc.gridx = 1;
        gridBagLayout.setConstraints(this.saveBtn, gbc);
        this.add(this.saveBtn, gbc);

        gbc.gridx = 2;
        gridBagLayout.setConstraints(this.resetBtn, gbc);
        this.add(this.resetBtn, gbc);

        gbc.gridx = 3;
        gridBagLayout.setConstraints(this.exitBtn, gbc);
        this.add(this.exitBtn, gbc);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.saveBtn){
            DrawingFrame.getInstance().getCanvas().saveCanvas();
        }
        else if(e.getSource() == this.loadBtn) {
            DrawingFrame.getInstance().getCanvas().loadCanvas();
        }
        else if (e.getSource() == this.resetBtn) {
            DrawingFrame.getInstance().getCanvas().resetCanvas();
        }
        else if(e.getSource() == this.exitBtn) {
            DrawingFrame.getInstance().getDisposeAction();
        }
    }

    private void assignListeners() {
        this.saveBtn.addActionListener(this);
        this.loadBtn.addActionListener(this);
        this.resetBtn.addActionListener(this);

        Action exitAction = DrawingFrame.getInstance().getDisposeAction();
        DrawingFrame.getInstance().getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        DrawingFrame.getInstance().getRootPane().getActionMap().put("ESCAPE", exitAction);
        this.exitBtn.addActionListener(exitAction);
        this.exitBtn.addActionListener(this);
    }

    private void setMinimumSize(){
        this.saveBtn.setMinimumSize(new Dimension(600, 20));
        this.loadBtn.setMinimumSize(new Dimension(600, 20));
        this.resetBtn.setMinimumSize(new Dimension(600, 20));
        this.exitBtn.setMinimumSize(new Dimension(600, 20));
    }
}
