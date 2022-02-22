package models;
import controllers.ReportCard;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class ReportCardModel extends DbManager{
    int BINS_NUM = 8;
    public ReportCardModel(){
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
            while(rs.next()){ // Looping through response collection
                int[] bins = new int[BINS_NUM];
                for(int i = 0; i < BINS_NUM; i++){
                    bins[i] = rs.getInt(2 + i);
                }

                ReportCard reportCard = new ReportCard(
                        rs.getString(15), rs.getString(16), rs.getInt(19), rs.getInt(20),
                        rs.getString(18), rs.getString(23), bins, rs.getString(10),
                        rs.getString(11), rs.getInt(12), rs.getInt(13)
                ); // Initializing ReportCard object

                reportCards.add(reportCard); // Adding ReportCard object to the LinkedList
            }
        }
        catch (SQLException ex) { // Processing possible SQL exceptions
            System.out.println("Error while selecting report cards: " + ex.getMessage());
            ex.printStackTrace();
        }
        ReportCard[] reportCardsArr = reportCards.toArray(new ReportCard[0]);
        return reportCardsArr;
    }

    public void updateReportCard(ReportCard reportCard){
        openConn();
        Statement stmt;
        String insertSQL;
        String INSERT_STRING = "";

        try{
            stmt = conn.createStatement();
            for(int i = 1; i <= BINS_NUM; i++){
                INSERT_STRING += String.format(" BIN" + i + "=%s, ", reportCard.bins[i - 1]);
            }

            insertSQL = String.format("UPDATE REPORTS SET" + INSERT_STRING + " COEF=%s, COMMENT='%s' " +
                            "WHERE STUDENTID=%s AND SECTIONID=%s",
                    reportCard.coef, reportCard.comment, reportCard.studentID, reportCard.sectionID);
            stmt.execute(insertSQL);
            conn.commit();

        }catch (SQLException ex){
            System.out.println("Update statement has failed " + ex.getMessage());
        }

    }
}
