package tech.laye.tourisme_api.accommodation;

import org.springframework.stereotype.Service;

@Service
public class AccommodationMapper {

    public AccommodationResponse toAccommodationResponse(Accommodation accommodation)
    {
        return AccommodationResponse.builder()
                .id(accommodation.getId())
                .name(accommodation.getName())
                .type(accommodation.getType())
                .address(accommodation.getAddress())
                .capacity(accommodation.getCapacity())
                .pricePerNight(accommodation.getPricePerNight())
                .build();
    }

    public Accommodation toAccommodation(AccommodationRequest accommodationRequest) {
        return Accommodation.builder()
                .name(accommodationRequest.name())
                .type(accommodationRequest.type())
                .address(accommodationRequest.address())
                .capacity(accommodationRequest.capacity())
                .pricePerNight(accommodationRequest.pricePerNight())
                .build();
    }
}
