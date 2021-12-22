package controllers;

import models.ReportCardModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;

public class XmlExporter {
    ReportCardModel model = new ReportCardModel();

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

    public void exportDataToXml(ReportCard[] reportCards, String filePath){
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element root = document.createElement("root");
            document.appendChild(root);

            for(ReportCard reportCard: reportCards){
                Element report = document.createElement("report");
                Element firstName = document.createElement("studFN");
                Element lastName = document.createElement("studLN");
                Element year = document.createElement("year");
                Element term = document.createElement("term");
                Element sectName = document.createElement("sectName");
                Element courseName = document.createElement("courseName");
                Element bin1 = document.createElement("bin1");
                Element bin2 = document.createElement("bin2");
                Element bin3 = document.createElement("bin3");
                Element bin4 = document.createElement("bin4");
                Element bin5 = document.createElement("bin5");
                Element bin6 = document.createElement("bin6");
                Element bin7 = document.createElement("bin7");
                Element bin8 = document.createElement("bin8");
                Element coef = document.createElement("coef");
                Element comment = document.createElement("comment");

                firstName.setTextContent(reportCard.studentFirstName);
                lastName.setTextContent(reportCard.studentLastName);
                year.setTextContent(String.valueOf(reportCard.year));
                term.setTextContent(String.valueOf(reportCard.term));
                sectName.setTextContent(reportCard.sectionName);
                courseName.setTextContent(reportCard.courseName);
                bin1.setTextContent(String.valueOf(reportCard.bins[0]));
                bin2.setTextContent(String.valueOf(reportCard.bins[1]));
                bin3.setTextContent(String.valueOf(reportCard.bins[2]));
                bin4.setTextContent(String.valueOf(reportCard.bins[3]));
                bin5.setTextContent(String.valueOf(reportCard.bins[4]));
                bin6.setTextContent(String.valueOf(reportCard.bins[5]));
                bin7.setTextContent(String.valueOf(reportCard.bins[6]));
                bin8.setTextContent(String.valueOf(reportCard.bins[7]));
                coef.setTextContent(String.valueOf(reportCard.coef));
                comment.setTextContent(String.valueOf(reportCard.comment));

                report.appendChild(firstName);
                report.appendChild(lastName);
                report.appendChild(year);
                report.appendChild(term);
                report.appendChild(sectName);
                report.appendChild(courseName);
                report.appendChild(bin1);
                report.appendChild(bin2);
                report.appendChild(bin3);
                report.appendChild(bin4);
                report.appendChild(bin5);
                report.appendChild(bin6);
                report.appendChild(bin7);
                report.appendChild(bin8);
                report.appendChild(coef);
                report.appendChild(comment);

                root.appendChild(report);
            }

            Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            tr.setOutputProperty(OutputKeys.METHOD, "xml");
            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "roles.dtd");
            tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            // send DOM to file
            tr.transform(new DOMSource(document), new StreamResult(new FileOutputStream(filePath)));
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
