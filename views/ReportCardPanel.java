package views;

import controllers.ReportCard;

import javax.swing.*;
import java.awt.*;

public class ReportCardPanel {
    JPanel panel = new JPanel();

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
        panel.setPreferredSize(new Dimension(600, 800));
    }

    public JPanel getPanel() {
        return panel;
    }
}
