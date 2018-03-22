package lab05;

import javax.swing.*;
import java.awt.*;

public class CatalogFrame extends JFrame{
    public DocumentForm getForm() {
        return form;
    }

    public void addComponentToForm(Component comp){
        form.add(comp);
    }

    private DocumentForm form;

    public CatalogList getList() {
        return list;
    }

    private CatalogList list = new CatalogList();

    public ControlPanel getControl() {
        return control;
    }

    private ControlPanel control;

    CatalogFrame(){
        super("Visual Document Manager");
        init();
    }

    private void init(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.control = new ControlPanel(this);
        this.form = new DocumentForm(this);
    }
}
