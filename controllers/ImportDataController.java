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
        int BINS_TOTAL = 8;
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

            for (int i = 0; i < nodeList.getLength(); i++) { // Looping through each report element
                Node node = nodeList.item(i);
                Element element = (Element) node;
                // Verifying that all the files contain necessary elements
                if (element.getElementsByTagName("studID").getLength() != 1) return false;     // Verifying if student id is present and not empty
                if (element.getElementsByTagName("studFN").getLength() != 1) return false;     // Verifying if student first name is present and not empty
                if (element.getElementsByTagName("studLN").getLength() != 1) return false;     // Verifying if student last name is present and not empty
                if (element.getElementsByTagName("courseID").getLength() != 1) return false;   // Verifying if courseID is present and not empty
                if (element.getElementsByTagName("courseName").getLength() != 1) return false; // Verifying if courseName is present and not empty
                if (element.getElementsByTagName("sectID").getLength() != 1) return false;     // Verifying if sectID is present and not empty
                if (element.getElementsByTagName("year").getLength() != 1) return false;       // Verifying if year is present and not empty
                if (element.getElementsByTagName("term").getLength() != 1) return false;       // Verifying if term is present and not empty
                if (element.getElementsByTagName("sectName").getLength() != 1) return false;   // Verifying if sectName is present and not empty

                for (int j = 1; j <= BINS_TOTAL; j++) {
                    if (element.getElementsByTagName("bin" + j).getLength() == 0) return false; // Verifying if bin is present
                }

                if (element.getElementsByTagName("coef").getLength() != 1) return false;        // Verifying if coef is present and not empty
                if (element.getElementsByTagName("comment").getLength() != 1) return false;     // Verifying if comment is present and not empty
            }

        } catch (Exception ex){
            System.out.println("Reading and verifying the xml file: " + ex.getMessage());
        }

        return true;
    }
}
