package lab05;

import lab04.AddCommand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

class ControlPanel extends JPanel implements ActionListener {

    private final JLabel optionAddDocumentLabel = new JLabel("Add document");
    private final JLabel titleLabel = new JLabel("Title of the document");
    private final JTextField documentTitleTF = new JTextField();
    private final JLabel pathLabel = new JLabel("Path in the local file system");
    private final JTextField documentPathTF = new JTextField();
    private final JLabel yearLabel = new JLabel("Publication Year");
    private final JSpinner yearSpinner = new JSpinner(new SpinnerNumberModel(2018, 1900, 2018, 1));
    private final JLabel authorLabel = new JLabel("Authors");
    private final JTextField authorTF = new JTextField();
    private final JButton addItemBtn = new JButton("Add to repository");
    private final JButton backBtn = new JButton("Back");

    private JFileChooser fc = new JFileChooser();

    ControlPanel(){
        init();
    }

    private void init(){
        this.fc.setCurrentDirectory(new File(Paths.get("").toAbsolutePath().toString()));
        this.assignListeners();
        this.setMinimumSize();
        this.addDocumentLayout();
    }

    private void addDocumentLayout(){
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        this.setPreferredSize(new Dimension(780, 600));
        this.setLayout(gridBagLayout);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;

        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 0, 10); // this crap makes gaps
        gridBagLayout.setConstraints(this.optionAddDocumentLabel, gbc);
        this.add(this.optionAddDocumentLabel, gbc);

        gbc.ipady = 8;
        gbc.gridy = 1;
        gridBagLayout.setConstraints(this.titleLabel, gbc);
        this.add(this.titleLabel, gbc);

        gbc.gridy = 2;
        gridBagLayout.setConstraints(this.documentTitleTF, gbc);
        this.add(this.documentTitleTF, gbc);

        gbc.gridy = 3;
        gridBagLayout.setConstraints(this.pathLabel, gbc);
        this.add(this.pathLabel, gbc);

        gbc.gridy = 4;
        gridBagLayout.setConstraints(this.documentPathTF, gbc);
        this.add(this.documentPathTF, gbc);

        gbc.gridy = 5;
        gridBagLayout.setConstraints(this.yearLabel, gbc);
        this.add(this.yearLabel, gbc);

        gbc.gridy = 6;
        gridBagLayout.setConstraints(this.yearSpinner, gbc);
        this.add(this.yearSpinner, gbc);

        gbc.gridy = 7;
        gridBagLayout.setConstraints(this.authorLabel, gbc);
        this.add(this.authorLabel, gbc);

        gbc.gridy = 8;
        gridBagLayout.setConstraints(this.authorTF, gbc);
        this.add(this.authorTF, gbc);

        gbc.gridy = 9;
        gbc.ipady = 200;
        JPanel table = new CatalogTable();
        gridBagLayout.setConstraints(table, gbc);
        this.add(table, gbc);

        gbc.gridy = 10;
        gbc.ipady = 20;
        gridBagLayout.setConstraints(this.addItemBtn, gbc);
        this.add(this.addItemBtn, gbc);

        gbc.gridy = 11;
        gridBagLayout.setConstraints(this.backBtn, gbc);
        this.add(this.backBtn, gbc);
    }


    public void actionPerformed(ActionEvent e) {
         if(e.getSource() == this.addItemBtn){
            String sb = "add article \"" +
                    documentTitleTF.getText() +
                    "\" \"" +
                    documentPathTF.getText() +
                    "\" " +
                    yearSpinner.getValue().toString() +
                    " \"" +
                    authorTF.getText() +
                    "\"";
            new AddCommand(MainFrame.getInstance().getCatalog())
                    .execute(new ArrayList<>(Arrays.asList(sb.split(" \""))));

            MainFrame.getInstance().getCatalogList()
                    .addDocument(MainFrame.getInstance().getCatalog().getItems()
                            .get(MainFrame.getInstance().getCatalog().getItems().size() - 1).toString());

            documentTitleTF.setText("");
            documentPathTF.setText("");
            yearSpinner.setValue(2018);

            MainFrame.getInstance().changePanel(new TreePanel());
        }
        else if(e.getSource() == this.backBtn) {
             documentTitleTF.setText("");
             documentPathTF.setText("");
             yearSpinner.setValue(2018);

             MainFrame.getInstance().changePanel(new TreePanel());
         }
    }


    private void assignListeners(){
        this.addItemBtn.addActionListener(this);
        this.backBtn.addActionListener(this);

        this.documentPathTF.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                if(documentPathTF.getText().equals("")) {
                    JFileChooser fc = new JFileChooser();
                    fc.setCurrentDirectory(new File(Paths.get("").toAbsolutePath().toString()));
                    if (fc.showOpenDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
                        documentPathTF.setText(fc.getSelectedFile().getAbsolutePath());
                    }
                    yearLabel.requestFocus();
                }
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {

            }
        });
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
        this.authorLabel.setMinimumSize(new Dimension(600, 20));
        this.authorTF.setMinimumSize(new Dimension(600, 20));
        this.addItemBtn.setMinimumSize(new Dimension(600, 20));
    }
}
