package lab04;

import java.util.ArrayList;

public class LoadCommand implements Command{
    private Catalog catalog;
    LoadCommand(Catalog catalog){
        this.catalog = catalog;
    }

    public void execute(ArrayList<String> data){
        this.catalog = Catalog.load(data.get(1).replace("\"", ""));
    }

    public Catalog execute(ArrayList<String> data, String dummy){
        return Catalog.load(data.get(1).replace("\"", ""));
    }
}
