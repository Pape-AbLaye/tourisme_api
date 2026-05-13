package tech.laye.tourisme_api.accommodation;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccommodationResponse {
    private Long id;
    private String name;
    private TypeAcc type;
    private String address;
    private int  capacity;
    private Long pricePerNight;
}
