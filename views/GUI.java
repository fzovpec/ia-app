package views;

import controllers.ImportDataController;
import controllers.XmlReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    JFrame frame = new JFrame();

    public GUI(){
        JPanel menuPanel = new NavigationPanel(frame).getPanel();
        JPanel contentPanel = new ContentPanel(frame).getPanel();

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
