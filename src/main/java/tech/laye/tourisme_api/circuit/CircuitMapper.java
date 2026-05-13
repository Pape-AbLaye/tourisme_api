package tech.laye.tourisme_api.circuit;

import org.springframework.stereotype.Service;

@Service
public class CircuitMapper {

    public CircuitResponse toACircuitResponse(Circuit circuit)
    {
        return CircuitResponse.builder()
                .id(circuit.getId())
                .title(circuit.getTitle())
                .language(circuit.getLanguage())
                .duration(circuit.getDuration())
                .maxParticipants(circuit.getMaxParticipants())
                .price(circuit.getPrice())
                .build();
    }

    public Circuit toCircuit(CircuitRequest circuitRequest) {
        return Circuit.builder()
                .title(circuitRequest.title())
                .duration(circuitRequest.duration())
                .maxParticipants(circuitRequest.maxParticipants())
                .price(circuitRequest.price())
                .language(circuitRequest.language())
                .build();
    }
}
