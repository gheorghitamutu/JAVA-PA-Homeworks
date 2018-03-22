package lab04;

import java.util.ArrayList;

public class SaveCommand implements Command {
    private Catalog catalog;
    public SaveCommand(Catalog catalog){
        this.catalog = catalog;
    }

    public void execute(ArrayList<String> data){
        catalog.save(data.get(1).replace("\"", ""));
    }

    public boolean execute(String path){
        return catalog.save(path);
    }
}


/*
this.catalogFrame.getContentPane().removeAll();

        BoxLayout boxLayout = new BoxLayout(this.catalogFrame.getForm(), BoxLayout.Y_AXIS);
        this.catalogFrame.getForm().setLayout(boxLayout);

        this.catalogFrame.addComponentToForm(this.optionAddDocumentLabel);
        this.catalogFrame.add(this.titleLabel);
        this.catalogFrame.getForm().add(this.documentTitleTF);
        this.catalogFrame.getForm().add(this.pathLabel);
        this.catalogFrame.getForm().add(this.documentPathTF);
        this.catalogFrame.getForm().add(this.yearLabel);
        this.catalogFrame.getForm().add(this.yearSpinner);
        this.catalogFrame.getForm().add(this.addItemBtn);

        this.catalogFrame.getForm().add(this.catalogFrame.getList(), this.catalogFrame.getList().getBorder());
        this.catalogFrame.getForm().add(this.openCatalogBtn);
        this.catalogFrame.getForm().add(this.saveCatalogBtn);

        this.catalogFrame.getForm().setVisible(true);
        this.catalogFrame.add(this.catalogFrame.getForm());
        this.catalogFrame.pack();
        this.catalogFrame.getForm().setVisible(true);
 */