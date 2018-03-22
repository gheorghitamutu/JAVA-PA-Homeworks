package lab05;

import javax.swing.*;
import java.awt.*;

class DocumentForm extends JPanel {
    private final CatalogFrame frame;
    private final ControlPanel control;

    private String itemAdded = "";

    DocumentForm(CatalogFrame frame){
        this.frame = frame;
        this.control = frame.getControl();
        init();
    }

    private void init(){
        this.setMinimumSize(new Dimension(600, 200));

    }

    private void addDocument(){
        this.frame.getList().addDocument(this.itemAdded);
    }


}
