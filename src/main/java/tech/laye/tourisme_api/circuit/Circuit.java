package tech.laye.tourisme_api.circuit;

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
@Table(name = "circuits")
public class Circuit extends BaseAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private int  duration;
    private int  maxParticipants;
    private String language;
    private Long price;
    //todo photo

    @OneToMany(mappedBy = "circuit")
    private List<Reservation> reservation;

    @OneToMany(mappedBy = "circuit")
    private List<Availability> availabilities;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
