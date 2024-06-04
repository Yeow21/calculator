package com.overtime.calculator;


import java.util.*;

public class CalculatorHandler
{
    private final ArrayList<Vtso> officers;
    private OvertimeShift shift;

    public CalculatorHandler(int year, int month, int day, String deskSide, String letter)
    {

        System.out.println("Start of CalculatorHandler Constructor...");
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
        System.out.println("ArrayList created: " + officers);


        for (Vtso officer : officers) {
            if (deskSide.equals(officer.getDeskSide()) && letter.equals(officer.getLetter())) {
                // shift = new OvertimeShift(year, month, day, deskSide, officer);
            }
        }

        HashMap<String, ArrayList<String>> coverMap = new HashMap<>();
        coverMap = shift.calculateOvertimeList(officers);

        for (String string : coverMap.keySet()) {
            System.out.print(string);
            System.out.println(coverMap.get(string));
        }

    }
}
