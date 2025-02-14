package com.overtime.calculator.stateboard;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.Objects;

@Entity
public class Tug {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private ArrayList<Integer> availability;
    private ArrayList<Boolean> escort;
    private ArrayList<Boolean> bowToBow;
    private ArrayList<String> comment;
    private String fleet;

    // No-argument constructor (required by JPA)
    public Tug() {
        this.availability = new ArrayList<>();
        this.escort = new ArrayList<>();
        this.bowToBow = new ArrayList<>();
        this.comment = new ArrayList<>();
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
        this.availability = availability != null ? availability : new ArrayList<>();
    }

    public ArrayList<Boolean> getEscort() {
        return escort;
    }

    public void setEscort(ArrayList<Boolean> escort) {
        this.escort = escort != null ? escort : new ArrayList<>();
    }

    public ArrayList<Boolean> getBowToBow() {
        return bowToBow;
    }

    public void setBowToBow(ArrayList<Boolean> bowToBow) {
        this.bowToBow = bowToBow != null ? bowToBow : new ArrayList<>();
    }

    public ArrayList<String> getComment() {
        return comment;
    }

    public void setComment(ArrayList<String> comment) {
        this.comment = comment != null ? comment : new ArrayList<>();
    }

    public String getFleet() {
        return fleet;
    }

    public void setFleet(String fleet) {
        if (fleet.equals("oil") || fleet.equals("gas")) {
            this.fleet = fleet;
        } else {
            System.out.printf("ERROR: setFleet() not correct oil/gas fleet value");
        }
    }

    // toString method for logging and debugging
    @Override
    public String toString() {
        return "Tug{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", availability=" + availability +
                ", escort=" + escort +
                ", bowToBow=" + bowToBow +
                ", comment=" + comment +
                ", fleet='" + fleet + '\'' +
                '}';
    }

    // equals and hashCode methods for proper entity comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tug tug = (Tug) o;
        return id == tug.id &&
                Objects.equals(name, tug.name) &&
                Objects.equals(availability, tug.availability) &&
                Objects.equals(escort, tug.escort) &&
                Objects.equals(bowToBow, tug.bowToBow) &&
                Objects.equals(comment, tug.comment) &&
                Objects.equals(fleet, tug.fleet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, availability, escort, bowToBow, comment, fleet);
    }
}