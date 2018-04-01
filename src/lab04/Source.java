/*
    Author: Mutu Gheorghita
*/

package lab04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Source {
    public static void main(String[] args){
        /* Compulsory*/
        Catalog catalog = new Catalog();
        catalog.add(new Book("The Art of Computer Programming", "resources\\lab04\\books\\tacp.ps", 1967, "Donald E. Knuth"));
        catalog.add (new Article("Mastering the Game of Go without Human Knowledge", "resources\\lab04\\articles\\AlphaGo.pdf", 2017, "David Silver", "Julian Schrittwieser", "Karen Simonyan"));
        catalog.add (new Manual("Cookbook", "resources\\lab04\\stuff\\cookbook.doc"));
        /*catalog.save("resources\\lab04\\catalogs\\catalog.dat");
        catalog = Catalog.load("resources\\lab04\\catalogs\\catalog.dat");
        catalog.list();
        catalog.open("resources\\lab04\\catalogs\\catalog.dat");
        */

        //Catalog catalog = new Catalog();
        //ArrayList<String> UI = new ArrayList<>(Arrays.asList("add article \"Mastering the Game of Go without Human Knowledge\" \"resources\\lab04\\articles\\AlphaGo.pdf\" 2017 \"David Silver, Julian Schrittwieser, Karen Simonyan\"".split(" \"")));
        //new AddCommand(catalog).execute(UI);
        //ArrayList<String> UI = new ArrayList<>(Arrays.asList("info \"resources\\lab04\\articles\\AlphaGo.pdf\"".split(" \"")));
        //new InfoCommand().execute(UI);

        //ArrayList<String> UI = new ArrayList<>(Arrays.asList("report html \"resources\\lab04\\reports\\htmlreports\\report.html\"".split(" \"")));
        //new ReportCommand(catalog).execute(UI);

        //ArrayList<String> UI = new ArrayList<>(Arrays.asList("open \"resources\\lab04\\reports\\htmlreports\\report.html\"".split(" \"")));
        //new OpenCommand(catalog).execute(UI);

        //ArrayList<String> UI = new ArrayList<>(Arrays.asList("save \"resources\\\\lab04\\\\catalogs\\\\catalog.dat\"".split(" \"")));
        //new SaveCommand(catalog).execute(UI);

        //ArrayList<String> UI = new ArrayList<>(Arrays.asList("report docx \"resources\\lab04\\reports\\docxreports\\report.docx\"".split(" \"")));
        //new ReportCommand(catalog).execute(UI);
        //System.exit(0);

        Scanner scan = new Scanner(System.in);
        ArrayList<String> userInput = new ArrayList<>(Arrays.asList(scan.nextLine().split(" \"")));
        while (!userInput.get(0).split(" ")[0].equals("exit")){
            switch(userInput.get(0).split(" ")[0]){
                case "add":
                    new AddCommand(catalog).execute(userInput);
                    break;
                case "list":
                    new ListCommand(catalog).execute(userInput);
                    break;
                case "open":
                    new OpenCommand(catalog).execute(userInput);
                    break;
                case "save":
                    new SaveCommand(catalog).execute(userInput);
                    break;
                case "load":
                    new LoadCommand(catalog).execute(userInput, "");
                    break;
                case "info":
                    new InfoCommand().execute(userInput);
                    break;
                case "report":
                    new ReportCommand(catalog).execute(userInput);
                    break;
                default:
                    System.out.println(
                            String.format("There s no command called %s!", userInput.get(0).split(" ")[0]));
                    break;
            }
            userInput = new ArrayList<>(Arrays.asList(scan.nextLine().split(" \"")));
        }
    }
}
