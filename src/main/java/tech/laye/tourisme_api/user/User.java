package tech.laye.tourisme_api.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import tech.laye.tourisme_api.accommodation.Accommodation;
import tech.laye.tourisme_api.activity.Activity;
import tech.laye.tourisme_api.availability.Availability;
import tech.laye.tourisme_api.circuit.Circuit;
import tech.laye.tourisme_api.common.BaseAuditingEntity;
import tech.laye.tourisme_api.event_enrollment.EventEnrollment;
import tech.laye.tourisme_api.order.Order;
import tech.laye.tourisme_api.product.Product;
import tech.laye.tourisme_api.reservation.Reservation;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "users")
public class User {
    @Id
    private String id;
    private String email;
    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "user")
    private List<Accommodation> accommodations;

    @OneToMany(mappedBy = "user")
    private List<Activity> Activity;

    @OneToMany(mappedBy = "user")
    private List<EventEnrollment> eventEnrollments;

    @OneToMany(mappedBy = "user")
    private List<Product> products;

    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToMany(mappedBy = "user")
    private List<Circuit> circuits;

    @OneToMany(mappedBy = "user")
    private List<Availability> availabilities;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private GuideProfile guideProfile;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private PrestaireProfile prestaireProfile;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private ArtisanProfile artisanProfile;
}