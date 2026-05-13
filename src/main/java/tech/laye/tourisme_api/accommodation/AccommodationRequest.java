package tech.laye.tourisme_api.accommodation;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AccommodationRequest(
        @NotEmpty(message = "name cannot be empty")
        String name,
        @NotNull(message = "type cannot be null ")
        TypeAcc type,
        @NotEmpty(message = "address is required")
        String address,
        @NotEmpty(message = "capacity cannot be empty")
        int  capacity,
        @NotEmpty(message = "price cannot be empty")
        Long pricePerNight

) {
}