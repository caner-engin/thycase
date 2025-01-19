package com.caner.thycase.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RouteSearchDTO {

    @NotNull
    private String originLocationCode;

    @NotNull
    private String destinationLocationCode;

    private int operatingDay;
}
