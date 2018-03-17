package lab04;

import java.util.ArrayList;

public class ReportCommand implements Command {
    private Catalog catalog;
    ReportCommand(Catalog catalog){
        this.catalog = catalog;
    }

    public void execute(ArrayList<String> data) {
        String path = data.get(1).replace("\"", "");

        switch(data.get(0).split(" ")[1].toLowerCase()){
            case "html":
                new HTMLReport(catalog, path).create();
                break;
            case "pdf":
                // TO DO: pdf report class
                break;
            case "word":
                // TO DO: word document report class
                break;
            default:
                System.out.println("This format is not supported!");
        }
    }
}
