package tech.laye.tourisme_api.reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.laye.tourisme_api.accommodation.Accommodation;
import tech.laye.tourisme_api.accommodation.AccommodationMapper;
import tech.laye.tourisme_api.activity.Activity;
import tech.laye.tourisme_api.activity.ActivityMapper;
import tech.laye.tourisme_api.availability.AvailabilityRepository;
import tech.laye.tourisme_api.circuit.Circuit;
import tech.laye.tourisme_api.circuit.CircuitMapper;

@Service
@RequiredArgsConstructor
public class ReservationMapper {

    private final ActivityMapper activityMapper;
    private final CircuitMapper circuitMapper;
    private  final AccommodationMapper accommodationMapper;
    private final AvailabilityRepository availabilityRepository;

    public ReservationResponse toReservationResponse(Reservation reservation){
        return ReservationResponse.builder()
                .status(reservation.getStatus())
                .id(reservation.getId())
                .targetType(reservation.getTargetType())
                .activityResponse(
                        reservation.getActivity() != null
                                ? activityMapper.toActivityResponse(reservation.getActivity())
                                : null
                )
                .accommodationResponse(
                        reservation.getAccommodation() != null
                                ? accommodationMapper.toAccommodationResponse(reservation.getAccommodation())
                                : null
                )
                .circuitResponse(
                        reservation.getCircuit() != null
                                ? circuitMapper.toACircuitResponse(reservation.getCircuit())
                                : null
                )
                .build();
    }

    public Reservation toReservation(ReservationRequest request){

        Reservation.ReservationBuilder builder = Reservation.builder()
                .targetType(request.targetType())
                .status(request.status());

        switch (request.targetType()) {

            case ACTIVITY -> {
                if (request.activity_id() == null)
                    throw new IllegalArgumentException("activity_id is required");

                builder.activity(
                        Activity.builder().id(request.activity_id()).build()
                );


            }

            case ACCOMMODATION -> {
                if (request.accommodation_id() == null)
                    throw new IllegalArgumentException("accommodation_id is required");

                builder.accommodation(
                        Accommodation.builder().id(request.accommodation_id()).build()
                );
            }

            case CIRCUIT -> {
                if (request.circuit_id() == null)
                    throw new IllegalArgumentException("circuit_id is required");

                builder.circuit(
                        Circuit.builder().id(request.circuit_id()).build()
                );
            }
        }

        return builder.build();
    }

}
