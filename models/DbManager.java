package models;
import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbManager {
    /*
    Class responsible for CRUD operations with database
    All other models are are inherited from this class
    * */

    Connection conn;
    String curDB;

    public void createTables(){
        Statement stmt;
        String createSQL = "";

        openConn();
        try{
            // Students table
            stmt = conn.createStatement();
            createSQL = "create table students ("
                    + " id integer not null,"
                    + " studentFirstName varchar(50), studentLastName varchar(50),"
                    + " constraint student_primary_key primary key (id))";
            stmt.execute(createSQL);
            System.out.println("Table students created.");
            // Courses table
            createSQL = "create table courses ("
                    + " id integer not null,"
                    + " courseName varchar(50),"
                    + " constraint course_primary_key primary key (id))";
            stmt.execute(createSQL);
            System.out.println("Table courses created.");
            // Sections table
            createSQL = "create table sections ("
                    + " id integer not null,"
                    + " sectionName varchar(50), sectionYear integer not null, term integer not null, courseID integer not null,"
                    + " constraint section_primary_key primary key (id),"
                    + " constraint classID_foreign_key foreign key (courseID) references courses(id))";
            stmt.execute(createSQL);
            System.out.println("Table sections created.");
            // Reports table
            createSQL = "create table reports ("
                    + " id integer not null generated always as"
                    + " identity (start with 1, increment by 1),"
                    + " bin1 integer, bin2 integer, bin3 integer, bin4 integer, bin5 integer,"
                    + " bin6 integer, bin7 integer, bin8 integer, coef integer, comment varchar(3000),"
                    + " studentID integer not null, sectionID integer not null,"
                    + " constraint courses_primary_key primary key (id),"
                    + " constraint studentID_foreign_key foreign key (studentID) references students(id),"
                    + " constraint coursesID_foreign_key foreign key (sectionID) references sections(id))";
            stmt.execute(createSQL);
            System.out.println("Table reports created.");

            conn.commit();

        } catch (SQLException EX) {
            System.out.println(EX.getMessage());

        }

        closeConn();
    }

    public void createOrConnectToDatabase(String DBName){
        curDB = DBName;
        try {
            // Try to connect to the database
            DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
            conn = DriverManager.getConnection("jdbc:derby:" + DBName +";");
            conn.setAutoCommit(false);
            createTables();

        } catch (SQLException e) {
            do {
                System.out.println("\n----- SQLException -----");
                System.out.println("  SQLState:   " + e.getSQLState());
                System.out.println("  Error Code: " + e.getErrorCode());
                System.out.println("  Message:    " + e.getMessage());
                e = e.getNextException();
            } while (e != null);
            createDatabase(DBName);
        }
    }

    private void createDatabase(String DBName){
        try {
            // Create the DB if it doesn't exist yet
            DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
            conn = DriverManager.getConnection("jdbc:derby:" + DBName +";create=true");
            conn.setAutoCommit(false);

        } catch (SQLException EX2) {
            System.out.println("The exception occurred " + EX2);

        }
    }

    public Connection openConn() {
        // Try to connect to the database
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
            conn = DriverManager.getConnection("jdbc:derby:" + curDB + ";");
            conn.setAutoCommit(false);
        } catch (SQLException ex){
            createDatabase(curDB);
        }

        return conn;
    }

    public void closeConn(){
        try {
            if (conn != null){
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
            }

        } catch (SQLException EX) {
            if ((EX.getErrorCode() == 50000) && ("XJ015".equals(EX.getSQLState()))) {
            } else {
                System.err.println("Derby did not shut down normally");
                System.err.println(EX.getMessage());
            }
        }
    }
}
