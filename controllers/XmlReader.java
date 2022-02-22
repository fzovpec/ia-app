package controllers;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import models.XmlReaderModel;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class XmlReader {
    public static void readXML(String XMLFilePath){
        int BINS_TOTAL = 8;
        XmlReaderModel model = new XmlReaderModel();
        try{
            // Reading file
            File file = new File(XMLFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            // Getting all the reports into the list
            NodeList nodeList = doc.getElementsByTagName("report");
            // Iterating over all the reports
            for (int i = 0; i < nodeList.getLength(); i++){
                Node node = nodeList.item(i);
                Element element = (Element) node;
                try { // If exception regarding parsing integers from studID, courseID, sectID, year, term
                    // Reading studID, studFN, studLN, courseID, courseName, sectID, year, term, sectName
                    int studentId = Integer.parseInt(element.getElementsByTagName("studID").item(0).getTextContent());
                    String firstName = element.getElementsByTagName("studFN").item(0).getTextContent();
                    String lastName = element.getElementsByTagName("studLN").item(0).getTextContent();
                    int courseId = Integer.parseInt(element.getElementsByTagName("courseID").item(0).getTextContent());
                    String courseName = element.getElementsByTagName("courseName").item(0).getTextContent();
                    int sectionId = Integer.parseInt(element.getElementsByTagName("sectID").item(0).getTextContent());
                    int year = Integer.parseInt(element.getElementsByTagName("year").item(0).getTextContent());
                    int term = Integer.parseInt(element.getElementsByTagName("term").item(0).getTextContent());
                    String sectionName = element.getElementsByTagName("sectName").item(0).getTextContent();
                    // Reading all the bins from XML
                    int[] bins = new int[BINS_TOTAL];
                    for(int j = 0; j < BINS_TOTAL; j++){
                        bins[j] = Integer.parseInt(element.getElementsByTagName("bin" + (j + 1)).item(0).getTextContent());
                    }
                    int coef = Integer.parseInt(element.getElementsByTagName("coef").item(0).getTextContent());
                    String comment = element.getElementsByTagName("comment").item(0).getTextContent();

                    // Inserting data into a database
                    model.insertStudentRecord(studentId, firstName, lastName);
                    model.insertCourse(courseId, courseName);
                    model.insertSection(sectionId, year, term, sectionName, courseId);
                    model.insertReport(studentId, sectionId, bins, coef, comment);
                }
                catch (NumberFormatException e){
                    // If exception regarding parsing integer values is arising, looping will go to the next report card
                }
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
