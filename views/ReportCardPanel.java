package views;

import controllers.ImportDataController;
import controllers.ReportCard;
import controllers.XmlReader;
import models.ReportCardModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportCardPanel {
    JPanel panel = new JPanel();
    ReportCardModel model = new ReportCardModel();

    public ReportCardPanel(ReportCard reportCard){
        JLabel studentName = new JLabel();
        String name = reportCard.studentFirstName + " " + reportCard.studentLastName;
        studentName.setText(name);

        JTextArea bin1 = new JTextArea("bin1");
        JTextArea bin2 = new JTextArea("bin2");
        JTextArea bin3 = new JTextArea("bin3");
        JTextArea bin4 = new JTextArea("bin4");
        JTextArea bin5 = new JTextArea("bin5");
        JTextArea bin6 = new JTextArea("bin6");
        JTextArea bin7 = new JTextArea("bin7");
        JTextArea bin8 = new JTextArea("bin8");
        JTextArea coef = new JTextArea("coef");
        JTextArea comment = new JTextArea("comment");
        JButton saveButton = new JButton("Save");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reportCard.bin1 = bin1.getText();
                reportCard.bin2 = bin2.getText();
                reportCard.bin3 = bin3.getText();
                reportCard.bin4 = bin4.getText();
                reportCard.bin5 = bin5.getText();
                reportCard.bin6 = bin6.getText();
                reportCard.bin7 = bin7.getText();
                reportCard.bin8 = bin8.getText();
                reportCard.coef = coef.getText();
                reportCard.comment = comment.getText();

                model.updateReportCard(reportCard);
            }
        });

        panel.add(studentName);
        panel.add(bin1);
        panel.add(bin2);
        panel.add(bin3);
        panel.add(bin4);
        panel.add(bin5);
        panel.add(bin6);
        panel.add(bin7);
        panel.add(bin8);
        panel.add(coef);
        panel.add(comment);
        panel.add(saveButton);
        panel.setPreferredSize(new Dimension(600, 800));
    }

    public JPanel getPanel() {
        return panel;
    }
}
