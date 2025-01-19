package com.caner.thycase.controller;

import com.caner.thycase.dto.RouteSearchDTO;
import com.caner.thycase.dto.TransportationRouteDTO;
import com.caner.thycase.service.RouteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.Set;

@RestController
@RequestMapping("route")
@RequiredArgsConstructor
@Validated
public class RouteController {

    private final RouteService routeService;

    @PostMapping
    @Operation(summary = "find routes", tags = {"ROUTE"})
    @ApiResponse(responseCode = "200", description = "Success")
    @ResponseStatus(HttpStatus.OK)
    public Set<LinkedList<TransportationRouteDTO>> findRoute(@Valid @RequestBody RouteSearchDTO routeSearchDTO) {
        return routeService.getRoutes(routeSearchDTO);
    }

}
