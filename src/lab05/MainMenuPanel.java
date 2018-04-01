package lab05;

import lab04.Catalog;
import lab04.Item;
import lab04.LoadCommand;
import lab04.OpenCommand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Paths;

public class MainMenuPanel
        extends JPanel
        implements ActionListener {

    private final JLabel catalogLabel = new JLabel("Catalog Manager");
    private final JButton newCatalogBtn = new JButton("New Catalog");
    private final JButton loadCatalogBtn = new JButton("Load Catalog");
    private final JButton openFileBtn = new JButton("Open File");
    private final JButton exitAppBtn = new JButton("Exit");

    private final KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);

    private JFileChooser fc = new JFileChooser();

    MainMenuPanel(){
        init();
    }

    private void init(){
        this.fc.setCurrentDirectory(new File(Paths.get("").toAbsolutePath().toString()));
        assignActions();
        designComponents();
        applyLayout();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.loadCatalogBtn) {
            if (this.fc.showOpenDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
                File file = this.fc.getSelectedFile();

                Catalog catalog = new LoadCommand(MainFrame.getInstance().getCatalog()).execute(file.getAbsolutePath());
                if(catalog != null){
                    MainFrame.getInstance().setCatalog(catalog);

                    for(Item item: catalog.getItems()){
                        CatalogList.getInstance().addDocument(item.toString());
                    }
                    MainFrame.getInstance().changePanel(new TreePanel());
                }
                else {
                    JOptionPane.showMessageDialog(MainFrame.getInstance(),
                            "The file is not a catalog file!",
                            "Error!", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else if(e.getSource() == this.newCatalogBtn){
            MainFrame.getInstance().setCatalog(new Catalog());
            MainFrame.getInstance().changePanel(new TreePanel());
        }
        else if (e.getSource() == this.openFileBtn) {
            if (this.fc.showOpenDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
                File file = this.fc.getSelectedFile();
                new OpenCommand(MainFrame.getInstance().getCatalog()).execute(file.getAbsolutePath());
            }
        }
        else if (e.getSource() == exitAppBtn) {
            System.exit(0);
        }
    }

    private void applyLayout(){
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();

        this.setLayout(gridBagLayout);

        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.ipadx = 250;
        gbc.ipady = 30;

        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 0, 10); // component padding
        catalogLabel.setHorizontalAlignment(JLabel.CENTER);
        gridBagLayout.setConstraints(catalogLabel, gbc);
        this.add(catalogLabel);

        gbc.gridy = 2;
        gridBagLayout.setConstraints(newCatalogBtn, gbc);
        this.add(newCatalogBtn);

        gbc.gridy = 4;
        gbc.insets = new Insets(40, 10, 0, 10);
        gridBagLayout.setConstraints(loadCatalogBtn, gbc);
        this.add(loadCatalogBtn);


        gbc.gridy = 6;
        gridBagLayout.setConstraints(openFileBtn, gbc);
        this.add(openFileBtn);


        gbc.gridy = 8;
        gbc.insets = new Insets(40, 10, 10, 10);
        gridBagLayout.setConstraints(exitAppBtn, gbc);
        this.add(exitAppBtn);
    }

    private void assignActions(){
        this.newCatalogBtn.addActionListener(this);
        this.loadCatalogBtn.addActionListener(this);
        this.openFileBtn.addActionListener(this);

        Action exitAction = MainFrame.getInstance().getDisposeAction();
        MainFrame.getInstance().getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        MainFrame.getInstance().getRootPane().getActionMap().put("ESCAPE", exitAction);
        this.exitAppBtn.addActionListener(exitAction);
    }

    private void designComponents(){
        this.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        this.setMaximumSize(new Dimension(200, 60));
    }
}
