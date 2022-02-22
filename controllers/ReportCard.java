package controllers;

public class ReportCard {
    public String studentFirstName;
    public String studentLastName;
    public int year;
    public int term;
    public String sectionName;
    public String courseName;

    public int[] bins;
    public float[] average;
    public String coef;
    public String comment;

    public int studentID;
    public int sectionID;

    public ReportCard(String studentFirstName, String studentLastName, int year, int term, String sectionName,
                      String courseName, int[] bins, String coef, String comment, int studentID, int sectionID){
        this.studentFirstName = studentFirstName;
        this.studentLastName = studentLastName;
        this.year = year;
        this.term = term;
        this.sectionName = sectionName;
        this.courseName = courseName;
        this.bins = bins;
        this.average = computeAverages(bins);

        this.coef = coef;
        this.comment = comment;

        this.studentID = studentID;
        this.sectionID = sectionID;
    }

    public ReportCard(){}

    private float[] computeAverages(int[] bins){
        float[] averages = new float[bins.length - 1]; // Initializing array of averages
        for(int i = 0; i < bins.length - 1; i+=1){
            averages[i] = (float) ((bins[i] + bins[i + 1]) / 2.0); // Computing an average
        }
        return averages;
    }
}
