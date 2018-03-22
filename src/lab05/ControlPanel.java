package lab05;

import lab04.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

class ControlPanel extends JPanel implements ActionListener{
    private final CatalogFrame catalogFrame;

    private final JButton newCatalogBtn = new JButton("New Catalog");
    private final JButton openCatalogBtn = new JButton("Open Catalog");
    private final JButton exitAppBtn = new JButton("Exit");
    private final JButton addDocumentBtn = new JButton("Add Document");
    private final JButton removeItemBtn = new JButton("Remove Document");
    private final JButton saveCatalogBtn = new JButton("Save Catalog");

    private final JLabel optionAddDocumentLabel = new JLabel("Add document");
    private final JLabel titleLabel = new JLabel("Title of the document");
    private final JTextField documentTitleTF = new JTextField();
    private final JLabel pathLabel = new JLabel("Path in the local file system");
    private final JTextField documentPathTF = new JTextField();
    private final JLabel yearLabel = new JLabel("Publication Year");
    private final JSpinner yearSpinner = new JSpinner(new SpinnerNumberModel(1950, 1900, 2017, 1));
    private final JButton addItemBtn = new JButton("Add to repository");

    private JFileChooser fc = new JFileChooser();

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    private Catalog catalog = new Catalog();

    ControlPanel(CatalogFrame catalogFrame){
        this.catalogFrame = catalogFrame;
        init();
    }

    private void init(){
        this.assignListeners();
        this.setMinimumSize();
        this.catalogFrame.getContentPane().setLayout(initialCatalogFrameMenu());
        this.catalogFrame.setLocationRelativeTo(null);
        this.catalogFrame.pack();
    }

    private GroupLayout initialCatalogFrameMenu() {
        GroupLayout groupLayout = new GroupLayout(this.catalogFrame.getContentPane());
        groupLayout.setAutoCreateContainerGaps(true);
        groupLayout.setAutoCreateGaps(true);

        groupLayout.setHorizontalGroup(
                groupLayout.createSequentialGroup()
                        .addComponent(newCatalogBtn)
                        .addComponent(openCatalogBtn)
                        .addComponent(exitAppBtn)
        );

        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(newCatalogBtn)
                                .addComponent(openCatalogBtn)
                                .addComponent(exitAppBtn)
                        )
        );

        return groupLayout;
    }

    private GroupLayout openCatalogFrameMenu(){
        GroupLayout groupLayout = new GroupLayout(this.catalogFrame.getContentPane());
        groupLayout.setAutoCreateContainerGaps(true);
        groupLayout.setAutoCreateGaps(true);

        groupLayout.setHorizontalGroup(
                groupLayout.createSequentialGroup()
                        .addComponent(saveCatalogBtn)
                        .addComponent(addDocumentBtn)
                        .addComponent(removeItemBtn)
                        .addComponent(exitAppBtn)
        );

        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(saveCatalogBtn)
                                .addComponent(addDocumentBtn)
                                .addComponent(removeItemBtn)
                                .addComponent(exitAppBtn)
                        )
        );

        return groupLayout;
    }

    private void addDocumentLayout(){
        this.catalogFrame.getContentPane().removeAll();

        this.catalogFrame.setLayout(new GridLayout(0, 1, 2, 2));

        this.catalogFrame.add(this.optionAddDocumentLabel);
        this.catalogFrame.add(this.titleLabel);
        this.catalogFrame.add(this.documentTitleTF);
        this.catalogFrame.add(this.pathLabel);
        this.catalogFrame.add(this.documentPathTF);
        this.catalogFrame.add(this.yearLabel);
        this.catalogFrame.add(this.yearSpinner);
        this.catalogFrame.add(this.addItemBtn);

        this.catalogFrame.add(this.catalogFrame.getList(), this.catalogFrame.getList().getBorder());
        this.catalogFrame.add(this.openCatalogBtn);
        this.catalogFrame.add(this.saveCatalogBtn);

        this.catalogFrame.pack();
    }


    public void actionPerformed(ActionEvent e) {
        int returnVal;

        if (e.getSource() == this.openCatalogBtn) {
            returnVal = this.fc.showOpenDialog(this.catalogFrame);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = this.fc.getSelectedFile();

                Catalog catalog = new LoadCommand(this.catalog).execute(file.getAbsolutePath());
                if(catalog != null){
                    this.catalog = catalog;

                    for(Item item: catalog.getItems()){
                        this.catalogFrame.getList().addDocument(item.toString());
                    }

                    this.catalogFrame.getContentPane().removeAll();
                    this.catalogFrame.getContentPane().setLayout(openCatalogFrameMenu());
                    this.catalogFrame.pack();
                }
                else {
                    JOptionPane.showMessageDialog(this.catalogFrame,
                            "The file is not a catalog file!",
                            "Error!", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else if (e.getSource() == this.saveCatalogBtn) {
            returnVal = this.fc.showSaveDialog(this.catalogFrame);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = this.fc.getSelectedFile();

                boolean saved = new SaveCommand(this.catalog).execute(file.getAbsolutePath());
                if(saved){
                    JOptionPane.showMessageDialog(this.catalogFrame,
                            "Catalog saved!",
                            "Succes!", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(this.catalogFrame,
                            "Error saving this catalog!",
                            "Error!", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else if(e.getSource() == this.addDocumentBtn){
            addDocumentLayout();
        }
        else if(e.getSource() == this.addItemBtn){
            String sb = "add article \"" +
                    documentTitleTF.getText() +
                    "\" \"" +
                    documentPathTF.getText() +
                    "\" " +
                    yearSpinner.getValue().toString() +
                    " \"No author, No author, No author\"";
            new AddCommand(this.catalog).execute(new ArrayList<>(Arrays.asList(sb.split(" \""))));
            //new ArrayList<>(Arrays.asList("add article \"Mastering the Game of Go without Human Knowledge\" \"resources\\lab04\\articles\\AlphaGo.pdf\" 2017 \"David Silver, Julian Schrittwieser, Karen Simonyan\"".split(" \"")
            this.catalogFrame.getList().addDocument(this.catalog.getItems().get(this.catalog.getItems().size() - 1).toString());

            documentTitleTF.setText("");
            documentPathTF.setText("");
            yearSpinner.setValue(2017);

            addDocumentLayout();
        }
        else if(e.getSource() == this.newCatalogBtn){
            this.catalog = new Catalog();
            this.catalogFrame.getContentPane().removeAll();
            this.catalogFrame.getContentPane().setLayout(openCatalogFrameMenu());
            this.catalogFrame.pack();
        }
        else if (e.getSource() == exitAppBtn) {
            System.exit(0);
        }
    }


    private void assignListeners(){
        this.newCatalogBtn.addActionListener(this);
        this.openCatalogBtn.addActionListener(this);
        this.saveCatalogBtn.addActionListener(this);
        this.addDocumentBtn.addActionListener(this);
        this.addItemBtn.addActionListener(this);
        this.exitAppBtn.addActionListener(this);

    }

    private void setMinimumSize(){
        this.catalogFrame.setMinimumSize(new Dimension(600, 300));
        this.catalogFrame.setMaximumSize(new Dimension(600, 300));

        this.newCatalogBtn.setMinimumSize(new Dimension(600, 20));
        this.newCatalogBtn.setAlignmentX(LEFT_ALIGNMENT);
        this.openCatalogBtn.setMinimumSize(new Dimension(600, 20));
        this.exitAppBtn.setMinimumSize(new Dimension(600, 20));
        this.addDocumentBtn.setMinimumSize(new Dimension(600, 20));
        this.removeItemBtn.setMinimumSize(new Dimension(600, 20));
        this.saveCatalogBtn.setMinimumSize(new Dimension(600, 20));

        this.optionAddDocumentLabel.setMinimumSize(new Dimension(600, 20));
        this.optionAddDocumentLabel.setAlignmentX(LEFT_ALIGNMENT);
        this.titleLabel.setMinimumSize(new Dimension(600, 20));
        this.titleLabel.setAlignmentX(LEFT_ALIGNMENT);
        this.documentTitleTF.setMinimumSize(new Dimension(600, 20));
        this.pathLabel.setMinimumSize(new Dimension(600, 20));
        this.documentPathTF.setMinimumSize(new Dimension(600, 20));
        this.yearLabel.setMinimumSize(new Dimension(600, 20));
        this.yearSpinner.setMinimumSize(new Dimension(600, 20));
        this.addItemBtn.setMinimumSize(new Dimension(600, 20));
    }

}
