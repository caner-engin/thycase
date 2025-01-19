package com.caner.thycase.controller;

import com.caner.thycase.model.Location;
import com.caner.thycase.service.LocationService;
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

@RestController
@RequestMapping("location")
@RequiredArgsConstructor
@Validated
public class LocationController {

    private final LocationService locationService;

    @PostMapping
    @Operation(summary = "creates new location", tags = {"LOCATION"})
    @ApiResponse(responseCode = "200", description = "Success")
    @ResponseStatus(HttpStatus.CREATED)
    public Location createLocation(@Valid @RequestBody Location location) {
        return locationService.save(location);
    }

}
