package com.caner.thycase.repository;

import com.caner.thycase.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Location findByCode(String code);
}