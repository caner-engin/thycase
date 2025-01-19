package com.caner.thycase.service;

import com.caner.thycase.exception.OtherException;
import com.caner.thycase.model.Location;
import com.caner.thycase.repository.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

    @InjectMocks
    private LocationService locationService;

    @Mock
    private LocationRepository locationRepository;

    private Location location;

    @BeforeEach
    public void setUp() {
        location = new Location();
        location.setName("istanbul airport");
        location.setCity("istanbul");
        location.setCountry("türkiye");
        location.setCode("IST");
    }

    @Test
    void getLocationByCode() {
        when(locationRepository.findByCode(anyString())).thenReturn(location);
        Location locationByCode = locationService.getLocationByCode("IST");
        assertEquals("IST", locationByCode.getCode());
    }

    @Test
    void save() {
        when(locationRepository.findByCode(anyString())).thenReturn(null);
        Location newLocation = new Location();
        newLocation.setName("Taksim Square\t");
        newLocation.setCity("istanbul");
        newLocation.setCountry("türkiye");
        newLocation.setCode("TS");

        locationService.save(newLocation);
        verify(locationRepository, times(1)).save(any(Location.class));
    }

    @Test
    void save_locationAlreadyExist() {
        when(locationRepository.findByCode(anyString())).thenReturn(location);
        Location newLocation = new Location();
        newLocation.setName("Taksim Square\t");
        newLocation.setCity("istanbul");
        newLocation.setCountry("türkiye");
        newLocation.setCode("TS");

        assertThrows(OtherException.class, () -> locationService.save(newLocation));
    }

}
