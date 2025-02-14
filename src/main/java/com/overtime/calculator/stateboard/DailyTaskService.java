package com.overtime.calculator.stateboard;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DailyTaskService {

    // Assuming you have autowired your repositories or services here
    private final PilotRepository pilotRepository;
    private final TugRepository tugRepository;
    private final PilotBoatRepository pilotBoatRepository;

    // Constructor injection for repositories/services
     public DailyTaskService(PilotRepository pilotRepository, TugRepository tugRepository, PilotBoatRepository pilotBoatRepository) {
         this.pilotRepository = pilotRepository;
         this.tugRepository = tugRepository;
         this.pilotBoatRepository = pilotBoatRepository;
    }

    @Scheduled(cron = "0 1 0 * * ?") // This cron expression represents 0600 every day
    public void performDailyTask() {

         pilotRepository.findAll().forEach(pilot -> {
             ArrayList<Integer> availability = pilot.getAvailability();
             availability.remove(0);
             availability.add(1);
             pilot.setAvailability(availability);
             pilotRepository.save(pilot);
         });

        tugRepository.findAll().forEach(tug -> {
            ArrayList<Integer> availability = tug.getAvailability();
            availability.remove(0);
            availability.add(1);
            tug.setAvailability(availability);
            tugRepository.save(tug);
        });

        pilotBoatRepository.findAll().forEach(pilotBoat -> {
            ArrayList<Integer> availability = pilotBoat.getAvailability();
            availability.remove(0);
            availability.add(1);
            pilotBoat.setAvailability(availability);
            pilotBoatRepository.save(pilotBoat);
        });
        // Your logic to perform actions on the entity classes
        // For example:
        // pilotRepository.updateSomeField();
        // tugRepository.updateSomeField();
        // pilotBoatRepository.updateSomeField();

        System.out.println("Daily task executed at 0600");
    }
}