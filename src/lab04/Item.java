/*
    Author: Mutu Gheorghita
*/

package lab04;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Item implements Serializable {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    private String title;
    private String path;
    private Integer year;
    private List<String> authors = new ArrayList<>();

    Item(String title, String path, Integer year, String... authors) {
        this.path = path;
        this.year = year;
        this.title = title;
        this.authors.addAll(new ArrayList<>(Arrays.asList(authors)));
        validateParams();
    }

    Item(String title, String path) {
        this.path = path;
        this.title = title;
        validateParams();
    }

    protected void validateParams(){
        try {
            if (title == null || title.equals("")) throw new CustomException("Title not valid!");
            if (year == null || year < 0) throw new CustomException("Year not valid!");
            if (path == null || !(new File(path).exists())) throw new CustomException("Path not valid!");
            for(String author: authors)
                if (author == null || author.equals("")) throw new CustomException("Author not valid!");
        }
        catch (CustomException CE){
            System.out.println(CE.getMessage() + " Exiting program...");
            System.exit(0);
        }
    }


}
