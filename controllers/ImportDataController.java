package controllers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class ImportDataController {
    public String chooseTheFile(JFrame frame){
        File file;
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(frame);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            file = fc.getSelectedFile();
            return file.getAbsoluteFile().toString();
        }

        return "";
    }

    public boolean verifyFile(String filePath){
        // verifies file format
        if (!filePath.contains(".xml")){
            return false;
        }

        // Verifies the syntax of the file
        try {
            File file = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("report");

            if (nodeList.getLength() <= 0){
                return false;
            }

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                Element element = (Element) node;
                // Verifying that all the files contain necessary elements
                if (element.getElementsByTagName("studID").getLength() != 1) return false;
                if (element.getElementsByTagName("studFN").getLength() != 1) return false;
                if (element.getElementsByTagName("studLN").getLength() != 1) return false;
                if (element.getElementsByTagName("courseID").getLength() != 1) return false;
                if (element.getElementsByTagName("courseName").getLength() != 1) return false;
                if (element.getElementsByTagName("sectID").getLength() != 1) return false;
                if (element.getElementsByTagName("year").getLength() != 1) return false;
                if (element.getElementsByTagName("term").getLength() != 1) return false;
                if (element.getElementsByTagName("sectName").getLength() != 1) return false;

                if (element.getElementsByTagName("bin1").getLength() != 1) return false;
                if (element.getElementsByTagName("bin2").getLength() != 1) return false;
                if (element.getElementsByTagName("bin3").getLength() != 1) return false;
                if (element.getElementsByTagName("bin5").getLength() != 1) return false;
                if (element.getElementsByTagName("bin6").getLength() != 1) return false;
                if (element.getElementsByTagName("bin7").getLength() != 1) return false;
                if (element.getElementsByTagName("bin8").getLength() != 1) return false;

                if (element.getElementsByTagName("coef").getLength() != 1) return false;
                if (element.getElementsByTagName("comment").getLength() != 1) return false;
            }

        } catch (Exception ex){
            System.out.println("Reading and verifying the xml file: " + ex.getMessage());
        }

        return true;
    }
}
