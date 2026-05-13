package tech.laye.tourisme_api.event_enrollment;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import tech.laye.tourisme_api.event.Event;
import tech.laye.tourisme_api.user.User;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class EventEnrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_Id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
