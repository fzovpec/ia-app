import models.ContentPanelModel;
import models.DbManager;
import controllers.XmlReader;
import views.ContentPanel;
import views.GUI;

import javax.swing.*;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) {
        new ContentPanelModel().getReportsData();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUI gui = new GUI();
            }
        });
    }
}
