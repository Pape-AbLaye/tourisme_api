package tech.laye.tourisme_api.activity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ActivityRequest(
        @NotEmpty(message = "activity's name cannot be empty")
        String name,
        String description,
        @NotEmpty(message = "capacity required")
        int capacity,
        @NotNull(message = "activity's type cannot be empty")
        Activity_type activityType,
        @NotNull(message = "price is required")
        Long price,
        int minimumAge,
        @NotEmpty(message = "location required")
        String location
) {
}