package com.caner.thycase.service;

import com.caner.thycase.dto.RouteSearchDTO;
import com.caner.thycase.dto.TransportationRouteDTO;
import com.caner.thycase.model.Location;
import com.caner.thycase.model.Transportation;
import com.caner.thycase.model.Transportation.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RouteServiceTest {

    @InjectMocks
    private RouteService routeService;

    @Mock
    private TransportationService transportationService;

    private Transportation flightTransportation;
    private Transportation originTransportation1;
    private Transportation originTransportation2;
    private Transportation destinationTransportation;
    private RouteSearchDTO routeSearchDTO;

    @BeforeEach
    public void setUp() {
        flightTransportation = createTransportation(
                "IST", "istanbul airport", "LHA", "London Heathrow Airport", Type.FLIGHT, 1);

        originTransportation1 = createTransportation(
                "TS", "Taksim Square", "IST", "istanbul airport", Type.BUS, 1);
        originTransportation2 = createTransportation(
                "TS", "Taksim Square", "IST", "istanbul airport", Type.SUBWAY, 1);

        destinationTransportation = createTransportation(
                "LHA", "London Heathrow Airport", "WS", "Wembley Stadium", Type.UBER, 1);

        routeSearchDTO = new RouteSearchDTO();
        routeSearchDTO.setOriginLocationCode("TS");
        routeSearchDTO.setDestinationLocationCode("WS");
        routeSearchDTO.setOperatingDay(1);
    }

    private Transportation createTransportation(String originCode, String originName, String destinationCode,
                                                String destinationName, Type type, int operatingDays) {
        Location originLocation = new Location();
        originLocation.setName(originName);
        originLocation.setCity("istanbul");
        originLocation.setCountry("türkiye");
        originLocation.setCode(originCode);

        Location destinationLocation = new Location();
        destinationLocation.setName(destinationName);
        destinationLocation.setCity("london");
        destinationLocation.setCountry("uk");
        destinationLocation.setCode(destinationCode);

        Transportation transportation = new Transportation();
        transportation.setOriginLocation(originLocation);
        transportation.setDestinationLocation(destinationLocation);
        transportation.setType(type);
        transportation.setOperatingDays(Collections.singleton(operatingDays));
        return transportation;
    }

    @Test
    void getRoutes_flightAsSecondTransportation() {
        when(transportationService.getFlightTransportations(anyInt())).thenReturn(new HashSet<>(Collections.singletonList(flightTransportation)));
        when(transportationService.getTransportationByOriginLocationCode(anyString(), anyInt()))
                .thenReturn(new HashSet<>(Arrays.asList(originTransportation1, originTransportation2)));
        when(transportationService.getTransportationByDestinationLocationCode(anyString(), anyInt()))
                .thenReturn(new HashSet<>(Collections.singletonList((destinationTransportation))));

        Set<LinkedList<TransportationRouteDTO>> routes = routeService.getRoutes(routeSearchDTO);
        assertEquals(2, routes.size());

        LinkedList<TransportationRouteDTO> firstRoute = routes.iterator().next();
        assertEquals(Type.BUS.getDescription(), firstRoute.get(0).getType());
        assertEquals("Taksim Square", firstRoute.get(0).getOriginLocationName());
        assertEquals("istanbul airport", firstRoute.get(0).getDestinationLocationName());
        assertEquals(Type.FLIGHT.getDescription(), firstRoute.get(1).getType());
        assertEquals("istanbul airport", firstRoute.get(1).getOriginLocationName());
        assertEquals("London Heathrow Airport", firstRoute.get(1).getDestinationLocationName());
        assertEquals(Type.UBER.getDescription(), firstRoute.get(2).getType());
        assertEquals("London Heathrow Airport", firstRoute.get(2).getOriginLocationName());
        assertEquals("Wembley Stadium", firstRoute.get(2).getDestinationLocationName());


    }

}
