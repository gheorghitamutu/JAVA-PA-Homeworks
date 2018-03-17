package lab04;

import java.util.ArrayList;

public class ListCommand implements Command{
    private Catalog catalog;
    ListCommand(Catalog catalog){
        this.catalog = catalog;
    }

    public void execute(ArrayList<String> data){

        catalog.list();
    }


}
