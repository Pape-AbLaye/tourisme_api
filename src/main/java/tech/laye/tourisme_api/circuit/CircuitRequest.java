package tech.laye.tourisme_api.circuit;

import jakarta.validation.constraints.NotEmpty;

public record CircuitRequest(
        @NotEmpty(message = "the title cannot be empty")
        String title,
        int  duration,
        @NotEmpty(message = "maxParticipants is not given")
        int  maxParticipants,
        @NotEmpty(message = "language that will be using ?")
        String language,
        @NotEmpty(message = "the price cannot be empty")
        Long price

) {
}