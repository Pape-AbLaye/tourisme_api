package tech.laye.tourisme_api.reservation;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import tech.laye.tourisme_api.accommodation.Accommodation;
import tech.laye.tourisme_api.activity.Activity;
import tech.laye.tourisme_api.circuit.Circuit;
import tech.laye.tourisme_api.common.BaseAuditingEntity;
import tech.laye.tourisme_api.user.User;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Table(name = "reservations")
public class Reservation extends BaseAuditingEntity {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private TargetType targetType;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @ManyToOne
    @JoinColumn(name = "accommodation")
    private Accommodation accommodation;

    @ManyToOne
    @JoinColumn(name = "circuit")
    private Circuit circuit;

    @ManyToOne
    @JoinColumn(name = "activity")
    private Activity activity;

}

