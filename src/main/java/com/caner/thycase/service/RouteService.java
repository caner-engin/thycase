package com.caner.thycase.service;

import com.caner.thycase.dto.RouteSearchDTO;
import com.caner.thycase.dto.TransportationRouteDTO;
import com.caner.thycase.model.Transportation;
import com.caner.thycase.model.Transportation.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final TransportationService transportationService;

    public Set<LinkedList<TransportationRouteDTO>> getRoutes(RouteSearchDTO routeSearchDTO) {
        Set<Transportation> flightTransportations = transportationService.getFlightTransportations(routeSearchDTO.getOperatingDay());

        Set<Transportation> originTransportations = transportationService.getTransportationByOriginLocationCode(
                routeSearchDTO.getOriginLocationCode(), routeSearchDTO.getOperatingDay());

        Set<Transportation> destinationTransportations = transportationService.getTransportationByDestinationLocationCode(
                routeSearchDTO.getDestinationLocationCode(), routeSearchDTO.getOperatingDay());

        originTransportations.addAll(flightTransportations);
        destinationTransportations.addAll(flightTransportations);

        Map<String, Transportation> flightByOriginMap = flightTransportations.stream()
                .collect(Collectors.toMap(transportation -> transportation.getOriginLocation().getCode(), Function.identity()));

        return getRoutes(routeSearchDTO, originTransportations, destinationTransportations, flightByOriginMap);
    }

    private static Set<LinkedList<TransportationRouteDTO>> getRoutes(
            RouteSearchDTO routeSearchDTO, Set<Transportation> originTransportations,
            Set<Transportation> destinationTransportations, Map<String, Transportation> flightByOriginMap) {
        Set<LinkedList<TransportationRouteDTO>> routes = new HashSet<>();
        for (Transportation transportationA : originTransportations) {
            if (routeSearchDTO.getOriginLocationCode().equals(transportationA.getOriginLocation().getCode())) {
                if (transportationA.getType() == Type.FLIGHT) {
                    if (transportationA.getDestinationLocation().getCode().equals(routeSearchDTO.getDestinationLocationCode())) {
                        LinkedList<TransportationRouteDTO> transportationRoute = new LinkedList<>();
                        transportationRoute.add(transportationA.toDTO());
                        routes.add(transportationRoute);
                    } else {
                        for (Transportation transportationB : destinationTransportations) {
                            if (transportationB.getType() != Type.FLIGHT
                                    && transportationA.getDestinationLocation().getCode().equals(transportationB.getOriginLocation().getCode())
                                    && transportationB.getDestinationLocation().getCode().equals(routeSearchDTO.getDestinationLocationCode())) {
                                LinkedList<TransportationRouteDTO> transportationRoute = new LinkedList<>();
                                transportationRoute.add(transportationA.toDTO());
                                transportationRoute.add(transportationB.toDTO());
                                routes.add(transportationRoute);
                            }
                        }
                    }
                } else {
                    Transportation transportationF = flightByOriginMap.get(transportationA.getDestinationLocation().getCode());
                    if (transportationF != null) {
                        if (transportationF.getDestinationLocation().getCode().equals(routeSearchDTO.getDestinationLocationCode())) {
                            LinkedList<TransportationRouteDTO> transportationRoute = new LinkedList<>();
                            transportationRoute.add(transportationA.toDTO());
                            transportationRoute.add(transportationF.toDTO());
                            routes.add(transportationRoute);
                        } else {
                            for (Transportation transportationB : destinationTransportations) {
                                if (transportationF.getDestinationLocation().getCode().equals(transportationB.getOriginLocation().getCode()) &&
                                        transportationB.getDestinationLocation().getCode().equals(routeSearchDTO.getDestinationLocationCode())) {
                                    LinkedList<TransportationRouteDTO> transportationRoute = new LinkedList<>();
                                    transportationRoute.add(transportationA.toDTO());
                                    transportationRoute.add(transportationF.toDTO());
                                    transportationRoute.add(transportationB.toDTO());
                                    routes.add(transportationRoute);
                                }
                            }
                        }
                    }
                }
            }
        }
        return routes;
    }

}