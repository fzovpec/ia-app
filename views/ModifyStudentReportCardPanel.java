package views;

import javax.swing.*;
import java.awt.*;

public class ModifyStudentReportCardPanel {
    JPanel panel = new JPanel();

    public ModifyStudentReportCardPanel(){
        JLabel pageTitle = new JLabel();
        pageTitle.setText("Testing string");

        panel.add(pageTitle);
        panel.setPreferredSize(new Dimension(600, 800));
    }

    public JPanel getPanel(){
        return panel;
    }
}
