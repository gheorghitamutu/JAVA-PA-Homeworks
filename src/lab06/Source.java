package lab06;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class Source {
    public static void main(String[] args) {
        try {
            NimbusLookAndFeel laf = new NimbusLookAndFeel();
            UIManager.setLookAndFeel(laf);
        } catch (UnsupportedLookAndFeelException ignore) {
        } finally {
            SwingUtilities.invokeLater(() -> new DrawingFrame().setVisible(true));
        }
    }
}
