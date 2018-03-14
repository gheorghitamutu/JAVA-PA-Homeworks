/*
    Author: Mutu Gheorghita
*/

package lab04;

public class Source {
    public static void main(String[] args){
        Catalog catalog = new Catalog();
        catalog.add(new Book("The Art of Computer Programming", "resources\\lab04\\books\\tacp.ps", 1967, "Donald E. Knuth"));
        catalog.add (new Article("Mastering the Game of Go without Human Knowledge", "resources\\lab04\\articles\\AlphaGo.pdf", 2017, "David Silver", "Julian Schrittwieser", "Karen Simonyan"));
        catalog.add (new Manual("Cookbook", "resources\\lab04\\stuff\\cookbook.doc"));
        catalog.save("resources\\lab04\\catalogs\\catalog.dat");
        catalog = Catalog.load("resources\\lab04\\catalogs\\catalog.dat");
        catalog.list();
        catalog.open("resources\\lab04\\catalogs\\catalog.dat");
    }
}
