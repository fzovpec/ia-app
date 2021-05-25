package models;

import java.sql.SQLException;
import java.sql.Statement;

public class XmlReaderModel extends DbManager{
    public XmlReaderModel(){
        createOrConnectToDatabase("IADB");
    }
    public void insertStudentRecord(int studentID, String studentLastName, String studentFirstName){
        System.out.println(curDB);
        Statement stmt;
        String insertSQL = "";

        openConn();
        try{
            // Students table
            stmt = conn.createStatement();
            insertSQL = String.format("INSERT INTO students (id, studentFirstName, studentLastName) VALUES (%s, '%s'," +
                    " '%s')", studentID, studentFirstName, studentLastName);

            stmt.execute(insertSQL);
            conn.commit();

        } catch (SQLException ex){
            System.out.println("Students insert: " + ex.getMessage());
        }

        closeConn();
    }

    public void insertCourse(int courseID, String courseName){
        Statement stmt;
        String insertSQL = "";

        openConn();
        try{
            stmt = conn.createStatement();
            insertSQL = String.format("INSERT INTO courses (id, courseName) VALUES (%s, '%s')", courseID, courseName);

            stmt.execute(insertSQL);
            conn.commit();
        } catch (SQLException ex){
            System.out.println("Courses insert: " + ex.getMessage());
        }

        closeConn();
    }

    public void insertSection(int sectionID, int year, int term, String sectionName, int courseID){
        Statement stmt;
        String insertSQL = "";

        openConn();
        try{
            stmt = conn.createStatement();
            insertSQL = String.format("INSERT INTO sections (id, sectionName, sectionYear, term, courseID) VALUES (%s, '%s', %s," +
                    " %s, %s)", sectionID, sectionName, year, term, courseID);

            stmt.execute(insertSQL);
            conn.commit();
        } catch (SQLException ex){
            System.out.println("Sections insert: " + ex.getMessage());
        }

        closeConn();
    }

    public void insertReport(int studentID, int sectionID, String bin1, String bin2, String bin3, String bin4, String bin5,
                             String bin6, String bin7, String bin8, String coef, String comment){
        Statement stmt;
        String insertSQL = "";

        openConn();
        try{
            stmt = conn.createStatement();
            insertSQL = String.format("INSERT INTO reports (bin1, bin2, bin3, bin4, bin5, bin6, bin7, bin8, coef, comment," +
                    "studentID, sectionID) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', %s, %s)",
                    bin1, bin2, bin3, bin4, bin5, bin6, bin7, bin8, coef, comment, studentID, sectionID);

            stmt.execute(insertSQL);
            conn.commit();
        } catch (SQLException ex){
            System.out.println("Courses insert: " + ex.getMessage());
        }

        closeConn();
    }
}
