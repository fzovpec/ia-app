package controllers;

public class ReportCard {
    public String studentFirstName;
    public String studentLastName;
    public int year;
    public int term;
    public String sectionName;
    public String courseName;
    public String bin1;
    public String bin2;
    public String bin3;
    public String bin4;
    public String bin5;
    public String bin6;
    public String bin7;
    public String bin8;
    public int coef;
    public String comment;

    public ReportCard(String studentFirstName, String studentLastName, int year, int term, String sectionName,
                      String courseName, String bin1, String bin2, String bin3, String bin4, String bin5, String bin6,
                      String bin7, String bin8, int coef, String comment){
        this.studentFirstName = studentFirstName;
        this.studentLastName = studentLastName;
        this.year = year;
        this.term = term;
        this.sectionName = sectionName;
        this.courseName = courseName;
        this.bin1 = bin1;
        this.bin2 = bin2;
        this.bin3 = bin3;
        this.bin4 = bin4;
        this.bin5 = bin5;
        this.bin6 = bin6;
        this.bin7 = bin7;
        this.bin8 = bin8;
        this.coef = coef;
        this.comment = comment;
    }

    public ReportCard(){}
}
