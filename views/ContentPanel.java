package views;

import controllers.ReportCard;
import controllers.ScreenSwitcher;
import models.ReportCardModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContentPanel {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
    ScreenSwitcher screenSwitcher = new ScreenSwitcher();
    ReportCardModel model = new ReportCardModel();
    ReportCard [] reportCards;

    public ContentPanel(JFrame frame){
        JLabel pageTitle = new JLabel();
        pageTitle.setText("Report cards");
        pageTitle.setFont(new Font(null, 0, 18));
        panel.add(Box.createHorizontalGlue());
        panel.add(pageTitle);
        panel.add(Box.createHorizontalGlue());

        reportCards = model.getReportsData();

        for (int i = 0; i < reportCards.length; i++){
            JPanel reportCardPanel = new JPanel();

            ReportCard reportCard = reportCards[i];

            JLabel studentName = new JLabel(reportCard.studentFirstName + " " + reportCard.studentLastName);
            JLabel courseName = new JLabel(reportCard.courseName);
            JLabel sectionName = new JLabel(reportCard.sectionName);
            JButton openReportCardButton = new JButton("Modify");

            reportCardPanel.add(studentName);
            reportCardPanel.add(courseName);
            reportCardPanel.add(sectionName);
            reportCardPanel.add(openReportCardButton);

            JPanel thisPanel = this.getPanel();
            JPanel reportCardPanelOtherWindow = new ReportCardPanel(reportCard).getPanel();
            reportCardPanelOtherWindow.setVisible(false);
            frame.add(reportCardPanelOtherWindow);

            openReportCardButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    screenSwitcher.openReportCard(thisPanel, reportCardPanelOtherWindow);
                }
            });


            reportCardPanel.setPreferredSize(new Dimension(600, 30));
            panel.add(reportCardPanel);
        }

        panel.setPreferredSize(new Dimension(600, 800));
    }

    public JPanel getPanel(){
        return panel;
    }
}
