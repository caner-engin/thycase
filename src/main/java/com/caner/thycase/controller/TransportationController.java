package com.caner.thycase.controller;

import com.caner.thycase.model.Transportation;
import com.caner.thycase.service.TransportationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("transportation")
@RequiredArgsConstructor
@Validated
public class TransportationController {

    private final TransportationService transportationService;

    @PostMapping
    @Operation(summary = "creates new transportation", tags = {"TRANSPORTATION"})
    @ApiResponse(responseCode = "200", description = "Success")
    @ResponseStatus(HttpStatus.CREATED)
    public Transportation createTransportation(@Valid @RequestBody Transportation transportation) {
        return transportationService.save(transportation);
    }

    @PutMapping
    @Operation(summary = "update transportation", tags = {"TRANSPORTATION"})
    @ApiResponse(responseCode = "200", description = "Success")
    @ResponseStatus(HttpStatus.OK)
    public Transportation updateTransportation(@Valid @RequestBody Transportation transportation) {
        return transportationService.update(transportation.getId(), transportation);
    }

    @GetMapping
    @Operation(summary = "get transportation", tags = {"TRANSPORTATION"})
    @ApiResponse(responseCode = "200", description = "Success")
    @ResponseStatus(HttpStatus.OK)
    public Transportation updateTransportation(@Valid @PathVariable Long id) {
        return transportationService.getById(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "get transportation", tags = {"TRANSPORTATION"})
    @ApiResponse(responseCode = "200", description = "Success")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTransportation(@Valid @PathVariable Long id) {
        transportationService.delete(id);
    }

}
