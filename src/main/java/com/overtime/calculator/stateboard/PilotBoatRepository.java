package com.overtime.calculator.stateboard;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PilotBoatRepository extends JpaRepository<PilotBoat, Long> {
    // Custom query methods can be added here if needed
}