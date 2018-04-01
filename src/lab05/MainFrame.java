package lab05;

import lab04.Catalog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

class MainFrame extends JFrame {
    private MainMenuPanel mainMenuPanel;

    // required to be initialized for open file command
    private Catalog catalog = new Catalog();

    CatalogList getCatalogList() {
        return catalogList;
    }

    private final CatalogList catalogList = CatalogList.getInstance();
    private static MainFrame instance;

    private GridBagLayout gridBagLayout = new GridBagLayout();
    private GridBagConstraints gbc = new GridBagConstraints();

        MainFrame(){
            super("Visual Document Manager");
            init();
        }

        private void init() {
            instance = this;
            this.mainMenuPanel = new MainMenuPanel();   // no final as forever recursion on new instance call if so
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.setMinimumSize(new Dimension(800, 700));
            this.setResizable(false);
            this.setGridBagLayout();

            this.changePanel(mainMenuPanel);

        }

        void changePanel(JPanel panel){
            for(Component component: this.getContentPane().getComponents()){ // solving removeAll() call which fucks up everything
                if(component.getClass() == JPanel.class || Component.class.isAssignableFrom(JPanel.class)){
                    component.setVisible(false);
                    this.remove(component);
                }
            }
            panel.setVisible(true);

            this.add(panel);
            this.pack();
        }

        public static MainFrame getInstance(){
            return instance; // instance will never be null as this is the first initialized class
        }

        private void setGridBagLayout(){
            this.gbc.gridwidth = 9;
            this.gbc.gridheight = 9;

            this.gbc.gridx = 3;
            this.gbc.gridy = 3;
            this.gridBagLayout.setConstraints(this.mainMenuPanel, this.gbc);
            this.setLayout(this.gridBagLayout);
        }

        Action getDisposeAction(){
            return new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    //default icon, custom title
                    int exit = JOptionPane.showConfirmDialog(
                            MainFrame.getInstance(),
                            "Exit app",
                            "Exit app?",
                            JOptionPane.YES_NO_OPTION);

                    if (exit == JOptionPane.YES_NO_OPTION) {
                        dispose();
                    }
                }
            };
        }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }
}
