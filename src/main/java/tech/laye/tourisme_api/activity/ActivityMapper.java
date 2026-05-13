package tech.laye.tourisme_api.activity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.laye.tourisme_api.availability.AvailabilityMapper;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class ActivityMapper {

    private  final AvailabilityMapper availabilityMapper;

    public ActivityResponse toActivityResponse(Activity activity)
    {
        return ActivityResponse.builder()
                .id(activity.getId())
                .name(activity.getName())
                .description(activity.getDescription())
                .capacity(activity.getCapacity())
                .activityType(activity.getActivityType())
                .location(activity.getLocation())
                .price(activity.getPrice())
                .minimumAge(activity.getMinimumAge())
                .availabilities(
                        activity.getAvailabilities() != null
                                ? activity.getAvailabilities().stream()
                                  .map(availabilityMapper::toAvailabilityResponse)
                                  .toList()
                                : Collections.emptyList()
                )
                .build();
    }

    public Activity toActivity(ActivityRequest activityRequest) {
        return Activity.builder()
                .activityType(activityRequest.activityType())
                .name(activityRequest.name())
                .description(activityRequest.description())
                .price(activityRequest.price())
                .capacity(activityRequest.capacity())
                .minimumAge(activityRequest.minimumAge())
                .location(activityRequest.location())
                .build();
    }
}
