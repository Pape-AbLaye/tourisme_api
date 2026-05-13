package tech.laye.tourisme_api.availability;



import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AvailabilityRequest(
        @NotNull(message = "startDate cannot be null")
        LocalDateTime startDate,
        @NotNull(message = "endDate cannot be null")
        LocalDateTime endDate,

        Integer totalSlots,
        Long accommodationId,
        Long circuitId,
        Long activityId

) {
}
