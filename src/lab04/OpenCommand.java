package lab04;

import java.util.ArrayList;

public class OpenCommand implements Command {
    private Catalog catalog;
    public OpenCommand(Catalog catalog){
        this.catalog = catalog;
    }

    public void execute(ArrayList<String> data){
        catalog.open(data.get(1).replace("\"", ""));
    }

    public void execute(String path){
        catalog.open(path);
    }
}
