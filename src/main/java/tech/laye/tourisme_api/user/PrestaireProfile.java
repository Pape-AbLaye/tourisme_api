package tech.laye.tourisme_api.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import tech.laye.tourisme_api.accommodation.Accommodation;
import tech.laye.tourisme_api.activity.Activity;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "prestataire_profiles")
public class PrestaireProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String businessName;
    private String contactInfo;
    private String location;
    private Double rating;
}