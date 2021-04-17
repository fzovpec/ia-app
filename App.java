import models.DbManager;
import controllers.XmlReader;

import java.nio.file.Paths;

public class App {
    public static void main(String[] args) {
        // Db manager
        DbManager dbManager = new DbManager();
        dbManager.createOrConnectToDatabase("IADB");
        dbManager.createTables();
        // xml file reader
        String dataPath = Paths.get(System.getProperty("user.dir"), "data.xml").toString();
        new XmlReader().readXML(dataPath);
    }
}
