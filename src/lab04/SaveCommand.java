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