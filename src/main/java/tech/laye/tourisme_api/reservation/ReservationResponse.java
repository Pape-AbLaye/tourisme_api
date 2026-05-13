package tech.laye.tourisme_api.reservation;

import lombok.*;
import tech.laye.tourisme_api.accommodation.AccommodationResponse;
import tech.laye.tourisme_api.activity.ActivityResponse;
import tech.laye.tourisme_api.circuit.CircuitResponse;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationResponse {

    private Long id;
    private TargetType targetType;
    private ReservationStatus status;
    private AccommodationResponse accommodationResponse;
    private CircuitResponse circuitResponse;
    private ActivityResponse activityResponse;
}
