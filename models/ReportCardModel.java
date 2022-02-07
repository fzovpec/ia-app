package models;
import controllers.ReportCard;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class ReportCardModel extends DbManager{
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

            while(rs.next()){
                int bin1 = rs.getInt(2);
                int bin2 = rs.getInt(3);
                int bin3 = rs.getInt(4);
                int bin4 = rs.getInt(5);
                int bin5 = rs.getInt(6);
                int bin6 = rs.getInt(7);
                int bin7 = rs.getInt(8);
                int bin8 = rs.getInt(9);
                int[] bins = {bin1, bin2, bin3, bin4, bin5, bin6, bin7, bin8};

                ReportCard reportCard = new ReportCard(
                        rs.getString(15), rs.getString(16), rs.getInt(19), rs.getInt(20),
                        rs.getString(18), rs.getString(23), bins, rs.getString(10),
                        rs.getString(11), rs.getInt(12), rs.getInt(13)
                );

                reportCards.add(reportCard);
            }
        }
        catch (SQLException ex) {
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

        try{
            stmt = conn.createStatement();
            insertSQL = String.format("UPDATE REPORTS SET BIN1=%s, BIN2=%s, BIN3=%s, BIN4=%s, " +
                            "BIN5=%s, BIN6=%s, BIN7=%s, BIN8=%s, COEF=%s, COMMENT='%s' " +
                            "WHERE STUDENTID=%s AND SECTIONID=%s",
                    reportCard.bins[0], reportCard.bins[1], reportCard.bins[2], reportCard.bins[3],
                    reportCard.bins[4], reportCard.bins[5], reportCard.bins[6], reportCard.bins[7],
                    reportCard.coef, reportCard.comment, reportCard.studentID, reportCard.sectionID);
            stmt.execute(insertSQL);
            // Need to look at the other files, query submission is missing here
            conn.commit();

        }catch (SQLException ex){
            System.out.println("Update statement has failed " + ex.getMessage());
        }

    }
}
