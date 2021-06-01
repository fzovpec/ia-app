package views;

import controllers.ReportCard;
import models.ReportCardModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class ContentPanel {
    JPanel panel = new JPanel();
    ReportCardModel model = new ReportCardModel();
    ReportCard [] reportCards;

    DefaultListModel<String> reportCardsListModel = new DefaultListModel<String>();
    JList list = new JList(reportCardsListModel);

    public ContentPanel(){
        JLabel pageTitle = new JLabel();
        pageTitle.setText("Report cards");
        pageTitle.setHorizontalAlignment(SwingConstants.LEFT);

        reportCards = model.getReportsData();
        System.out.println(reportCards.length);
        for (int i = 0; i < reportCards.length; i++){
            ReportCard reportCard = reportCards[i];
            String dataStringForTheList = String.format("%s %s %s %s", reportCard.studentFirstName,
                    reportCard.studentLastName, reportCard.courseName, reportCard.sectionName);
            reportCardsListModel.addElement(dataStringForTheList);
        }

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedIndex = list.getSelectedIndex();

                }
            }
        });

        panel.add(pageTitle);
        panel.add(list);
        panel.setPreferredSize(new Dimension(600, 800));
    }

    public JPanel getPanel(){
        return panel;
    }
}
