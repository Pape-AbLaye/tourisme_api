package tech.laye.tourisme_api.circuit;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import tech.laye.tourisme_api.securityUtils.SecurityUtils;
import tech.laye.tourisme_api.user.Role;
import tech.laye.tourisme_api.user.User;
import tech.laye.tourisme_api.user.UserRepository;

import javax.naming.OperationNotSupportedException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CircuitService {

    private final CircuitRepository circuitRepository;
    private final CircuitMapper circuitMapper;
    private final UserRepository userRepository;

    public Page<CircuitResponse> getAllCircuit(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        return circuitRepository.findAll( pageable)
                .map(circuitMapper::toACircuitResponse);
    }

    public Page<CircuitResponse> getMyCircuit(int page, int size, Authentication connectedUser) {
        String currentUserId = SecurityUtils.getCurrentUserId(connectedUser);

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        return circuitRepository.findAllByUser( pageable ,currentUserId)
                .map(circuitMapper::toACircuitResponse);
    }

    public CircuitResponse getCircuit(Long id) {
        return circuitRepository.findById(id).
                map(circuitMapper::toACircuitResponse).orElseThrow(
                        ()-> new EntityNotFoundException("Entity Not Found !")
                );
    }

    @Transactional
    public Long save( CircuitRequest circuitRequest, Authentication connectedUser) {
        String currentUserId = SecurityUtils.getCurrentUserId(connectedUser);

        User user = userRepository.findById(currentUserId).orElseThrow(
                ()-> new EntityNotFoundException("user not found")
        );

        if(user.getRole() != Role.GUIDE)
        {
            throw new RuntimeException("u don't have the right to do this !");
        }

        Circuit circuit = circuitMapper.toCircuit(circuitRequest);
        circuit.setUser(user);
        return circuitRepository.save(circuit).getId();
    }

    @Transactional
    public Long updateCircuit(Long id,  CircuitRequest circuitRequest, Authentication connectedUser) throws OperationNotSupportedException {
        Circuit circuit = circuitRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("product not found")
        );
        String currentUserId = SecurityUtils.getCurrentUserId(connectedUser);

        if (!Objects.equals(circuit.getUser().getId(),currentUserId))
        {
            throw new OperationNotSupportedException("cannot update another user's product!");
        }

        circuit.setPrice(circuitRequest.price());
        circuit.setDuration(circuitRequest.duration());
        circuit.setLanguage(circuitRequest.language());
        circuit.setMaxParticipants(circuitRequest.maxParticipants());
        circuit.setTitle(circuitRequest.title());
        return circuitRepository.save(circuit).getId();
    }

    public void deleteCircuit(Long id, Authentication connectedUser) throws OperationNotSupportedException {
        Circuit circuit = circuitRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("product not found !")
        );
        String currentUserId = SecurityUtils.getCurrentUserId(connectedUser);

        if (!Objects.equals(circuit.getUser().getId(),currentUserId))
        {
            throw new OperationNotSupportedException("cannot delete another user's product!");
        }

        circuitRepository.delete(circuit);
    }
}
