package lab04;

import java.util.ArrayList;

public class AddCommand implements Command {
    private Catalog catalog;
    AddCommand(Catalog catalog){
        this.catalog = catalog;
    }

    public void execute(ArrayList<String> data){
        String[] authors = new String[]{};
        if(data.size() > 3)
            authors = data.get(3).replace("\"", "").split(",");

        switch (data.get(0).split(" ")[1]){
            case "book":
                try {
                    if (data.size() < 4) throw new CustomException();
                }
                catch(CustomException CE){
                    System.out.println("Invalid command!");
                    break;
                }

                catalog.add(new Book(
                        data.get(1).replace("\"", ""),
                        data.get(2).split(" ")[0].replace("\"", ""),
                        Integer.parseInt(data.get(2).split(" ")[1]),
                        authors));
                break;

            case "article":
                try {
                    if (data.size() < 4) throw new CustomException();
                }
                catch(CustomException CE){
                    System.out.println("Invalid command!");
                    break;
                }

                catalog.add(new Article(
                        data.get(1).replace("\"", ""),
                        data.get(2).split(" ")[0].replace("\"", ""),
                        Integer.parseInt(data.get(2).split(" ")[1]),
                        authors));
                break;

            case "manual":
                try {
                    if (data.size() < 3) throw new CustomException();
                }
                catch(CustomException CE){
                    System.out.println("Invalid command!");
                    break;
                }

                catalog.add(new Manual(
                        data.get(1).replace("\"", ""),
                        data.get(2).replace("\"", "")));
                break;
            default:
                System.out.println("There's not such an item in this catalog!");
                break;
        }
    }
}
