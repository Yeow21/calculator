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
    private ArrayList<Boolean> rejectedOfficers = new ArrayList<>();
    private ArrayList<String> rejectedOfficersList= new ArrayList<>();
    private ArrayList<String> rejectedOperatorsList= new ArrayList<>();
    private ArrayList<Boolean> rejectedOperators = new ArrayList<>();
    private String officerConfirmed;
    private String deskSideConfirmed;
    @Column(length = 1000)
    private ArrayList<Vtso> officerList = new ArrayList<>();
    @Column(length = 1000)
    private ArrayList<Vtso> operatorList = new ArrayList<>();


    public OvertimeShift(){

    }

    public OvertimeShift(LocalDate date, String deskSide, String letter, ArrayList<Vtso>officerList, ArrayList<Vtso>operatorList) {
        this.date = date;
        this.deskSide = deskSide;
        this.letter = letter;
        this.officerList = officerList;
        this.operatorList = operatorList;
    }

    public void setShiftConfirmed()
    {
        for (int i = 0; i < rejectedOperators.size(); i++) {
            rejectedOperators.set(i, true);
        }
        for (int i = 0; i < rejectedOfficers.size(); i++) {
            rejectedOfficers.set(i, true);
        }
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

    public void populateRejectedLists()
    {
        if (rejectedOfficers.isEmpty()) {
            for (String officer : officersList) {
                rejectedOfficers.add(false);
            }
            for (String operator : operatorsList) {
                rejectedOperators.add(false);
            }
        }
    }

    public void rejectOfficer(String letter, String deskSide)
    {

        if (deskSide.equals("operator")) {
            for (Vtso operator : operatorList) {
                if (operator.getLetter().equals(letter)) {
                    operator.setCoverRejected();
                }
            }
        }

        if (deskSide.equals("officer")) {
            for (Vtso officer : officerList) {
                if (officer.getLetter().equals(letter)) {
                    officer.setCoverRejected();
                }
            }
        }
    }

    public void confirmOfficer(String letter, String deskSide)
    {
        if (deskSide.equals("operator")) {
            for (Vtso operator : operatorList) {
                if (operator.getLetter().equals(letter)) {
                    operator.setCoverConfirmed();
                }
            }
        }

        if (deskSide.equals("officer")) {
            for (Vtso officer : officerList) {
                if (officer.getLetter().equals(letter)) {
                    officer.setCoverConfirmed();
                }
            }
        }
    }


    public boolean confirmCover(String confirmedLetter, String confirmedDeskSide) {
        populateRejectedLists();

        if (deskSide.equals("operator")) { // check to see whether the operator or officer needs cover
            if (rejectedOperators.contains(false)) {
                String nextOperator = operatorsList.get(rejectedOperators.indexOf(false));
                if (nextOperator.equals(confirmedLetter) && confirmedDeskSide.equals("operator")) {
                    setOfficerConfirmed(confirmedLetter);
                    setDeskSideConfirmed(confirmedDeskSide);
                    return true;
                } else if (rejectedOfficers.contains(false)) {
                    String nextOfficer = officersList.get(rejectedOfficers.indexOf(false));
                    if (nextOfficer.equals(confirmedLetter) && confirmedDeskSide.equals("officer")) {
                        setOfficerConfirmed(confirmedLetter);
                        setDeskSideConfirmed(confirmedDeskSide);
                        return true;
                    }
                }
            }
            if (deskSide.equals("officer")) { // check to see whether the operator or officer needs cover
                if (rejectedOfficers.contains(false)) {
                    String aNextOfficer = officersList.get(rejectedOfficers.indexOf(false));
                    if (aNextOfficer.equals(confirmedLetter) && confirmedDeskSide.equals("officer")) {
                        setOfficerConfirmed(confirmedLetter);
                        setDeskSideConfirmed(confirmedDeskSide);
                        return true;
                    } else if (rejectedOperators.contains(false)) {
                        String aNextOperator = operatorsList.get(rejectedOperators.indexOf(false));
                        if (aNextOperator.equals(confirmedLetter) && confirmedDeskSide.equals("operator")) {
                            setOfficerConfirmed(confirmedLetter);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void removeOfficerFromCoverList(String letter, String deskSide)
    {
        if (deskSide.equals("operator")) {
            int indexOfRemovedOfficer = operatorsList.indexOf(letter);
            operatorsList.remove(letter);
            rejectedOperatorsList.add(indexOfRemovedOfficer, letter);
        } else if (deskSide.equals("officer")) {
            int indexOfRemovedOfficer = officersList.indexOf(letter);
            officersList.remove(letter);
            rejectedOfficersList.add(indexOfRemovedOfficer, letter);
        }
    }




























    public String getOfficerConfirmed() {
        return officerConfirmed;
    }

    public void setOfficerConfirmed(String officerConfirmed) {
        this.officerConfirmed = officerConfirmed;
    }

    public String getDeskSideConfirmed() {
        return deskSideConfirmed;
    }

    public void setDeskSideConfirmed(String deskSideConfirmed) {
        this.deskSideConfirmed = deskSideConfirmed;
    }



















    public ArrayList<Boolean> getRejectedOfficers()
    {
        return rejectedOfficers;
    }

    public ArrayList<Boolean> getRejectedOperators()
    {
        return rejectedOperators;
    }


















    public void setRejectedOfficers(ArrayList<Boolean> rejectedOfficers) {
        this.rejectedOfficers = rejectedOfficers;
    }

    public void setRejectedOperators(ArrayList<Boolean> rejectedOperators) {
        this.rejectedOperators = rejectedOperators;
    }























    @Override
    public String toString() {
        return "OvertimeShift{" +
                "id=" + id +
                ", date=" + date +
                ", deskSide='" + deskSide + '\'' +
                ", letter='" + letter + '\'' +
                ", coverMetaData=" + coverMetaData +
                ", officersList=" + officersList +
                ", operatorsList=" + operatorsList +
                ", rejectedOfficers=" + rejectedOfficers +
                ", rejectedOperators=" + rejectedOperators +
                ", officerConfirmed='" + officerConfirmed + '\'' +
                ", deskSideConfirmed='" + deskSideConfirmed + '\'' +
                '}';
    }

    public ArrayList<String> getRejectedOfficersList() {
        return rejectedOfficersList;
    }

    public void setRejectedOfficersList(ArrayList<String> rejectedOfficersList) {
        this.rejectedOfficersList = rejectedOfficersList;
    }

    public ArrayList<String> getRejectedOperatorsList() {
        return rejectedOperatorsList;
    }

    public void setRejectedOperatorsList(ArrayList<String> rejectedOperatorsList) {
        this.rejectedOperatorsList = rejectedOperatorsList;
    }

    public ArrayList<Vtso> getOfficerList() {
        return officerList;
    }

    public void setOfficerList(ArrayList<Vtso> officerList) {
        this.officerList = officerList;
    }

    public ArrayList<Vtso> getOperatorList() {
        return operatorList;
    }

    public void setOperatorList(ArrayList<Vtso> operatorList) {
        this.operatorList = operatorList;
    }
}
