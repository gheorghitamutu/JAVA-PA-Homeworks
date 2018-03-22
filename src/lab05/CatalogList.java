package lab05;

import javax.swing.*;
import java.awt.*;

public class CatalogList extends JList {
    private DefaultListModel<Object> model = new DefaultListModel<>();

    public String getTitle() {
        return title;
    }

    private String title;
    CatalogList(){
        this.title = "<html><i><font color='blue'>" +
                "Catalog Documents" + "</font></i></html>";

        this.setBorder(BorderFactory.createTitledBorder(this.title));
        this.setModel(model);
        this.setMinimumSize(new Dimension(100, 100));
        this.setAlignmentX(CENTER_ALIGNMENT);

    }

    public void addDocument(String item){
        model.addElement(item);
    }
}
