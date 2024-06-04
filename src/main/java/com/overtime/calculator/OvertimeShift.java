package com.overtime.calculator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class OvertimeShift
{
    private LocalDate date;
    private Vtso officerCovered;
    private String deskSideCovered;
    private int shiftNumber;
    private String letter;

    public OvertimeShift(LocalDate date, String deskSideCovered, Vtso officer)
    {
        this.deskSideCovered = deskSideCovered;
        officerCovered = officer;
        this.date = date;



        // working out the shift number of the person who needs cover
        long daysSinceFirstShift = ChronoUnit.DAYS.between(officer.getFirstShiftDate(), this.getDate());
        System.out.println("daysSinceFirstShift: " + daysSinceFirstShift);
        long daysIntoRotation = daysSinceFirstShift % officer.getRotationLength();
        System.out.println("daysIntoRotation: " + daysIntoRotation);
        if (daysIntoRotation <= officer.getRotationLengthMinusLeave()) {
            shiftNumber = (int) (daysIntoRotation % 8) + 1;
            System.out.println("shiftNumber = " + shiftNumber);
        } else {
            shiftNumber = (int) daysIntoRotation;
        }
    }

    public HashMap<String, ArrayList<String>> calculateOvertimeList(ArrayList<Vtso> officers)
    {
        HashMap<String, ArrayList<String>> coverList = new HashMap<>();
        coverList.put("officer", new ArrayList<String>());
        coverList.put("operator", new ArrayList<String>());

        // filter DO's using stream
        List<Vtso> dutyOfficers = officers
            .stream()
                .filter(v -> v.getDeskSide().equals("officer"))
                .toList();

        // delete - test
        for (Vtso officer : dutyOfficers) {
            System.out.println("1" + officer.getName());
        }

        // filer DO's using stream
        List<Vtso> marineOperators = officers
                .stream()
                .filter(v -> v.getDeskSide().equals("operator"))
                .toList();

        // delete - test
        for (Vtso operator : marineOperators) {
            System.out.println("3" + operator.getName());
        }


        int otherShiftNumber = shiftNumber == 1 || shiftNumber == 3 ? shiftNumber + 1 : shiftNumber - 1;
        System.out.println("shiftNumber = " + shiftNumber + " && otherShiftNumber = " + otherShiftNumber);
        int officerShiftNumber = deskSideCovered.equals("officer") ? shiftNumber : otherShiftNumber;
        System.out.println("Officer shift number = " + officerShiftNumber);
        int operatorShiftNumber = deskSideCovered.equals("operator") ? shiftNumber : otherShiftNumber;
        System.out.println("Operator shift number = " + operatorShiftNumber);

        // calculate officers for cover
        switch (officerShiftNumber) {
            case 1:

                // get first person for cover
                for (Vtso officer : dutyOfficers) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation == 7) {
                        coverList.get("officer").add(officer.getLetter());
                    }
                }

                // get 2nd person for cover (on 10 off)
                for (Vtso officer : dutyOfficers) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation >= 26) {
                        coverList.get("officer").add(officer.getLetter() + "(10)");
                    }
                }

                // get 3rd person for cover (on 10 off)
                for (Vtso officer : dutyOfficers) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation >= 9 && daysIntoRotation < 26) {
                        coverList.get("officer").add(officer.getLetter() + "(10)");
                    }
                }
                break;

            case 2:
                // get first person for cover
                for (Vtso officer : dutyOfficers) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation == 6) {
                        coverList.get("officer").add(officer.getLetter());
                    }
                }

                // get 2nd person for cover
                for (Vtso officer : dutyOfficers) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    System.out.println(daysIntoRotation);
                    if (daysIntoRotation == 8) {
                        coverList.get("officer").add(officer.getLetter());
                        System.out.println("This one fired");
                        System.out.println(officer.getLetter());
                    }
                }

                // get 2nd person for cover (on 10 off)
                for (Vtso officer : dutyOfficers) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation >= 26) {
                        coverList.get("officer").add(officer.getLetter()+ "(10)");
                        System.out.println("Then This one fired");
                        System.out.println(officer.getLetter() );
                    }
                }

                // get 3rd person for cover (on 10 off)
                for (Vtso officer : dutyOfficers) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation >= 9 && daysIntoRotation < 26) {
                        coverList.get("officer").add(officer.getLetter() + "(10)");
                        System.out.println("now This one fired");
                        System.out.println(officer.getLetter());
                    }
                }
                break;

            case 3:
                for (Vtso officer : dutyOfficers) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation == 7) {
                        coverList.get("officer").add(officer.getLetter());
                    }
                }

                for (Vtso officer : dutyOfficers) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation == 5) {
                        coverList.get("officer").add(officer.getLetter());
                    }
                }

                for (Vtso officer : dutyOfficers) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation >= 21 && daysIntoRotation <=25) {
                        coverList.get("officer").add(officer.getLetter()+ "(10)");
                    }
                }

                for (Vtso officer : dutyOfficers) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation > 25) {
                        coverList.get("officer").add(officer.getLetter()+ "(10)");
                    }
                }
                break;

            case 4:
                for (Vtso officer : dutyOfficers) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation == 6) {
                        coverList.get("officer").add(officer.getLetter());
                    }
                }

                for (Vtso officer : dutyOfficers) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation >= 21 && daysIntoRotation <=25) {
                        coverList.get("officer").add(officer.getLetter()+ "(10)");
                    }
                }

                for (Vtso officer : dutyOfficers) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation > 25) {
                        coverList.get("officer").add(officer.getLetter()+ "(10)");
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
                        coverList.get("operator").add(officer.getLetter());
                    }
                }

                for (Vtso officer : marineOperators) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation > 46) {
                        coverList.get("operator").add(officer.getLetter() + "(14)");
                    }
                }

                for (Vtso officer : marineOperators) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation >= 38 && daysIntoRotation <= 46) {
                        coverList.get("operator").add(officer.getLetter() + "(14)");
                    }
                }
                break;
            case 2:
                for (Vtso officer : marineOperators) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation == 6) {
                        coverList.get("operator").add(officer.getLetter());
                    }
                }

                for (Vtso officer : marineOperators) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation == 8) {
                        coverList.get("operator").add(officer.getLetter());
                    }
                }

                for (Vtso officer : marineOperators) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation > 46) {
                        coverList.get("operator").add(officer.getLetter() + "(14)");
                    }
                }

                for (Vtso officer : marineOperators) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation >= 38 && daysIntoRotation <= 46) {
                        coverList.get("operator").add(officer.getLetter() + "(14)");
                    }
                }
                break;

            case 3:
                for (Vtso officer : marineOperators) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation == 7) {
                        coverList.get("operator").add(officer.getLetter());
                    }
                }

                for (Vtso officer : marineOperators) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation == 5) {
                        coverList.get("operator").add(officer.getLetter());
                    }
                }

                for (Vtso officer : marineOperators) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation > 46 && daysIntoRotation < 50) {
                        coverList.get("operator").add(officer.getLetter() + "(14)");
                    }
                }

                for (Vtso officer : marineOperators) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation >= 37 && daysIntoRotation <= 46) {
                        coverList.get("operator").add(officer.getLetter() + "(14)");
                    }
                }

                break;

            case 4:
                for (Vtso officer : marineOperators) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation == 6) {
                        coverList.get("operator").add(officer.getLetter());
                    }
                }

                for (Vtso officer : marineOperators) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation > 46 && daysIntoRotation < 50) {
                        coverList.get("operator").add(officer.getLetter() + "(14)");
                    }
                }

                for (Vtso officer : marineOperators) {
                    int daysIntoRotation = officer.daysIntoRotation(date);
                    if (daysIntoRotation >= 37 && daysIntoRotation <= 46) {
                        coverList.get("operator").add(officer.getLetter() + "(14)");
                    }
                }
                break;
        }
        return coverList;

    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Vtso getOfficerCovered() {
        return officerCovered;
    }

    public void setOfficerCovered(Vtso officerCovered) {
        this.officerCovered = officerCovered;
    }

    public String getDeskSideCovered() {
        return deskSideCovered;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }
    // --------------------------------------------------------------------------------------------------------- //

}
