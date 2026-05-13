package tech.laye.tourisme_api.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "guide_profiles")
public class GuideProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private String certificationStatus;
    private String specialities;
    private Double rating;

    @ElementCollection
    private List<String> languages;
}
