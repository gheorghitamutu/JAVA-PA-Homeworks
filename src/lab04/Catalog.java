/*
    Author: Mutu Gheorghita
*/

package lab04;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Catalog implements Serializable {
    private List<Item> items = new ArrayList<>();

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    private String name = this.getClass().getName();
    private String developer = "gmutu";

    public void add(Item item){
        items.add(item);
    }

    public void save(String path){
        try {
            if (path == null || path.equals("")) throw new CustomException("Path not valid!");
        }
        catch (CustomException CE){
            System.out.println(CE.getMessage() + " Exiting program...");
            System.exit(0);
        }

        Serialize(this, path);
    }

    public static Catalog load(String path){
        try {
            if (path == null ||  !(new File(path).exists())) throw new CustomException("Path not valid!");;
        }
        catch (CustomException CE){
            System.out.println(CE.getMessage() + " Exiting program...");
            System.exit(0);
        }

        return Deserialize(path);
    }

    /**
     * Serializes the passed object to the passed filePath.
     *
     * @param object Object that is being serialized.
     * @param path File path where object should be stored.
     */
    private void Serialize(Object object, String path) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(object);
            fileOutputStream.close();
            objectOutputStream.close();
            System.out.println(String.format("SERIALIZED [%s]: %s", object.getClass().getName(), object));
        } catch (IOException exception) {
            // Output unexpected IOException.
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Deserializes object found in passed path.
     *
     * @param path Path to file where serialized object is found.
     */
    private static Catalog Deserialize(String path) {
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Catalog catalog = (Catalog) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            System.out.println(String.format("DESERIALIZED [%s]: %s", catalog.getClass().getName(), catalog));
            return catalog;
        } catch (IOException | ClassNotFoundException  exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    public void list(){
        StringBuilder sb;
        for(Item item: items){
            sb = new StringBuilder();

            sb.append(String.format("%s's title is %s!\n",
                    item.getClass().getName(), item.getTitle()));

            sb.append(String.format("%s's path is %s!\n", item.getClass().getName(),
                    new File(item.getPath()).getAbsolutePath()));

            sb.append(String.format("%s's year is %s!\n",
                    item.getClass().getName(), (item.getYear() != null) ? item.getYear().toString() : "unknown"));

            StringBuilder authorsStringList = new StringBuilder();
            for (String author : item.getAuthors()) {
                authorsStringList.append(author);
                if(!author.equals(item.getAuthors().get(item.getAuthors().size() - 1)))
                    authorsStringList.append(", ");
            }
            if(authorsStringList.length() == 0)
                authorsStringList.append("unknown");

            sb.append(String.format("%s's authors are %s!\n",
                    item.getClass().getName(), authorsStringList));

            System.out.println(sb);
        }

    }

    public void open(String path) {
        try {
            Desktop.getDesktop().open(new File(path));
        }
        catch (IOException IOE){
            System.out.println(IOE.getMessage());
        }
    }
}
