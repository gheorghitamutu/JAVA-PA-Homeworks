package lab05;

import javax.swing.*;

public class Source {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (UnsupportedLookAndFeelException e) {
            // handle exception
        } catch (ClassNotFoundException e) {
            // handle exception
        } catch (InstantiationException e) {
            // handle exception
        } catch (IllegalAccessException e) {
            // handle exception
        } finally {
            SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
            //SwingUtilities.invokeLater(() -> new TreePanel(new Catalog()).createAndShowGUI());
        }
    }
}
