package com.overtime.calculator;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;

@Entity
public class OvertimeShift
{
    private @Id
    @GeneratedValue long id;
    private LocalDate date;
    private String deskSide;
    private String letter;
    private ArrayList<String> coverMetaData;
    private ArrayList<String> officersList = new ArrayList<>();
    private ArrayList<String> operatorsList = new ArrayList<>();
    private ArrayList<String> removedOperators = new ArrayList<>();
    private ArrayList<String> removedOfficers = new ArrayList<>();


    public OvertimeShift(){}

    public OvertimeShift(LocalDate date, String deskSide, String letter, ArrayList<String>officerList, ArrayList<String>operatorList){
        this.date = date;
        this.deskSide = deskSide;
        this.letter = letter;
        this.officersList = officerList;
        this.operatorsList = operatorList;
    }




    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDeskSide() {
        return deskSide;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public ArrayList<Vtso> populateOfficers()
    {
        ArrayList<Vtso> officers = new ArrayList<>();
        officers.add(new Vtso("Joe", "A", 1, 13, "officer"));
        officers.add(new Vtso("Jonathan", "B", 1, 19, "officer"));
        officers.add(new Vtso("Luke", "C", 1, 25, "officer"));
        officers.add(new Vtso("Fraser", "D", 1, 31, "officer"));
        officers.add(new Vtso("George", "E", 2, 6, "officer"));

        officers.add(new Vtso("Ben", "A", 2, 19, "operator"));
        officers.add(new Vtso("Kevin", "B", 1, 20, "operator"));
        officers.add(new Vtso("Sam", "C", 1, 10, "operator"));
        officers.add(new Vtso("Iguana", "D", 1, 30, "operator"));
        officers.add(new Vtso("Chris", "E", 2, 9, "operator"));
        return officers;
    }


    public ArrayList<String> getOfficersList() {
        return officersList;
    }

    public void setOfficersList(ArrayList<String> officersList) {
        this.officersList = officersList;
    }

    public ArrayList<String> getOperatorsList() {
        return operatorsList;
    }

    public void setOperatorsList(ArrayList<String> operatorsList) {
        this.operatorsList = operatorsList;
    }

    public ArrayList<String> getCoverMetaData() {
        return coverMetaData;
    }

    public void setCoverMetaData(ArrayList<String> coverMetaData) {
        this.coverMetaData = coverMetaData;
    }

    public void setDeskSide(String deskSide) {
        this.deskSide = deskSide;
    }

    public void removeLetterFromCoverList(String letter, String deskSide)
    {
        if (deskSide.equals("officer")) {
            System.out.println(officersList.remove(letter));
            System.out.println(removedOfficers.add(letter));
        } else if (deskSide.equals("operator")) {
            System.out.println(operatorsList.remove(letter));
            System.out.println(removedOperators.add(letter));
        }
    }

    public ArrayList<String> getRemovedOperators() {
        return removedOperators;
    }

    public void setRemovedOperators(ArrayList<String> removedOperators) {
        this.removedOperators = removedOperators;
    }

    public ArrayList<String> getRemovedOfficers() {
        return removedOfficers;
    }

    public void setRemovedOfficers(ArrayList<String> removedOfficers) {
        this.removedOfficers = removedOfficers;
    }
}
