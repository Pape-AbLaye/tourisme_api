package tech.laye.tourisme_api.availability;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import tech.laye.tourisme_api.accommodation.Accommodation;
import tech.laye.tourisme_api.accommodation.AccommodationRepository;
import tech.laye.tourisme_api.activity.Activity;
import tech.laye.tourisme_api.activity.ActivityRepository;
import tech.laye.tourisme_api.circuit.Circuit;
import tech.laye.tourisme_api.circuit.CircuitRepository;
import tech.laye.tourisme_api.securityUtils.SecurityUtils;
import tech.laye.tourisme_api.user.User;
import tech.laye.tourisme_api.user.UserRepository;

import javax.naming.OperationNotSupportedException;
import java.nio.file.AccessDeniedException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AvailabilityService {

    private final AvailabilityRepository availabilityRepository;
    private final AvailabilityMapper availabilityMapper;
    private final ActivityRepository activityRepository;
    private final AccommodationRepository accommodationRepository;
    private final CircuitRepository circuitRepository;
    private final UserRepository userRepository;

    public Page<AvailabilityResponse> getMyAvailabilities(int page, int size, Authentication connectedUser) {
        String currentUserId = SecurityUtils.getCurrentUserId(connectedUser);

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        return availabilityRepository.findAllByUser(pageable,currentUserId)
                .map(availabilityMapper::toAvailabilityResponse);
    }

    public AvailabilityResponse getAvailability(Long id) {
        return availabilityRepository.findById(id).
                map(availabilityMapper::toAvailabilityResponse).orElseThrow(
                        ()-> new EntityNotFoundException("Entity Not Found !")
                );
    }

    @Transactional
    public Long save(AvailabilityRequest availabilityRequest, Authentication connectedUser) throws OperationNotSupportedException {

        String connectedUserId = SecurityUtils.getCurrentUserId(connectedUser);
        User user = userRepository.findById(connectedUserId).orElseThrow(
                () -> new EntityNotFoundException("User not found")
        );

        Availability availability = availabilityMapper.toAvailability(availabilityRequest);

        if (availabilityRequest.activityId() != null){
            Activity activity = activityRepository.findById(availabilityRequest.activityId())
                    .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

            if (!activity.getUser().getId().equals(connectedUserId)) throw new OperationNotSupportedException("It's not yours");

            availability.setActivity(activity);

            int finalSlots = (availabilityRequest.totalSlots() != null && availabilityRequest.totalSlots() > 0)
                    ? availabilityRequest.totalSlots()
                    : activity.getCapacity();

            availability.setTotalSlots(finalSlots);
        }

        else if (availabilityRequest.accommodationId() != null) {
            Accommodation accommodation = accommodationRepository.findById(availabilityRequest.accommodationId())
                    .orElseThrow(() -> new EntityNotFoundException("Accommodation not found"));

            if (!accommodation.getUser().getId().equals(connectedUserId)) {
                throw new OperationNotSupportedException("Not your accommodation");
            }

            availability.setAccommodation(accommodation);

            int finalInventory = (availabilityRequest.totalSlots() != null && availabilityRequest.totalSlots() > 0)
                    ? availabilityRequest.totalSlots()
                    : 1;

            availability.setTotalSlots(finalInventory);
        }

        else if (availabilityRequest.circuitId() != null){
            Circuit circuit = circuitRepository.findById(availabilityRequest.circuitId())
                    .orElseThrow(() -> new EntityNotFoundException("This circuit is not found"));

            if (!circuit.getUser().getId().equals(connectedUserId)) throw new OperationNotSupportedException("It's not yours");

            availability.setCircuit(circuit);

            int finalSlots = (availabilityRequest.totalSlots() != null && availabilityRequest.totalSlots() > 0)
                    ? availabilityRequest.totalSlots()
                    : circuit.getMaxParticipants();

            availability.setTotalSlots(finalSlots);
        }

        availability.setUser(user);

        return availabilityRepository.save(availability).getId();
    }

    public void deleteAvailability(Long id, Authentication connectedUser) throws OperationNotSupportedException {
        Availability availability = availabilityRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("entity not found !")
        );
        String currentUserId = SecurityUtils.getCurrentUserId(connectedUser);

        if (!Objects.equals(availability.getUser().getId(),currentUserId))
        {
            throw new OperationNotSupportedException("cannot delete another user's availability!");
        }

        availabilityRepository.delete(availability);
    }

    @Transactional
    public Long updateAvailability(Long id, AvailabilityRequest availabilityRequest, Authentication connectedUser) throws AccessDeniedException {
        Availability availability = availabilityRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("entity not found !")
        );

        String currentUserId = SecurityUtils.getCurrentUserId(connectedUser);
        if (!availability.getUser().getId().equals(currentUserId)) {
            throw new AccessDeniedException("You do not have permission to update this availability");
        }

        if (availabilityRequest.totalSlots() < availability.getBookedSlots()) {
            throw new IllegalStateException("New capacity cannot be lower than current bookings!");
        }

        availability.setTotalSlots(availabilityRequest.totalSlots());
        availability.setStartDate(availabilityRequest.startDate());
        availability.setEndDate(availabilityRequest.endDate());
        return availabilityRepository.save(availability).getId();
    }

    public Long updateIsAvailable(Long id,  Boolean state, Authentication connectedUser) throws AccessDeniedException {
        Availability availability = availabilityRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("entity not found !")
        );

        String currentUserId = SecurityUtils.getCurrentUserId(connectedUser);
        if (!availability.getUser().getId().equals(currentUserId)) {
            throw new AccessDeniedException("You do not have permission to update this this fiald");
        }

        availability.setAvailable(state);
        return availabilityRepository.save(availability).getId();
    }
}
