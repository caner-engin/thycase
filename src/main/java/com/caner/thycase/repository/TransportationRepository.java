package com.caner.thycase.repository;

import com.caner.thycase.model.Transportation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface TransportationRepository extends JpaRepository<Transportation, Long> {

    Set<Transportation> findByOriginLocation_code(String originLocationCode);

    Set<Transportation> findByDestinationLocation_code(String destinationLocationCode);

    Set<Transportation> findByType(Transportation.Type type);

}