package lab05;

import lab04.AddCommand;
import lab04.Catalog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

class ControlPanel extends JPanel implements ActionListener {
    private final MainFrame catalogFrame;

    private final JLabel optionAddDocumentLabel = new JLabel("Add document");
    private final JLabel titleLabel = new JLabel("Title of the document");
    private final JTextField documentTitleTF = new JTextField();
    private final JLabel pathLabel = new JLabel("Path in the local file system");
    private final JTextField documentPathTF = new JTextField();
    private final JLabel yearLabel = new JLabel("Publication Year");
    private final JSpinner yearSpinner = new JSpinner(new SpinnerNumberModel(1950, 1900, 2017, 1));
    private final JButton addItemBtn = new JButton("Add to repository");

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    private Catalog catalog;

    ControlPanel(){
        this.catalogFrame = MainFrame.getInstance();
        this.catalog = catalogFrame.getCatalog();
        init();
    }

    private void init(){
        this.assignListeners();
        this.setMinimumSize();
        addDocumentLayout();
    }

    private void addDocumentLayout(){
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        this.setPreferredSize(new Dimension(780, 600));
        this.setLayout(gridBagLayout);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 0, 10); // this crap makes gaps
        gridBagLayout.setConstraints(this.optionAddDocumentLabel, gbc);
        this.add(this.optionAddDocumentLabel, gbc);

        gbc.gridy = 2;
        gridBagLayout.setConstraints(this.titleLabel, gbc);
        this.add(this.titleLabel, gbc);

        gbc.gridy = 4;
        gridBagLayout.setConstraints(this.documentTitleTF, gbc);
        this.add(this.documentTitleTF, gbc);

        gbc.gridy = 6;
        gridBagLayout.setConstraints(this.pathLabel, gbc);
        this.add(this.pathLabel, gbc);

        gbc.gridy = 8;
        gridBagLayout.setConstraints(this.documentPathTF, gbc);
        this.add(this.documentPathTF, gbc);

        gbc.gridy = 10;
        gridBagLayout.setConstraints(this.yearLabel, gbc);
        this.add(this.yearLabel, gbc);

        gbc.gridy = 12;
        gridBagLayout.setConstraints(this.yearSpinner, gbc);
        this.add(this.yearSpinner, gbc);

        gbc.gridy = 14;
        gbc.weighty = 0.2;
        gbc.ipady = 20;
        gridBagLayout.setConstraints(this.addItemBtn, gbc);
        this.add(this.addItemBtn, gbc);
    }


    public void actionPerformed(ActionEvent e) {
         if(e.getSource() == this.addItemBtn){
            String sb = "add article \"" +
                    documentTitleTF.getText() +
                    "\" \"" +
                    documentPathTF.getText() +
                    "\" " +
                    yearSpinner.getValue().toString() +
                    " \"No author, No author, No author\"";
            new AddCommand(this.catalog).execute(new ArrayList<>(Arrays.asList(sb.split(" \""))));
            //new ArrayList<>(Arrays.asList("add article \"Mastering the Game of Go without Human Knowledge\" \"resources\\lab04\\articles\\AlphaGo.pdf\" 2017 \"David Silver, Julian Schrittwieser, Karen Simonyan\"".split(" \"")
            this.catalogFrame.getCatalogList().addDocument(this.catalog.getItems().get(this.catalog.getItems().size() - 1).toString());

            documentTitleTF.setText("");
            documentPathTF.setText("");
            yearSpinner.setValue(2017);

            MainFrame.getInstance().changePanel(new TreePanel(this.catalog));
        }
    }


    private void assignListeners(){
        this.addItemBtn.addActionListener(this);

    }

    private void setMinimumSize(){
        this.optionAddDocumentLabel.setPreferredSize(new Dimension(780, 20));
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
