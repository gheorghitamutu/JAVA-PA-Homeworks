package lab06;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Paths;

public class ControlPanel
        extends JPanel
        implements ActionListener {
    private JButton loadBtn = new JButton("Load");
    private JButton saveBtn = new JButton("Save");
    private JButton resetBtn = new JButton("Reset");

    private JFileChooser fc = new JFileChooser();

    ControlPanel(){
        init();
    }

    private void init() {
        this.fc.setCurrentDirectory(new File(Paths.get("").toAbsolutePath().toString()));
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
        gridBagLayout.setConstraints(this.loadBtn, gbc);
        this.add(this.loadBtn, gbc);

        gbc.gridx = 1;
        gridBagLayout.setConstraints(this.saveBtn, gbc);
        this.add(this.saveBtn, gbc);

        gbc.gridx = 2;
        gridBagLayout.setConstraints(this.resetBtn, gbc);
        this.add(this.resetBtn, gbc);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.saveBtn){
            String sb = "";
        }
        else if(e.getSource() == this.loadBtn) {
            // do nothing
        }
        else if (e.getSource() == this.resetBtn) {
            // do nothing
        }
    }

    private void assignListeners() {
        this.saveBtn.addActionListener(this);
        this.loadBtn.addActionListener(this);
        this.resetBtn.addActionListener(this);
    }

    private void setMinimumSize(){
        this.saveBtn.setMinimumSize(new Dimension(600, 20));
        this.loadBtn.setMinimumSize(new Dimension(600, 20));
        this.resetBtn.setMinimumSize(new Dimension(600, 20));
    }
}
