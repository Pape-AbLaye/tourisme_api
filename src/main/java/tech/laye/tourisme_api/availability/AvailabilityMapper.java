package tech.laye.tourisme_api.availability;

import org.springframework.stereotype.Service;
import tech.laye.tourisme_api.accommodation.Accommodation;
import tech.laye.tourisme_api.activity.Activity;
import tech.laye.tourisme_api.circuit.Circuit;

@Service
public class AvailabilityMapper {

    public AvailabilityResponse toAvailabilityResponse(Availability availability)
    {
        return AvailabilityResponse.builder()
                .id(availability.getId())
                .startDate(availability.getStartDate())
                .endDate(availability.getEndDate())
                .isAvailable(availability.isAvailable())
                .totalSlots(availability.getTotalSlots())
                .bookedSlots(availability.getBookedSlots())
                .build();
    }

    public Availability toAvailability(AvailabilityRequest request) {
        Availability availability = Availability.builder()
                .startDate(request.startDate())
                .endDate(request.endDate())
                .totalSlots(request.totalSlots() != null ? request.totalSlots() : 0)
                .bookedSlots(0)
                .isAvailable(true)
                .build();

        if (request.accommodationId() != null) {
            availability.setAccommodation(Accommodation.builder().id(request.accommodationId()).build());
        }
        if (request.circuitId() != null) {
            availability.setCircuit(Circuit.builder().id(request.circuitId()).build());
        }
        if (request.activityId() != null) {
            availability.setActivity(Activity.builder().id(request.activityId()).build());
        }

        return availability;
    }
}
