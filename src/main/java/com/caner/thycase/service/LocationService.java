package com.caner.thycase.service;

import com.caner.thycase.exception.OtherException;
import com.caner.thycase.model.Location;
import com.caner.thycase.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public Location getLocationByCode(String code) {
        return locationRepository.findByCode(code);
    }

    public Location save(Location location) {
        Location existingLocation = getLocationByCode(location.getCode());
        if (existingLocation != null) {
            throw new OtherException("location already exist");
        }
        return locationRepository.save(location);
    }

}
