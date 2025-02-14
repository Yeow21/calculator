package com.overtime.calculator.stateboard;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PilotRepository extends JpaRepository<Pilot, Long> {
    // Custom query methods can be added here if needed
}