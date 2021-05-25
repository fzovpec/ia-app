package models;
import controllers.ReportCard;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class ContentPanelModel extends DbManager{
    public ContentPanelModel(){
        createOrConnectToDatabase("IADB");
    }

    public ReportCard[] getReportsData(){
        LinkedList <ReportCard> reportCards = new LinkedList<ReportCard>();
        Statement stmt;
        ResultSet rs = null;
        String statement = "SELECT REPORTS.*, STUDENTS.*, SECTIONS.*, COURSES.* FROM REPORTS INNER JOIN STUDENTS ON " +
                "REPORTS.STUDENTID = STUDENTS.ID INNER JOIN SECTIONS ON REPORTS.SECTIONID = SECTIONS.ID " +
                "INNER JOIN COURSES ON SECTIONS.COURSEID = COURSES.ID";

        openConn();
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(statement);
            int i = 0;

            while(rs.next()){
                ReportCard reportCard = new ReportCard();

                reportCard.bin1 = rs.getString(2);
                reportCard.bin2 = rs.getString(3);
                reportCard.bin3 = rs.getString(4);
                reportCard.bin4 = rs.getString(5);
                reportCard.bin5 = rs.getString(6);
                reportCard.bin6 = rs.getString(7);
                reportCard.bin7 = rs.getString(8);
                reportCard.bin8 = rs.getString(9);
                reportCard.coef = rs.getInt(10);
                reportCard.comment = rs.getString(11);
                reportCard.studentFirstName = rs.getString(15);
                reportCard.studentLastName = rs.getString(16);
                reportCard.sectionName = rs.getString(18);
                reportCard.year = rs.getInt(19);
                reportCard.term = rs.getInt(20);
                reportCard.courseName = rs.getString(23);

                reportCards.add(reportCard);

                i++;
            }
        }
        catch (SQLException ex){
            System.out.println("Error while selecting report cards: " + ex.getMessage());
        }

        ReportCard[] reportCardsArr = reportCards.toArray(new ReportCard[0]);
        return reportCardsArr;
    }
}
