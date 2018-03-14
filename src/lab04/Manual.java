/*
    Author: Mutu Gheorghita
*/

package lab04;

import java.io.File;

class Manual extends Item {
    Manual(String title, String path){
        super(title, path);
    }

    @Override
    protected void validateParams(){
        try {
            if (getTitle() == null || getTitle().equals("")) throw new CustomException("Title not valid!");
            if (getPath() == null || !(new File(getPath()).exists())) throw new CustomException("Path not valid!");
        }
        catch (CustomException CE){
            System.out.println(CE.getMessage() + " Exiting program...");
            System.exit(0);
        }
    }
}
