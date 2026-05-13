package tech.laye.tourisme_api.accommodation;

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
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final AccommodationMapper accommodationMapper;
    private final UserRepository userRepository;

    public Page<AccommodationResponse> getAllAcc(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        return accommodationRepository.findAll( pageable)
                .map(accommodationMapper::toAccommodationResponse);
    }

    public Page<AccommodationResponse> getMyAcc(int page, int size, Authentication connectedUser) {
        String currentUserId = SecurityUtils.getCurrentUserId(connectedUser);

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        return accommodationRepository.findAllByUser(pageable,currentUserId)
                .map(accommodationMapper::toAccommodationResponse);
    }

    public AccommodationResponse getAcc(Long id) {
        return accommodationRepository.findById(id).
                map(accommodationMapper::toAccommodationResponse).orElseThrow(
                        ()-> new EntityNotFoundException("Entity Not Found !")
                );
    }

    @Transactional
    public Long save( AccommodationRequest accommodationRequest, Authentication connectedUser) {
        String currentUserId = SecurityUtils.getCurrentUserId(connectedUser);

        User user = userRepository.findById(currentUserId).orElseThrow(
                ()-> new EntityNotFoundException("User not found with ID: " + currentUserId)
        );

        if(user.getRole() != Role.PRESTATAIRE)
        {
            throw new RuntimeException("u don't have the right to do this !");
        }

        Accommodation accommodation = accommodationMapper.toAccommodation(accommodationRequest);
        accommodation.setUser(user);
        return accommodationRepository.save(accommodation).getId();
    }

    @Transactional
    public Long updateAcc(Long id,  AccommodationRequest accommodationRequest, Authentication connectedUser) throws OperationNotSupportedException {
        Accommodation accommodation = accommodationRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("product not found")
        );
        String currentUserId = SecurityUtils.getCurrentUserId(connectedUser);


        if (!Objects.equals(accommodation.getUser().getId(),currentUserId))
        {
            throw new OperationNotSupportedException("cannot update another user's product!");
        }

        accommodation.setName(accommodationRequest.name());
        accommodation.setCapacity(accommodationRequest.capacity());
        accommodation.setAddress(accommodationRequest.address());
        accommodation.setType(accommodationRequest.type());
        accommodation.setPricePerNight(accommodationRequest.pricePerNight());

        return accommodationRepository.save(accommodation).getId();
    }

    public void deleteAcc(Long id, Authentication connectedUser) throws OperationNotSupportedException {
        Accommodation accommodation = accommodationRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("product not found !")
        );
        String currentUserId = SecurityUtils.getCurrentUserId(connectedUser);

        if (!Objects.equals(accommodation.getUser().getId(),currentUserId))
        {
            throw new OperationNotSupportedException("cannot delete another user's product!");
        }

        accommodationRepository.delete(accommodation);
    }
}
