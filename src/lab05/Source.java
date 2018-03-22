package lab05;

import javax.swing.*;

public class Source {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CatalogFrame().setVisible(true));
        }
}
