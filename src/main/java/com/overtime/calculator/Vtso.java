package com.overtime.calculator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 *
 */
public class Vtso
{
    private String name;
    private String letter; // operator or officer letter
    private LocalDate firstShiftDate;
    private String deskSide; // operator or officer
    private int rotationLength; // in days

    public Vtso(String name, String letter, int monthOfFirstShift, int dayOfFirstShift, String deskSide)
    {
        this.name = name;
        this.letter = letter;
        firstShiftDate = LocalDate.of(2024, monthOfFirstShift, dayOfFirstShift);
        this.deskSide = deskSide;

        if (deskSide.equals("operator")) {
            rotationLength = 50;
        } else {
            rotationLength = 30;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public LocalDate getFirstShiftDate() {
        return firstShiftDate;
    }

    public void setFirstShiftDate(LocalDate firstShiftDate) {
        this.firstShiftDate = firstShiftDate;
    }

    public String getDeskSide() {
        return deskSide;
    }

    public void setDeskSide(String deskSide) {
        this.deskSide = deskSide;
    }

    public boolean isOfficer()
    {
        return deskSide.equals("officer");
    }

    public int getRotationLength() {
        return rotationLength;
    }

    public int getRotationLengthMinusLeave() {
        return getRotationLength() == 30 ? 20 : 36;
    }

    public void setRotationLength(int rotationLength) {
        this.rotationLength = rotationLength;
    }

    /**
     *
     * @param date the date of the overtime shift
     * @return number of days into rotation. integer between 1 and 8 or high if on 14 off
     */
    public int daysIntoRotation(LocalDate date)
    {
        // working out the shift number of the person who needs cover
        long daysSinceFirstShift = ChronoUnit.DAYS.between(getFirstShiftDate(), date);
        long daysIntoRotation = daysSinceFirstShift % rotationLength;
        if (daysIntoRotation <= getRotationLengthMinusLeave()) {
            return (int) (daysIntoRotation % 8) + 1;
        } else {
            return (int) daysIntoRotation + 1;
        }
    }
}
