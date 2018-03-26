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

public class MainMenuPanel  extends JPanel implements ActionListener {
    private final MainFrame mainFrame = MainFrame.getInstance();

    private final JLabel catalogLabel = new JLabel("Catalog Manager");
    private final JButton newCatalogBtn = new JButton("New Catalog");
    private final JButton loadCatalogBtn = new JButton("Load Catalog");
    private final JButton openFileBtn = new JButton("Open File");
    private final JButton exitAppBtn = new JButton("Exit");

    private final KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);

    private JFileChooser fc = new JFileChooser();
    private Catalog catalog = MainFrame.getInstance().getCatalog();

    MainMenuPanel(){
        init();
    }

    private void init(){
        assignActions();
        designComponents();
        applyLayout();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.loadCatalogBtn) {
            if (this.fc.showOpenDialog(this.mainFrame) == JFileChooser.APPROVE_OPTION) {
                File file = this.fc.getSelectedFile();

                Catalog catalog = new LoadCommand(this.catalog).execute(file.getAbsolutePath());
                if(catalog != null){
                    this.catalog = catalog;

                    for(Item item: catalog.getItems()){
                        CatalogList.getInstance().addDocument(item.toString());
                    }
                    this.mainFrame.changePanel(new TreePanel(this.catalog));
                }
                else {
                    JOptionPane.showMessageDialog(this.mainFrame,
                            "The file is not a catalog file!",
                            "Error!", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else if(e.getSource() == this.newCatalogBtn){
            this.mainFrame.setCatalog(new Catalog());
            this.catalog = this.mainFrame.getCatalog();
            this.mainFrame.changePanel(new TreePanel(this.catalog));
        }
        else if (e.getSource() == this.openFileBtn) {
            if (this.fc.showOpenDialog(this.mainFrame) == JFileChooser.APPROVE_OPTION) {
                File file = this.fc.getSelectedFile();
                new OpenCommand(this.catalog).execute(file.getAbsolutePath());
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
        gbc.insets = new Insets(10, 10, 0, 10); // this crap makes gaps
        catalogLabel.setHorizontalAlignment(JLabel.CENTER);
        gridBagLayout.setConstraints(catalogLabel, gbc);
        this.add(catalogLabel);

        gbc.gridy = 2;
        gbc.insets = new Insets(10, 10, 0, 10); // this crap makes gaps
        gridBagLayout.setConstraints(newCatalogBtn, gbc);
        this.add(newCatalogBtn);

        gbc.gridy = 4;
        gbc.insets = new Insets(40, 10, 0, 10); // this crap makes gaps
        gridBagLayout.setConstraints(loadCatalogBtn, gbc);
        this.add(loadCatalogBtn);


        gbc.gridy = 6;
        gbc.insets = new Insets(40, 10, 10, 10); // this crap makes gaps
        gridBagLayout.setConstraints(openFileBtn, gbc);
        this.add(openFileBtn);


        gbc.gridy = 8;
        gbc.insets = new Insets(40, 10, 10, 10); // this crap makes gaps
        gridBagLayout.setConstraints(exitAppBtn, gbc);
        this.add(exitAppBtn);
    }

    private void assignActions(){
        this.newCatalogBtn.addActionListener(this);
        this.loadCatalogBtn.addActionListener(this);
        this.openFileBtn.addActionListener(this);

        Action exitAction = mainFrame.getDisposeAction();
        this.mainFrame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        this.mainFrame.getRootPane().getActionMap().put("ESCAPE", exitAction);
        this.exitAppBtn.addActionListener(exitAction);
    }

    private void designComponents(){
        this.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        this.setMaximumSize(new Dimension(200, 60));
    }
}
