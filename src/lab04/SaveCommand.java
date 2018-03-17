package lab04;

import java.util.ArrayList;

public class SaveCommand implements Command {
    private Catalog catalog;
    SaveCommand(Catalog catalog){
        this.catalog = catalog;
    }

    public void execute(ArrayList<String> data){
        catalog.save(data.get(1).replace("\"", ""));
    }
}
