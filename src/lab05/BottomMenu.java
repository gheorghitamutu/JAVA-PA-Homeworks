package lab05;

import lab04.Catalog;
import lab04.SaveCommand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class BottomMenu
        extends JPanel
        implements ActionListener {
    private final JButton addDocumentBtn = new JButton("Add Document");
    private final JButton saveCatalogBtn = new JButton("Save Catalog");

    private Catalog catalog;
    private JFileChooser fc = new JFileChooser();

    BottomMenu(Catalog catalog){
        super();
        this.catalog = catalog;
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();

        this.setLayout(gridBagLayout);
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.ipadx = 250;
        gbc.ipady = 30;
        gridBagLayout.setConstraints(this, gbc);

        gbc.gridx = 0;
        gbc.insets = new Insets(10, 10, 0, 10); // this crap makes gaps
        gridBagLayout.setConstraints(this.addDocumentBtn, gbc);
        this.add(this.addDocumentBtn, gbc);


        gbc.gridx = 2;
        gbc.insets = new Insets(10, 10, 0, 10); // this crap makes gaps
        gridBagLayout.setConstraints(this.saveCatalogBtn, gbc);
        this.add(this.saveCatalogBtn, gbc);

        this.assignListeners();
        this.setMinimumSize();
    }

    public void actionPerformed(ActionEvent e) {
        int returnVal;
        if (e.getSource() == this.saveCatalogBtn) {
            returnVal = this.fc.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = this.fc.getSelectedFile();

                boolean saved = new SaveCommand(this.catalog).execute(file.getAbsolutePath());
                if(saved){
                    JOptionPane.showMessageDialog(this,
                            "Catalog saved!",
                            "Succes!", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(this,
                            "Error saving this catalog!",
                            "Error!", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else if(e.getSource() == this.addDocumentBtn){
            MainFrame.getInstance().changePanel(new ControlPanel());
        }
    }

    private void assignListeners(){
        this.saveCatalogBtn.addActionListener(this);
        this.addDocumentBtn.addActionListener(this);
    }

    private void setMinimumSize(){
        this.addDocumentBtn.setMinimumSize(new Dimension(70, 20));
        this.saveCatalogBtn.setMinimumSize(new Dimension(70, 20));
    }
}
