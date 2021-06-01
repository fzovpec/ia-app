package views;

import controllers.ImportDataController;
import controllers.XmlReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavigationPanel{
    JPanel panel = new JPanel();

    public NavigationPanel(JFrame frame){
        JButton importDataButton = new JButton();
        importDataButton.setText("Import Data");
        importDataButton.setSize(100, 50);
        importDataButton.setBackground(new Color(0, 150, 136));
        importDataButton.setForeground(new Color(255, 255, 255));
        importDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImportDataController importDataController = new ImportDataController();
                String filePath = importDataController.chooseTheFile(frame);
                if (importDataController.verifyFile(filePath)){
                    XmlReader.readXML(filePath);
                }
                else{
                    JOptionPane.showMessageDialog(frame, "Unfortunately it's not possible to read this file");
                }
            }
        });

        JButton exportDataButton = new JButton();
        exportDataButton.setText("Export Data");
        exportDataButton.setSize(100, 50);
        exportDataButton.setBackground(new Color(250, 0, 87));
        exportDataButton.setForeground(new Color(255, 255, 255));

        panel.setBackground(new Color(62, 80, 180));
        panel.add(importDataButton);
        panel.add(exportDataButton);
        panel.setPreferredSize(new Dimension(170, 800));
    }

    public JPanel getPanel(){
        return panel;
    }
}
