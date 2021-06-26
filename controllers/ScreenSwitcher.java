package controllers;

import javax.swing.*;

public class ScreenSwitcher {
    public void openReportCard(JPanel contentFrame, JPanel reportFrame){
        contentFrame.setVisible(false);
        reportFrame.setVisible(true);
    }
    public void closeReportCard(JPanel contentFrame, JPanel reportFrame){
        contentFrame.setVisible(true);
        reportFrame.setVisible(false);
    }
}
