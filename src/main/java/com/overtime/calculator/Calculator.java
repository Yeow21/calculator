package com.overtime.calculator;

import java.util.*;
import java.time.LocalDate;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private int shiftNumber;

    public Calculator() {
    }



    public Calculator(LocalDate date, String deskSide, String letter)
    {
        this.deskSide = deskSide;
        this.letter = letter;
        this.date = date;
    }


    /**
     * The method which needs to be run to return the OvertimeShift object which will be represented on the web page
     * @return
     */
    public OvertimeShift getOvertimeShift()
    {
        // creates the list of officers and operators
        populateOfficers();

        // get officer object which is used to work out which shiftNumber is needed
        Vtso officerToBeCovered = getOfficerToBeCovered();

        // set the shift number to use to calculate cover list
        this.shiftNumber = getShiftNumberFromOfficer(officerToBeCovered);

        // Return the OvertimeShift object after running it through the calculator method
        return calculateOvertimeList(officers);
    }

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
    public Vtso getOfficerToBeCovered()
    {
        for (Vtso officer : officers) {
            if (deskSide.equals(officer.getDeskSide()) && letter.equals(officer.getLetter())) {
                return officer;
            }
        }
        return null;
    }

    /**
     * calculate the number of the shift of the person being covered, 1-4
     * @param officer
     * @return
     */
    private int getShiftNumberFromOfficer(Vtso officer) {
        int shiftNumber = 0;

        // working out the shift number of the person who needs cover
        long daysSinceFirstShift = ChronoUnit.DAYS.between(officer.getFirstShiftDate(), this.getDate());
        System.out.println("daysSinceFirstShift: " + daysSinceFirstShift);
        long daysIntoRotation = daysSinceFirstShift % officer.getRotationLength();
        System.out.println("daysIntoRotation: " + daysIntoRotation);
        if (daysIntoRotation <= officer.getRotationLengthMinusLeave()) {
            shiftNumber = (int) (daysIntoRotation % 8) + 1;
            System.out.println("shiftNumber = " + shiftNumber);
        }

        return shiftNumber;
    }


    public OvertimeShift calculateOvertimeList(ArrayList<Vtso> officers) //
    {
        HashMap<String, ArrayList<Vtso>> coverList = new HashMap<>();
        coverList.put("officer", new ArrayList<Vtso>());
        coverList.put("operator", new ArrayList<Vtso>());

        // Filter officers
        List<Vtso> dutyOfficers = officers
                .stream()
                .filter(v -> v.getDeskSide().equals("officer"))
                .toList();

        // Filter operators
        List<Vtso> marineOperators = officers
                .stream()
                .filter(v -> v.getDeskSide().equals("operator"))
                .toList();






        int otherShiftNumber = shiftNumber == 1 || shiftNumber == 3 ? shiftNumber + 1 : shiftNumber - 1;

        // DEBUG~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        System.out.println("shiftNumber = " + shiftNumber + " && otherShiftNumber = " + otherShiftNumber);



        int officerShiftNumber = deskSide.equals("officer") ? shiftNumber : otherShiftNumber;

        // DEBUG~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        System.out.println("Officer shift number = " + officerShiftNumber);


        int operatorShiftNumber = deskSide.equals("operator") ? shiftNumber : otherShiftNumber;

        // DEBUG~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        System.out.println("Operator shift number = " + operatorShiftNumber);




        // calculate officers for cover
        switch (officerShiftNumber) {
            case 1:

                // get first person for cover
                for (Vtso officer : dutyOfficers) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation == 7) {
                        coverList.get("officer").add(officer);
                    }
                }

                // get 2nd person for cover (on 10 off)
                for (Vtso officer : dutyOfficers) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation >= 26) {
                        officer.timeOff();
                        coverList.get("officer").add(officer);
                    }
                }

                // get 3rd person for cover (on 10 off)
                for (Vtso officer : dutyOfficers) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation >= 9 && daysIntoRotation < 26) {
                        officer.timeOff();
                        coverList.get("officer").add(officer);
                    }
                }
                break;

            case 2:

                for (Vtso officer : dutyOfficers) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation == 30) {
                        officer.timeOff();
                        coverList.get("officer").add(officer);
                    }
                }

                // get 2nd person for cover
                for (Vtso officer : dutyOfficers) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    System.out.println(daysIntoRotation);
                    if (daysIntoRotation == 8) {
                        coverList.get("officer").add(officer);

                    }
                }

                // get first person for cover
                for (Vtso officer : dutyOfficers) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation == 6) {
                        coverList.get("officer").add(officer);
                    }
                }

                // get 2nd person for cover (on 10 off)
                for (Vtso officer : dutyOfficers) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation >= 26 && daysIntoRotation <= 29) {
                        officer.timeOff();
                        coverList.get("officer").add(officer);
                    }
                }

                // get 3rd person for cover (on 10 off)
                for (Vtso officer : dutyOfficers) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation >= 9 && daysIntoRotation < 26) {
                        officer.timeOff();
                        coverList.get("officer").add(officer);
                    }
                }
                break;

            case 3:

                for (Vtso officer : dutyOfficers) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation == 21) {
                        officer.timeOff();
                        coverList.get("officer").add(officer);
                    }
                }

                for (Vtso officer : dutyOfficers) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation == 5) {
                        coverList.get("officer").add(officer);
                    }
                }

                for (Vtso officer : dutyOfficers) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation == 7) {
                        coverList.get("officer").add(officer);
                    }
                }

                for (Vtso officer : dutyOfficers) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation >= 21 && daysIntoRotation <=25) {
                        officer.timeOff();
                        coverList.get("officer").add(officer);
                    }
                }

                for (Vtso officer : dutyOfficers) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation > 25) {
                        officer.timeOff();
                        coverList.get("officer").add(officer);
                    }
                }
                break;

            case 4:
                for (Vtso officer : dutyOfficers) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation == 6) {
                        coverList.get("officer").add(officer);
                    }
                }

                for (Vtso officer : dutyOfficers) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation > 25 && daysIntoRotation < 30) {
                        officer.timeOff();
                        coverList.get("officer").add(officer);
                    }
                }

                for (Vtso officer : dutyOfficers) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation >= 21 && daysIntoRotation <=25) {
                        officer.timeOff();
                        coverList.get("officer").add(officer);
                    }
                }


                break;
            default:
                System.out.println("No successful case!");
                break;
        }



        // calculate operators for cover
        switch (operatorShiftNumber) {
            case 1:
                for (Vtso officer : marineOperators) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation == 7) {
                        coverList.get("operator").add(officer);
                    }
                }

                // change to commit
                for (Vtso officer : marineOperators) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation > 46) {
                        officer.timeOff();
                        coverList.get("operator").add(officer);
                    }
                }

                for (Vtso officer : marineOperators) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation >= 38 && daysIntoRotation <= 46) {
                        officer.timeOff();
                        coverList.get("operator").add(officer);
                    }
                }
                break;
            case 2:
                for (Vtso officer : marineOperators) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation == 6) {
                        coverList.get("operator").add(officer);
                    }
                }

                for (Vtso officer : marineOperators) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation == 8) {
                        coverList.get("operator").add(officer);
                    }
                }

                for (Vtso officer : marineOperators) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation > 46) {
                        officer.timeOff();
                        coverList.get("operator").add(officer);
                    }
                }

                for (Vtso officer : marineOperators) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation >= 38 && daysIntoRotation <= 46) {
                        officer.timeOff();
                        coverList.get("operator").add(officer);
                    }
                }
                break;

            case 3:
                for (Vtso officer : marineOperators) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation == 7) {
                        coverList.get("operator").add(officer);
                    }
                }

                for (Vtso officer : marineOperators) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation == 5) {
                        coverList.get("operator").add(officer);
                    }
                }

                for (Vtso officer : marineOperators) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation > 46 && daysIntoRotation < 50) {
                        officer.timeOff();
                        coverList.get("operator").add(officer);
                    }
                }

                for (Vtso officer : marineOperators) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation >= 37 && daysIntoRotation <= 46) {
                        officer.timeOff();
                        coverList.get("operator").add(officer);
                    }
                }

                break;

            case 4:
                for (Vtso officer : marineOperators) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation == 6) {
                        coverList.get("operator").add(officer);
                    }
                }

                for (Vtso officer : marineOperators) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation > 46 && daysIntoRotation < 50) {
                        officer.timeOff();
                        coverList.get("operator").add(officer);
                    }
                }

                for (Vtso officer : marineOperators) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation >= 37 && daysIntoRotation <= 46) {
                        officer.timeOff();
                        coverList.get("operator").add(officer);
                    }
                }
                break;
        }

        // populate auxillary informated
        String dayOrNight = shiftNumber == 1 || shiftNumber == 2 ? "day" : "night";
        ArrayList<String> coverData = new ArrayList<>();
        coverData.add(date.toString());
        coverData.add(deskSide);
        coverData.add(letter);
        coverData.add(dayOrNight);

        ArrayList<String>coverMetaData = coverData;
        ArrayList<Vtso>officerList = coverList.get("officer");
        ArrayList<Vtso>operatorList = coverList.get("operator");


        OvertimeShift newShift = new OvertimeShift(date, deskSide, letter, officerList, operatorList);
        System.out.printf(
                "\n DEBUG: Date: %s, Desk Side: %s, Letter: %s, Officer List: %s, Operator List: %s\n",
                date.toString(), deskSide, letter, officerList, operatorList
        );
        return newShift;
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


    public void setShiftNumber(int shiftNumber) {
        this.shiftNumber = shiftNumber;
    }

    public int getShiftNumber() {
        return shiftNumber;
    }
}
