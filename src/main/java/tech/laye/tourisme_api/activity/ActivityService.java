package tech.laye.tourisme_api.activity;

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
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;
    private final UserRepository userRepository;

    public Page<ActivityResponse> getAllActivity(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        return activityRepository.findAllWithAvailabilities(pageable)
                .map(activityMapper::toActivityResponse);
    }

    public Page<ActivityResponse> getMyActivity(int page, int size, Authentication connectedUser) {
        String currentUserId = SecurityUtils.getCurrentUserId(connectedUser);

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        return activityRepository.findAllByUser(pageable,currentUserId)
                .map(activityMapper::toActivityResponse);
    }

    public ActivityResponse getActivity(Long id) {
        return activityRepository.findById(id).
                map(activityMapper::toActivityResponse).orElseThrow(
                        ()-> new EntityNotFoundException("Entity Not Found !")
                );
    }

    public Page<ActivityResponse> getActivityByType(int page, int size, Activity_type type) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        return activityRepository.findByActivityType(type, pageable)
                .map(activityMapper::toActivityResponse);
    }
    @Transactional
    public Long save( ActivityRequest activityRequest, Authentication connectedUser) {

        String currentUserId = SecurityUtils.getCurrentUserId(connectedUser);

        User user = userRepository.findById(currentUserId).orElseThrow(
                ()-> new EntityNotFoundException("user not found")
        );

        if(user.getRole() != Role.PRESTATAIRE)
        {
            throw new RuntimeException("u don't have the right to do this !");
        }

        Activity activity = activityMapper.toActivity(activityRequest);
        activity.setUser(user);
        return activityRepository.save(activity).getId();
    }

    @Transactional
    public Long updateActivity(Long id,  ActivityRequest activityRequest, Authentication connectedUser) throws OperationNotSupportedException {
        Activity activity = activityRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("product not found")
        );
        String currentUserId = SecurityUtils.getCurrentUserId(connectedUser);

        if (!Objects.equals(activity.getUser().getId(),currentUserId))
        {
            throw new OperationNotSupportedException("cannot update another user's product!");
        }

        activity.setActivityType(activityRequest.activityType());
        activity.setCapacity(activityRequest.capacity());
        activity.setDescription(activityRequest.description());
        activity.setName(activityRequest.name());
        activity.setLocation(activityRequest.location());
        activity.setMinimumAge(activityRequest.minimumAge());
        activity.setPrice(activityRequest.price());
        return activityRepository.save(activity).getId();
    }

    public void deleteActivity(Long id, Authentication connectedUser) throws OperationNotSupportedException {
        Activity activity = activityRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("product not found !")
        );
        String currentUserId = SecurityUtils.getCurrentUserId(connectedUser);

        if (!Objects.equals(activity.getUser().getId(),currentUserId))
        {
            throw new OperationNotSupportedException("cannot delete another user's product!");
        }

        activityRepository.delete(activity);
    }
}
