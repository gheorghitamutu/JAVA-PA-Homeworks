package lab06;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

class DrawingFrame extends JFrame {
    private static DrawingFrame instance;

    private GridBagLayout gridBagLayout = new GridBagLayout();
    private GridBagConstraints gbc = new GridBagConstraints();

    Canvas getCanvas() {
        return canvas;
    }

    ToolbarPanel getToolbarPanel() {
        return toolbarPanel;
    }

    private ToolbarPanel toolbarPanel;

    private Canvas canvas = new Canvas();

        DrawingFrame(){
            super("Canvas Manager");
            init();
        }

        private void init() {
            instance = this;

            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.setMinimumSize(new Dimension(800, 700));
            this.setResizable(false);
            this.setGridBagLayout();

            this.changePanel(this.canvas, false, 0, 0);
            this.toolbarPanel = new ToolbarPanel(this.canvas);
            this.changePanel(this.toolbarPanel, false, 0, 3);
            this.changePanel(new ControlPanel(), false, 0, 8);
        }

        private void changePanel(JPanel panel, boolean removePreviousPanels, int gridx, int gridy){
            if(removePreviousPanels) {
                for (Component component : this.getContentPane().getComponents()) { // solving removeAll() call which fucks up everything
                    if (component.getClass() == JPanel.class || Component.class.isAssignableFrom(JPanel.class)) {
                        component.setVisible(false);
                        this.remove(component);
                    }
                }
            }

            panel.setVisible(true);
            this.gbc.gridy = gridy;
            this.gbc.gridx = gridx;
            this.gridBagLayout.setConstraints(panel, this.gbc);
            this.add(panel, gbc);
            this.pack();
        }

        public static DrawingFrame getInstance(){
            return instance; // instance will never be null as this is the first initialized class
        }

        private void setGridBagLayout(){
            this.gbc.gridwidth = 1;
            this.gbc.gridheight = 1;

            this.gbc.gridx = 1;
            this.gbc.gridy = 1;
            this.setLayout(this.gridBagLayout);
        }

        Action getDisposeAction(){
            return new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    //default icon, custom title
                    int exit = JOptionPane.showConfirmDialog(
                            DrawingFrame.getInstance(),
                            "Exit app",
                            "Exit app?",
                            JOptionPane.YES_NO_OPTION);

                    if (exit == JOptionPane.YES_NO_OPTION) {
                        dispose();
                    }
                }
            };
        }
}
