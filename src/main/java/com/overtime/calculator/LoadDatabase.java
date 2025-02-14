package com.overtime.calculator;

import com.overtime.calculator.stateboard.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(OvertimeShiftRepository repository) {
        OvertimeShift shiftOne = new Calculator(
                java.time.LocalDate.of(2024, 6, 4),
                "operator",
                "E")
                .getOvertimeShift();

        OvertimeShift shiftTwo = new Calculator(
                java.time.LocalDate.of(2024, 6, 5),
                "operator",
                "E")
                .getOvertimeShift();

        OvertimeShift shiftThree = new Calculator(
                java.time.LocalDate.of(2024, 6, 6),
                "officer",
                "E")
                .getOvertimeShift();

        return args -> {
            //log.info("Preloading " + repository.save(shiftOne));
            //log.info("Preloading {}", repository.save(shiftTwo));
        };
    }

    @Bean
    CommandLineRunner initTugDatabase(TugRepository tugRepository, PilotBoatRepository pilotBoatRepository, PilotRepository pilotRepository) {
        return args -> {
            // Clear existing data
            tugRepository.deleteAll();
            pilotBoatRepository.deleteAll();
            pilotRepository.deleteAll();

            ArrayList<String> tugName = new ArrayList<>();
            tugName.add("Lindsway");
            tugName.add("Watwick");
            tugName.add("Gelliswick");
            tugName.add("Musselwick");
            tugName.add("Kilroom");
            tugName.add("Caldey");
            tugName.add("Haven");
            tugName.add("Pembroke");
            tugName.add("Waterston");
            // Preload 9 Tugs
            for (int i = 0; i < 9; i++) {
                Tug tug = new Tug();
                tug.setName(tugName.get(i-0));
                tug.setEscort(new ArrayList<>(List.of(true, false, true)));
                tug.setBowToBow(new ArrayList<>(List.of(false, true, false)));
                tug.setComment(new ArrayList<>(List.of("Good performance", "Needs maintenance")));
                tug.setFleet(i <= 5 ? "gas" : "oil"); // First 5 tugs in "gas" fleet, next 4 in "oil" fleet
                tug.setAvailability(new ArrayList<>(List.of(2, 2, 2, 2, 2))); // Set availability to (2, 2, 2, 2, 2)

                log.info("Preloading " + tugRepository.save(tug));
            }

            ArrayList<String> pilotBoatName = new ArrayList<>();
            pilotBoatName.add("Picton");
            pilotBoatName.add("Skomer");
            pilotBoatName.add("St. Davids");

            // Preload 3 Pilot Boats
            for (int i = 0; i < 3; i++) {
                PilotBoat pilotBoat = new PilotBoat();
                pilotBoat.setName(pilotBoatName.get(i));
                pilotBoat.setAvailability(new ArrayList<>(List.of(2, 2, 2, 2, 2))); // Set availability to (2, 2, 2, 2, 2)
                pilotBoat.setBoatPlace(i <= 2 ? new ArrayList<>(List.of("outside")) : new ArrayList<>(List.of("inside"))); // First 2 outside, last 1 inside

                log.info("Preloading " + pilotBoatRepository.save(pilotBoat));
            }


            // Preload 4 Pilots
            for (int i = 1; i <= 4; i++) {
                Pilot pilot = new Pilot();
                pilot.setPilotNumber(i); // Pilot numbers 1, 2, 3, 4
                pilot.setPilotClass(new ArrayList<>(List.of(1, 2, 3))); // Example pilot classes
                pilot.setAvailability(new ArrayList<>(List.of(2, 2, 2, 2, 2))); // Set availability to (2, 2, 2, 2, 2)

                log.info("Preloading " + pilotRepository.save(pilot));
            }
        };
    }
}