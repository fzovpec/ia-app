package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public int insertEnrollment(int sectionID, int studentID){
        int id = 0;
        PreparedStatement pstmt;
        String insertSQL;

        openConn();
        try{
            insertSQL = String.format("INSERT INTO enrollments (studentID, sectionID) VALUES (%s, %s)", studentID, sectionID);
            pstmt = conn.prepareStatement(insertSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            id = rs.getInt(1);
            conn.commit();
        } catch (SQLException ex){
            System.out.println("Enrollement insert: " + ex.getMessage());
        }

        closeConn();

        return id;
    }

    public void insertReport(int enrollmentID, String note1, String note2, String compo, String coef, String comment){
        Statement stmt;
        String insertSQL = "";

        openConn();
        try{
            stmt = conn.createStatement();
            insertSQL = String.format("INSERT INTO reports (note1, note2, compo, coef, comment, enrollmentID) VALUES " +
                    "('%s', '%s', '%s', '%s', '%s', %s)", note1, note2, compo, coef, comment, enrollmentID);

            stmt.execute(insertSQL);
            conn.commit();
        } catch (SQLException ex){
            System.out.println("Courses insert: " + ex.getMessage());
        }

        closeConn();
    }
}
