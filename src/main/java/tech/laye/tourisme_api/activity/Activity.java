package tech.laye.tourisme_api.activity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import tech.laye.tourisme_api.availability.Availability;
import tech.laye.tourisme_api.common.BaseAuditingEntity;
import tech.laye.tourisme_api.reservation.Reservation;
import tech.laye.tourisme_api.user.PrestaireProfile;
import tech.laye.tourisme_api.user.User;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Table(name = "activity")
public class Activity extends BaseAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int capacity;
    private Activity_type activityType;
    private Long price;
    private int minimumAge;
    private String location;
    // todo photo

    @OneToMany(mappedBy = "activity")
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "activity")
    private List<Availability> availabilities;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
