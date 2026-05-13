package tech.laye.tourisme_api.activity;

import lombok.*;
import tech.laye.tourisme_api.availability.AvailabilityResponse;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityResponse {

    private Long id;
    private String name;
    private String description;
    private int capacity;
    private Activity_type activityType;
    private Long price;
    private int minimumAge;
    private String location;
    private List<AvailabilityResponse> availabilities;
}
