package tech.laye.tourisme_api.reservation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import tech.laye.tourisme_api.accommodation.Accommodation;
import tech.laye.tourisme_api.activity.Activity;
import tech.laye.tourisme_api.availability.Availability;
import tech.laye.tourisme_api.availability.AvailabilityRepository;
import tech.laye.tourisme_api.circuit.Circuit;
import tech.laye.tourisme_api.securityUtils.SecurityUtils;
import tech.laye.tourisme_api.user.User;
import tech.laye.tourisme_api.user.UserRepository;

import java.nio.file.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final UserRepository userRepository;
    private final AvailabilityRepository availabilityRepository;


    public Page<ReservationResponse> getAllreservation(int page , int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        return reservationRepository.findAll(pageable)
                .map(reservationMapper::toReservationResponse);

    }

    @Transactional
    public Long save(ReservationRequest request, Authentication connectedUser) {
        String currentUserId = SecurityUtils.getCurrentUserId(connectedUser);

        Availability availability = availabilityRepository.findById(request.availabilityId())
                .orElseThrow(() -> new EntityNotFoundException("Availability slot not found"));

        if (!availability.isAvailable()) {
            throw new IllegalStateException("This slot is no longer available.");
        }
        if (availability.getBookedSlots() + request.quantity() > availability.getTotalSlots()) {
            throw new IllegalStateException("Not enough space remaining!");
        }

        availability.setBookedSlots(availability.getBookedSlots() + request.quantity());

        if (availability.getBookedSlots() >= availability.getTotalSlots()) {
            availability.setAvailable(false);
        }

        Reservation reservation = reservationMapper.toReservation(request);
        reservation.setUser(User.builder().id(currentUserId).build());

        availabilityRepository.save(availability);
        return reservationRepository.save(reservation).getId();
    }

    @Transactional
    public Long update(Long id, ReservationRequest request, Authentication connectedUser) throws AccessDeniedException {

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found"));

        String currentUserId = SecurityUtils.getCurrentUserId(connectedUser);
        if (!reservation.getUser().getId().equals(currentUserId)) {
            throw new AccessDeniedException("You do not have permission to update this reservation");
        }

        reservation.setStatus(request.status());
        reservation.setTargetType(request.targetType());

        switch (request.targetType()) {
            case ACCOMMODATION -> {
                if (request.accommodation_id() == null)
                    throw new IllegalArgumentException("accommodation_id is required");
                reservation.setAccommodation(
                        Accommodation.builder().id(request.accommodation_id()).build()
                );
                reservation.setActivity(null);
                reservation.setCircuit(null);
            }
            case ACTIVITY -> {
                if (request.activity_id() == null)
                    throw new IllegalArgumentException("activity_id is required");
                reservation.setActivity(
                        Activity.builder().id(request.activity_id()).build()
                );
                reservation.setAccommodation(null);
                reservation.setCircuit(null);
            }
            case CIRCUIT -> {
                if (request.circuit_id() == null)
                    throw new IllegalArgumentException("circuit_id is required");
                reservation.setCircuit(
                        Circuit.builder().id(request.circuit_id()).build()
                );
                reservation.setAccommodation(null);
                reservation.setActivity(null);
            }
        }

        return reservationRepository.save(reservation).getId();
    }

    public void delete(Long id, Authentication connectedUser) {

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found"));

        String currentUserId = SecurityUtils.getCurrentUserId(connectedUser);

        if (!reservation.getUser().getId().equals(currentUserId)) {
            throw new RuntimeException("You can only delete your reservation !");
        }

        reservationRepository.delete(reservation);
    }

    public ReservationResponse getById(Long id) {
        return reservationRepository.findById(id).
                map(reservationMapper::toReservationResponse).orElseThrow(
                        ()-> new EntityNotFoundException("Entity Not Found !")
                );
    }
}

