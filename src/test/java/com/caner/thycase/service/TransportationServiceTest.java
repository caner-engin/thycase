package com.caner.thycase.service;

import com.caner.thycase.exception.OtherException;
import com.caner.thycase.model.Location;
import com.caner.thycase.model.Transportation;
import com.caner.thycase.model.Transportation.Type;
import com.caner.thycase.repository.TransportationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransportationServiceTest {

    @InjectMocks
    private TransportationService transportationService;

    @Mock
    private TransportationRepository transportationRepository;

    @Mock
    private LocationService locationService;

    private Transportation transportation;
    private Location originLocation;
    private Location destinationLocation;

    @BeforeEach
    public void setUp() {
        originLocation = new Location();
        originLocation.setName("istanbul airport");
        originLocation.setCity("istanbul");
        originLocation.setCountry("türkiye");
        originLocation.setCode("IST");

        destinationLocation = new Location();
        destinationLocation.setName("Taksim Square");
        destinationLocation.setCity("istanbul");
        destinationLocation.setCountry("türkiye");
        destinationLocation.setCode("TS");

        transportation = new Transportation();
        transportation.setOriginLocation(originLocation);
        transportation.setDestinationLocation(destinationLocation);
        transportation.setType(Type.FLIGHT);
        transportation.setOperatingDays(Collections.singleton(1));
    }

    @Test
    void save() {
        when(transportationRepository.save(any(Transportation.class))).thenReturn(transportation);
        when(locationService.getLocationByCode(anyString())).thenReturn(originLocation, destinationLocation);

        transportationService.save(transportation);
        verify(transportationRepository, times(1)).save(any(Transportation.class));
    }

    @Test
    void save_originLocationIsNullException() {
        transportation.setOriginLocation(null);

        assertThrows(OtherException.class, () -> transportationService.save(transportation));
    }

    @Test
    void save_originLocationCodeIsNullException() {
        transportation.getOriginLocation().setCode(null);

        assertThrows(OtherException.class, () -> transportationService.save(transportation));
    }

    @Test
    void update() {
        when(transportationRepository.findById(anyLong())).thenReturn(Optional.of(transportation));
        when(transportationRepository.save(any(Transportation.class))).thenReturn(transportation);
        when(locationService.getLocationByCode(anyString())).thenReturn(originLocation, destinationLocation);

        transportationService.update(1L, transportation);
        verify(transportationRepository, times(1)).save(any(Transportation.class));
    }

    @Test
    void originTransportations() {
        when(transportationRepository.findByDestinationLocation_code(anyString())).thenReturn(Collections.singleton(transportation));

        Set<Transportation> transportations = transportationService.getTransportationByDestinationLocationCode("IST", 1);
        assertFalse(transportations.isEmpty());
    }

    @Test
    void originTransportations_operationDayNotMatch() {
        when(transportationRepository.findByDestinationLocation_code(anyString())).thenReturn(Collections.singleton(transportation));

        Set<Transportation> transportations = transportationService.getTransportationByDestinationLocationCode("IST", 2);
        assertTrue(transportations.isEmpty());
    }

}
