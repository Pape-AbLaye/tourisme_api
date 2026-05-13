package tech.laye.tourisme_api.circuit;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CircuitResponse {

    private Long id;
    private String title;
    private int  duration;
    private int  maxParticipants;
    private String language;
    private Long price;
}
