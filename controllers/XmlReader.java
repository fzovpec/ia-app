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

                // Retrieving attributes listed in the file
                // Student attributes
                int studentId = Integer.parseInt(element.getElementsByTagName("studID").item(0).getTextContent());
                String firstName = element.getElementsByTagName("studFN").item(0).getTextContent();
                String lastName = element.getElementsByTagName("studLN").item(0).getTextContent();
                // Course attributes
                int courseId = Integer.parseInt(element.getElementsByTagName("courseID").item(0).getTextContent());
                String courseName = element.getElementsByTagName("courseName").item(0).getTextContent();
                // Section attributes
                int sectionId = Integer.parseInt(element.getElementsByTagName("sectID").item(0).getTextContent());
                int year = Integer.parseInt(element.getElementsByTagName("year").item(0).getTextContent());
                int term = Integer.parseInt(element.getElementsByTagName("term").item(0).getTextContent());
                String sectionName = element.getElementsByTagName("sectName").item(0).getTextContent();
                // Report attributes
                String note1 = element.getElementsByTagName("note1").item(0).getTextContent();
                String note2 = element.getElementsByTagName("note2").item(0).getTextContent();
                String compo = element.getElementsByTagName("compo").item(0).getTextContent();
                String coef = element.getElementsByTagName("coef").item(0).getTextContent();
                String comment = element.getElementsByTagName("comment").item(0).getTextContent();

                // Calling to the model to insert the data into database
                model.insertStudentRecord(studentId, firstName, lastName);
                model.insertCourse(courseId, courseName);
                model.insertSection(sectionId, year, term, sectionName, courseId);

                int enrollmentID = model.insertEnrollment(sectionId, studentId);
                model.insertReport(enrollmentID, note1, note2, compo, coef, comment);
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
