package views;

import controllers.ImportDataController;
import controllers.XmlReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.jar.JarEntry;

public class GUI {
    JFrame frame = new JFrame();

    public GUI(){
        JPanel menuPanel = new JPanel();
        JPanel contentPanel = new JPanel();

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

        JLabel pageTitle = new JLabel();
        pageTitle.setText("Report cards");
        pageTitle.setHorizontalAlignment(SwingConstants.LEFT);

        menuPanel.setBackground(new Color(62, 80, 180));
        menuPanel.add(importDataButton);
        menuPanel.setPreferredSize(new Dimension(170, 800));

        contentPanel.add(pageTitle);
        contentPanel.setPreferredSize(new Dimension(600, 800));

        frame.add(menuPanel);
        frame.add(contentPanel);
        frame.setLayout(new SpringLayout());
        frame.setPreferredSize(new Dimension(700, 800));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Ecole");
        frame.pack();
        frame.setVisible(true);
    }
}
