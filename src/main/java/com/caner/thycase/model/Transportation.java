package com.caner.thycase.model;

import com.caner.thycase.dto.TransportationRouteDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Transportation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
	@EqualsAndHashCode.Include
    private Type type;

    @ManyToOne
    @JoinColumn(name = "origin_location_id", nullable = false)
	@EqualsAndHashCode.Include
    private Location originLocation;

    @ManyToOne
    @JoinColumn(name = "destination_location_id", nullable = false)
	@EqualsAndHashCode.Include
    private Location destinationLocation;

    private Set<Integer> operatingDays;

	public TransportationRouteDTO toDTO() {
        return new TransportationRouteDTO(this.type.getDescription(), this.getOriginLocation().getName(), this.getDestinationLocation().getName());
    }

    @Getter
    public enum Type {
        FLIGHT("Flight"),
        BUS("Bus"),
        SUBWAY("Subway"),
        UBER("Uber");

        private final String description;

        Type(String description) {
            this.description = description;
        }
    }
}
