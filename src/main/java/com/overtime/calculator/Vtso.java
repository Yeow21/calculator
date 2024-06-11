package com.overtime.calculator;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 *
 */
@Entity
public class Vtso implements Serializable
{
    private static final long serialVersionUID = 1L; // Added serialVersionUID
    @Id
    @GeneratedValue long id;
    private String name;
    private String letter; // operator or officer letter
    private LocalDate firstShiftDate;
    private String deskSide; // operator or officer
    private int rotationLength;
    private boolean isOnTimeOff;
    private int timeOffLength;
    private boolean confirmedCover;
    private boolean rejectedCover;
    private boolean coverConfirmed;
    private boolean coverRejected;

    public Vtso() {
    }
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
        //test 
        // working out the shift number of the person who needs cover
        long daysSinceFirstShift = ChronoUnit.DAYS.between(getFirstShiftDate(), date);
        long daysIntoRotation = daysSinceFirstShift % rotationLength;
        if (daysIntoRotation <= getRotationLengthMinusLeave()) {
            return (int) (daysIntoRotation % 8) + 1;
        } else {
            return (int) daysIntoRotation + 1;
        }
    }

    public boolean isOnTimeOff() {
        return isOnTimeOff;
    }

    public void setOnTimeOff(boolean onTimeOff) {
        isOnTimeOff = onTimeOff;
        if (deskSide.equals("operator")) {
            setLetter(getLetter() + "(14)");
        } else {
            setLetter(getLetter() + ("(10)"));
        }
    }

    public int getTimeOffLength() {
        return timeOffLength;
    }

    public void setTimeOffLength(int timeOffLength) {
        this.timeOffLength = timeOffLength;
    }

    public void timeOff()
    {
        setOnTimeOff(true);
        setTimeOffLength(deskSide.equals("officer") ? 10 : 14);
    }

    public boolean getConfirmedCover() {
        return confirmedCover;
    }

    public void setConfirmedCover(boolean confirmedCover) {
        this.confirmedCover = confirmedCover;
    }

    public boolean getRejectedCover() {
        return rejectedCover;
    }

    public void setRejectedCover(boolean rejectedCover) {
        this.rejectedCover = rejectedCover;
    }

    public boolean isCoverConfirmed() {
        return coverConfirmed;
    }

    public void setCoverConfirmed() {
        this.coverConfirmed = !this.coverConfirmed;
    }

    public boolean isCoverRejected() {
        return coverRejected;
    }

    public void setCoverRejected() {

        this.coverRejected = !this.coverRejected;
    }
}
