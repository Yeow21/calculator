package com.overtime.calculator.stateboard;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.Objects;

@Entity
public class Pilot {

    @Id
    @GeneratedValue
    private long id;

    private int pilotNumber; // pilot number for the sake of clarity
    private ArrayList<Integer> pilotClass;
    private ArrayList<Integer> availability;

    // No-argument constructor (required by JPA)
    public Pilot() {
        this.pilotClass = new ArrayList<>();
        this.availability = new ArrayList<>();
    }

    // Getters and Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPilotNumber() {
        return pilotNumber;
    }

    public void setPilotNumber(int pilotNumber) {
        this.pilotNumber = pilotNumber;
    }

    public ArrayList<Integer> getPilotClass() {
        return pilotClass;
    }

    public void setPilotClass(ArrayList<Integer> pilotClass) {
        this.pilotClass = pilotClass != null ? pilotClass : new ArrayList<>();
    }

    public ArrayList<Integer> getAvailability() {
        return availability;
    }

    public void setAvailability(ArrayList<Integer> availability) {
        this.availability = availability != null ? availability : new ArrayList<>();
    }

    // toString method for logging and debugging
    @Override
    public String toString() {
        return "Pilot{" +
                "id=" + id +
                ", pilotNumber=" + pilotNumber +
                ", pilotClass=" + pilotClass +
                ", availability=" + availability +
                '}';
    }

    // equals and hashCode methods for proper entity comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pilot pilot = (Pilot) o;
        return id == pilot.id &&
                pilotNumber == pilot.pilotNumber &&
                Objects.equals(pilotClass, pilot.pilotClass) &&
                Objects.equals(availability, pilot.availability);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pilotNumber, pilotClass, availability);
    }
}