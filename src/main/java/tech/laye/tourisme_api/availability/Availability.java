package tech.laye.tourisme_api.availability;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import tech.laye.tourisme_api.accommodation.Accommodation;
import tech.laye.tourisme_api.activity.Activity;
import tech.laye.tourisme_api.circuit.Circuit;
import tech.laye.tourisme_api.common.BaseAuditingEntity;
import tech.laye.tourisme_api.user.User;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Table(name = "availabilities")
public class Availability extends BaseAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean isAvailable;
    private int totalSlots;
    private int bookedSlots;

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @ManyToOne
    @JoinColumn(name = "circuit")
    private Circuit circuit;

    @ManyToOne
    @JoinColumn(name = "activity")
    private Activity activity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
