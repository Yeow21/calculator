package com.overtime.calculator;

import org.springframework.data.jpa.repository.JpaRepository;

// object to store, id is type long
public interface VtsoRepository extends JpaRepository<Vtso, Long>{
}
