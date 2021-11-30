package controllers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class FilteringController {
    public int [] getYears(ReportCard[] reportCards){
        HashMap<Integer,Integer> hashmapYears = new HashMap<Integer,Integer>();
        for(int i = 0; i < reportCards.length; i++){
            hashmapYears.put(reportCards[i].year, i);
        }

        Integer[] years = hashmapYears.keySet().toArray(new Integer[0]);
        int[] primitiveYears = new int[years.length];

        for(int i = 0; i < years.length; i++){
            primitiveYears[i] = years[i];
        }

        return primitiveYears;
    }

    public String [] getCourses(ReportCard[] reportCards){
        HashMap<String,Integer> hashmapYears = new HashMap<String,Integer>();

        for(int i = 0; i < reportCards.length; i++){
            hashmapYears.put(reportCards[i].courseName, i);
        }

        return hashmapYears.keySet().toArray(new String[0]);
    }

    public String [] getSections(ReportCard[] reportCards){
        HashMap<String,Integer> hashmapYears = new HashMap<String,Integer>();

        for(int i = 0; i < reportCards.length; i++){
            hashmapYears.put(reportCards[i].sectionName, i);
        }

        return hashmapYears.keySet().toArray(new String[0]);
    }

    public int [] getTerms(ReportCard[] reportCards){
        HashMap<Integer,Integer> hashmapTerms = new HashMap<Integer,Integer>();
        for(int i = 0; i < reportCards.length; i++){
            hashmapTerms.put(reportCards[i].term, i);
        }

        Integer[] years = hashmapTerms.keySet().toArray(new Integer[0]);
        int[] primitiveTerms = new int[years.length];

        for(int i = 0; i < years.length; i++){
            primitiveTerms[i] = years[i];
        }

        return primitiveTerms;
    }

    public ReportCard[] filterReportCards(String year, String term, String courseName, String sectionName, ReportCard[] reportCards){
        if(year.equals("All") && term.equals("All") && courseName.equals("All") && sectionName.equals("All")){
            return reportCards;
        }
        List<ReportCard> filteredReportCards = new LinkedList<>();
        for(int i = 0; i < reportCards.length; i++) {
            boolean reportCardFollowsFilteringConditions = true;

            if (!year.equals("All") && reportCards[i].year != Integer.parseInt(year)) {
                reportCardFollowsFilteringConditions = false;
            }
            if (!term.equals("All") && reportCards[i].term != Integer.parseInt(term)) {
                reportCardFollowsFilteringConditions = false;
            }
            if (!courseName.equals("All") && !reportCards[i].courseName.equals(courseName)) {
                reportCardFollowsFilteringConditions = false;
            }
            if (!sectionName.equals("All") && !reportCards[i].sectionName.equals(sectionName)) {
                reportCardFollowsFilteringConditions = false;
            }

            if (reportCardFollowsFilteringConditions) {
                filteredReportCards.add(reportCards[i]);
            }
        }

        return filteredReportCards.toArray(new ReportCard[0]);
    }
}
