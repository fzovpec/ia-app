import controllers.ReportCard;
import controllers.XmlExporter;
import models.DbManager;
import models.ReportCardModel;
import views.GUI;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        DbManager manager = new DbManager();
        manager.createOrConnectToDatabase("IADB");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUI gui = new GUI();
            }
        });
    }
}

//More students; Show the import, export button; full screen; menu bar; information; scroll; flexible bins maximum
// bins appear; on the right side filtering by classname, year, term; or on the left all classes, terms and years
// generate random students; additional information; two inputs one average, third - two averages