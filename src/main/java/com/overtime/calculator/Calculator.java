package com.overtime.calculator;

import java.util.*;
import java.time.LocalDate;

public class Calculator
{
    private String name;
    private int year;
    private int month;
    private int day;
    private String deskSide;
    private String letter;
    private LocalDate date;
    private HashMap<String, ArrayList<String>> coverMap;


    private ArrayList<Vtso> officers;

    public Calculator()
    {

    }
    /**
     *
     * @param year
     * @param month
     * @param day
     * @param deskSide
     * @param letter
     */
    public Calculator(int year, int month, int day, String deskSide, String letter)
    {
        this.year = year;
        this.month = month;
        this.day = day;
        this.deskSide = deskSide;
        this.letter = letter;
        date = LocalDate.of(year, month, day);
        populateOfficers();
        OvertimeShift shift = getShift();
        System.out.println(shift);
        System.out.println(populateCoverMap(shift));
        System.out.println("TEST COMPLETED!!!!!!!!!!!!!!!!!!");
    }



    /**
     * 1 -
     * populate the officers arrayList
     * @return
     */
    public void populateOfficers()
    {
        officers = new ArrayList<>();
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
    }


    /**
     * Calculate officer etc that needs cover and return the shift object
     * @return OvertimeShift object if successful, otherwise return null
     */
    public OvertimeShift getShift()
    {
        populateOfficers();
        for (Vtso officer : officers) {
            if (deskSide.equals(officer.getDeskSide()) && letter.equals(officer.getLetter())) {
                return new OvertimeShift(date, deskSide, officer);
            }
        }
        return null;
    }

    /**
     * Once shift object initialised, call this to return the cover map
     * @param shift the shift object which has been initialised with the details of the shift to be covered.
     * @return the completed map with order of cover
     */
    public HashMap<String, ArrayList<String>> populateCoverMap(OvertimeShift shift)
    {
        HashMap<String, ArrayList<String>> coverMap = shift.calculateOvertimeList(officers);

        for (String string : coverMap.keySet()) {
            System.out.print(string);
            System.out.println(coverMap.get(string));
        }

        this.coverMap = coverMap;

        return coverMap;
    }

























    @Override
    public String toString() {
        return "CalculatorHandler{" +
                "name='" + name + '\'' +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", deskSide='" + deskSide + '\'' +
                ", letter='" + letter + '\'' +
                '}';
    }
    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getDeskSide() {
        return deskSide;
    }

    public void setDeskSide(String deskSide) {
        this.deskSide = deskSide;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public HashMap<String, ArrayList<String>> getCoverMap() {
        return coverMap;
    }

    public void setCoverMap(HashMap<String, ArrayList<String>> coverMap) {
        this.coverMap = coverMap;
    }
}
