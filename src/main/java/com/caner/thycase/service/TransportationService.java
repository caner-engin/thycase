package com.caner.thycase.service;

import com.caner.thycase.exception.NotFoundException;
import com.caner.thycase.exception.OtherException;
import com.caner.thycase.model.Location;
import com.caner.thycase.model.Transportation;
import com.caner.thycase.model.Transportation.Type;
import com.caner.thycase.repository.TransportationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransportationService {

    private final TransportationRepository transportationRepository;
    private final LocationService locationService;

    public Transportation save(Transportation transportation) {
        if (transportation.getOriginLocation() == null || transportation.getDestinationLocation() == null) {
            throw new OtherException("origin and destination location cannot be null");
        }
        if (transportation.getOriginLocation().getCode() == null || transportation.getDestinationLocation().getCode() == null) {
            throw new OtherException("origin and destination location codes cannot be null");
        }
        Location originLocation = locationService.getLocationByCode(transportation.getOriginLocation().getCode());
        Location destinationLocation = locationService.getLocationByCode(transportation.getDestinationLocation().getCode());
        if (originLocation == null || destinationLocation == null) {
            throw new NotFoundException("location not exist");
        }
        transportation.setOriginLocation(originLocation);
        transportation.setDestinationLocation(destinationLocation);
        return transportationRepository.save(transportation);
    }

    public Transportation update(Long id, Transportation transportation) {
        Optional<Transportation> existingTransportationOpt = transportationRepository.findById(id);
        if (existingTransportationOpt.isEmpty()) {
            throw new NotFoundException("transportation not found");
        }
        if (transportation.getOriginLocation() == null || transportation.getDestinationLocation() == null) {
            throw new OtherException("origin and destination location cannot be null");
        }
        if (transportation.getOriginLocation().getCode() == null || transportation.getDestinationLocation().getCode() == null) {
            throw new OtherException("origin and destination location codes cannot be null");
        }
        Location originLocation = locationService.getLocationByCode(transportation.getOriginLocation().getCode());
        Location destinationLocation = locationService.getLocationByCode(transportation.getDestinationLocation().getCode());
        Transportation existingTransportation = existingTransportationOpt.get();
        existingTransportation.setType(transportation.getType());
        existingTransportation.setOriginLocation(originLocation);
        existingTransportation.setDestinationLocation(destinationLocation);
        existingTransportation.setType(transportation.getType());
        existingTransportation.setOperatingDays(transportation.getOperatingDays());
        return transportationRepository.save(existingTransportation);
    }

    public Transportation getById(Long id) {
        Optional<Transportation> byId = transportationRepository.findById(id);
        return byId.orElse(null);
    }

    public void delete(Long id) {
        Optional<Transportation> existingTransportationOpt = transportationRepository.findById(id);
        if (existingTransportationOpt.isEmpty()) {
            throw new NotFoundException("transportation not found");
        }
        transportationRepository.deleteById(id);
    }

    @Cacheable(value = "transportationCache", key = "#root.methodName + '_' + #originLocationCode + '_' + #operatingDay", cacheResolver = "ehCacheResolver")
    public Set<Transportation> getTransportationByOriginLocationCode(String originLocationCode, int operatingDay) {
        Set<Transportation> originTransportations = transportationRepository.findByOriginLocation_code(originLocationCode);
        return filterByOperatingDay(originTransportations, operatingDay);
    }

    @Cacheable(value = "transportationCache", key = "#root.methodName + '_' + #destinationLocationCode + '_' + #operatingDay", cacheResolver = "ehCacheResolver")
    public Set<Transportation> getTransportationByDestinationLocationCode(String destinationLocationCode, int operatingDay) {
        Set<Transportation> destinationTransportations = transportationRepository.findByDestinationLocation_code(destinationLocationCode);
        return filterByOperatingDay(destinationTransportations, operatingDay);
    }

    @Cacheable(value = "transportationCache", key = "#root.methodName + '_' + #operatingDay", cacheResolver = "ehCacheResolver")
    public Set<Transportation> getFlightTransportations(int operatingDay) {
        Set<Transportation> flightTransportations = transportationRepository.findByType(Type.FLIGHT);
        return filterByOperatingDay(flightTransportations, operatingDay);
    }

    private Set<Transportation> filterByOperatingDay(Set<Transportation> transportationSet, int operatingDay) {
        return transportationSet.stream()
                .filter(transportation -> transportation.getOperatingDays().contains(operatingDay))
                .collect(Collectors.toSet());
    }

}
