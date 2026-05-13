package tech.laye.tourisme_api.reservation;

import jakarta.validation.constraints.NotNull;

public record ReservationRequest(

        @NotNull(message = "target cannot be empty")
        TargetType targetType,
        @NotNull(message = "status cannot be empty")
        ReservationStatus status,
        Long availabilityId,
        int quantity,
        Long accommodation_id,
        Long circuit_id,
        Long activity_id
) {
}