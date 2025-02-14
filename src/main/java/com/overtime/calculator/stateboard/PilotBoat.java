package com.overtime.calculator.stateboard;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.Objects;

@Entity
public class PilotBoat {

    @Id
    @GeneratedValue
    private long id;

    private String name;
    private ArrayList<Integer> availability;
    private ArrayList<String> boatPlace;

    // No-argument constructor (required by JPA)
    public PilotBoat() {
        this.availability = new ArrayList<>(); // Initialize the list to avoid NullPointerException
        this.boatPlace = new ArrayList<>(); // Initialize the list to avoid NullPointerException
    }

    // Parameterized constructor for convenience
    public PilotBoat(String name, ArrayList<String> availability, ArrayList<String> boatPlace) {
        this.name = name;
        this.availability = new ArrayList<>(); // Initialize the list if null
        this.boatPlace = boatPlace != null ? boatPlace : new ArrayList<>(); // Initialize the list if null
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Integer> getAvailability() {
        return availability;
    }

    public void setAvailability(ArrayList<Integer> availability) {
        this.availability = availability != null ? availability : new ArrayList<>(); // Ensure the list is never null
    }

    public ArrayList<String> getBoatPlace() {
        return boatPlace;
    }

    public void setBoatPlace(ArrayList<String> boatPlace) {
        this.boatPlace = boatPlace != null ? boatPlace : new ArrayList<>(); // Ensure the list is never null
    }

    // toString method for logging and debugging
    @Override
    public String toString() {
        return "PilotBoat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", availability=" + availability +
                ", boatPlace=" + boatPlace +
                '}';
    }

    // equals and hashCode methods for proper entity comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PilotBoat pilotBoat = (PilotBoat) o;
        return id == pilotBoat.id &&
                Objects.equals(name, pilotBoat.name) &&
                Objects.equals(availability, pilotBoat.availability) &&
                Objects.equals(boatPlace, pilotBoat.boatPlace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, availability, boatPlace);
    }
}