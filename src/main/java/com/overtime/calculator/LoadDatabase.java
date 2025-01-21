package com.overtime.calculator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;

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

            //log.info("Preloading {}", repository.save(shiftThree));
        };
    }
}