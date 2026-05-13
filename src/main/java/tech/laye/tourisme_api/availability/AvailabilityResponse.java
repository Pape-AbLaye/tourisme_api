package tech.laye.tourisme_api.availability;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvailabilityResponse {
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean isAvailable;
    private int totalSlots;
    private int bookedSlots;
}
