package tech.laye.tourisme_api.accommodation;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import tech.laye.tourisme_api.availability.Availability;
import tech.laye.tourisme_api.common.BaseAuditingEntity;
import tech.laye.tourisme_api.reservation.Reservation;
import tech.laye.tourisme_api.user.User;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Table(name = "accommodations")
public class Accommodation extends BaseAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private TypeAcc type;
    private String address;
    private int  capacity;
    private Long pricePerNight;
    //todo photo

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "accommodation")
    private List<Reservation> reservation;

    @OneToMany(mappedBy = "accommodation")
    private List<Availability> availabilities;
}
