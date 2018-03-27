package lab05;

import lab04.Catalog;
import org.apache.commons.io.FileUtils;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;

class XMLTree {

    public boolean saveToFile(String xmlPath) {
        if(!xmlPath.endsWith(".xml")){
            xmlPath += ".xml";
        }

        Catalog catalog = MainFrame.getInstance().getCatalog();
        Serializer serializer = new Persister();
        File file = new File(xmlPath);

        try {
            if(file.exists()) {
                FileUtils.forceDelete(file);
            }
            if(file.createNewFile()) {
                serializer.write(catalog, file);
            }
            else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
