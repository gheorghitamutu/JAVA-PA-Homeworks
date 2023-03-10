package lab05;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class Source {
    public static void main(String[] args) {
        try {
            NimbusLookAndFeel laf = new NimbusLookAndFeel();
            UIManager.setLookAndFeel(laf);
            UIDefaults defs = laf.getDefaults();
            defs.put("Tree.drawHorizontalLines", true);
            defs.put("Tree.drawVerticalLines", true);
            defs.put("Tree.linesStyle", "dashed");

        } catch (UnsupportedLookAndFeelException e) {
            // do nothing
        } finally {
            SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
        }
    }
}
