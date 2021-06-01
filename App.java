import controllers.ReportCard;
import controllers.XmlExporter;
import models.ReportCardModel;
import views.GUI;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        new ReportCardModel().getReportsData();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUI gui = new GUI();
            }
        });
        ReportCard[] reportCards = new ReportCardModel().getReportsData();
        new XmlExporter().exportDataToXml(reportCards, "export.xml");
    }
}
