package lab04;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

public class InfoCommand implements Command {
    private Catalog catalog;
    InfoCommand(Catalog catalog){
        this.catalog = catalog;
    }


    public void execute(ArrayList<String> data){
        try {
            data.set(1, data.get(1).replace("\"", ""));
            if (data.get(1) == null || data.get(1).equals("")) throw new CustomException("Path not valid!");

            BasicFileAttributes attrs = Files.readAttributes(Paths.get(data.get(1)), BasicFileAttributes.class);
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("Path: %s!\n", new File(data.get(1)).getAbsolutePath()));
            sb.append(String.format("Creation Time: %s!\n", attrs.creationTime()));
            sb.append(String.format("Last Access Time: %s!\n", attrs.lastAccessTime()));
            sb.append(String.format("Last Modified Time: %s!\n", attrs.lastModifiedTime()));
            sb.append(String.format("Is Directory: %s!\n", attrs.isDirectory()));
            sb.append(String.format("Is Other: %s!\n", attrs.isOther()));
            sb.append(String.format("Is Regular File: %s!\n", attrs.isRegularFile()));
            sb.append(String.format("Is Symbolic Link: %s!\n", attrs.isSymbolicLink()));
            sb.append(String.format("Size: %s!\n", attrs.size()));

            System.out.println(sb);
        }
        catch (CustomException | IOException IOCE){
            System.out.println(IOCE.getMessage());
        }
    }
}
