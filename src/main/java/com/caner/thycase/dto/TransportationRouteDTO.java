package com.caner.thycase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransportationRouteDTO {

    private String type;
    private String originLocationName;
    private String destinationLocationName;
}
