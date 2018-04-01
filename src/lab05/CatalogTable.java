package lab05;

import lab04.Item;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

class CatalogTable extends JPanel {

    CatalogTable() {
        super(new GridLayout(1,0));

        final JTable table = new JTable();
        DefaultTableModel dtm = new DefaultTableModel(0, 0);

        String[] header = {"Title",
                "Path",
                "Year",
                "Authors"};

        dtm.setColumnIdentifiers(header);
        table.setModel(dtm);

        for(Item item: MainFrame.getInstance().getCatalog().getItems()) {
            dtm.addRow(new Object[]{item.getTitle(), item.getPath(), item.getYear(), item.getAuthors()});
        }

        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);
    }


}
