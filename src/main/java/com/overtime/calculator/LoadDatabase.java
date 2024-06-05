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

        return args -> {
            log.info("Preloading " + repository.save(new OvertimeShift
                    (LocalDate.of(2024, 6, 5),
                            "operator",
                            new Vtso("Chris",
                                    "E",
                                    2,
                                    9,
                                    "operator") )));

            log.info("Preloading " + repository.save(new OvertimeShift
                    (LocalDate.of(2024, 6, 4),
                            "operator",
                            new Vtso("Chris",
                                    "E",
                                    2,
                                    9,
                                    "operator") )));
        };
    }
}