package lab05;

import lab04.SaveCommand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Paths;

public class BottomMenu
        extends JPanel
        implements ActionListener {
    private final JButton addDocumentBtn = new JButton("Add Document");
    private final JButton saveCatalogBtn = new JButton("Save Catalog");
    private final JButton saveCatalogXMLButton = new JButton("Save Catalog XML");
    private final JButton backBtn = new JButton("Back");

    private JFileChooser fc = new JFileChooser();

    BottomMenu(){
        super();

        this.init();
        this.assignListeners();
        this.setMinimumSize();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.saveCatalogBtn) {
            if (this.fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = this.fc.getSelectedFile();

                if(new SaveCommand(MainFrame.getInstance().getCatalog()).execute(file.getAbsolutePath())){
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
        else if (e.getSource() == this.saveCatalogXMLButton) {
            if (this.fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = this.fc.getSelectedFile();

                if (new XMLTree().saveToFile(file.getAbsolutePath())) {
                    JOptionPane.showMessageDialog(this,
                            "Catalog saved in XML format!",
                            "Succes!", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Error saving this catalog in XML format!",
                            "Error!", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else if(e.getSource() == this.addDocumentBtn){
            MainFrame.getInstance().changePanel(new ControlPanel());
        }
        else if(e.getSource() == this.backBtn){
            MainFrame.getInstance().changePanel(new MainMenuPanel());
        }
    }

    private void init() {
        this.fc.setCurrentDirectory(new File(Paths.get("").toAbsolutePath().toString()));

        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();

        this.setLayout(gridBagLayout);
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.ipadx = 240;
        gbc.ipady = 30;
        gridBagLayout.setConstraints(this, gbc);

        gbc.gridx = 0;
        gbc.ipadx = 65; // fix the 3 buttons bottom row size
        gbc.insets = new Insets(10, 10, 0, 10); // this crap makes gaps
        gridBagLayout.setConstraints(this.addDocumentBtn, gbc);
        this.add(this.addDocumentBtn, gbc);


        gbc.gridx = 2;
        gridBagLayout.setConstraints(this.saveCatalogBtn, gbc);
        this.add(this.saveCatalogBtn, gbc);

        gbc.gridx = 4;
        gridBagLayout.setConstraints(this.saveCatalogXMLButton, gbc);
        this.add(this.saveCatalogXMLButton, gbc);

        gbc.gridx = 6;
        gridBagLayout.setConstraints(this.backBtn, gbc);
        this.add(this.backBtn, gbc);
    }

    private void assignListeners(){
        this.saveCatalogBtn.addActionListener(this);
        this.saveCatalogXMLButton.addActionListener(this);
        this.addDocumentBtn.addActionListener(this);
        this.backBtn.addActionListener(this);
    }

    private void setMinimumSize(){
        this.addDocumentBtn.setMinimumSize(new Dimension(50, 20));
        this.saveCatalogBtn.setMinimumSize(new Dimension(50, 20));
        this.saveCatalogXMLButton.setMinimumSize(new Dimension(50, 20));
        this.backBtn.setMinimumSize(new Dimension(50, 20));
    }
}
