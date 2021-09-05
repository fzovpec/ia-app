package views;

import controllers.ReportCard;
import models.ReportCardModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifyMessage {
    JFrame frame = new JFrame();

    public ModifyMessage(int identifier){
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
        ReportCardModel reportCardModel = new ReportCardModel();
        ReportCard reportCard = reportCardModel.getReportsData()[identifier];
        JLabel label = new JLabel(reportCard.studentFirstName + " " + reportCard.studentLastName);

        JTextArea commentArea = new JTextArea(reportCard.comment);
        commentArea.setBounds(10,50, 200,200);
        JButton saveButton = new JButton("Save");
        saveButton.setBounds(10, 270, 100, 20);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String comment = commentArea.getText();
                reportCard.comment = comment;
                reportCardModel.updateReportCard(reportCard);
            }
        });

        frame.add(label);
        frame.add(Box.createRigidArea(new Dimension(0, 10)));
        frame.add(commentArea);
        frame.add(Box.createRigidArea(new Dimension(0, 10)));
        frame.add(saveButton);

        frame.setPreferredSize(new Dimension(300, 400));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle("Ecole");
        frame.pack();
        frame.setVisible(true);
    }
}
