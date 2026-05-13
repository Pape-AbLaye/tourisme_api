package tech.laye.tourisme_api.user;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserMapper {

    public User fromTokenAttributes(Map<String, Object> attributes) {
        User user = new User();

        if (attributes.containsKey("sub")) {
            user.setId(attributes.get("sub").toString());
        }

        if (attributes.containsKey("given_name")) {
            user.setFirstName(attributes.get("given_name").toString());
        } else if (attributes.containsKey("nickname")) {
            user.setFirstName(attributes.get("nickname").toString());
        }

        if (attributes.containsKey("family_name")) {
            user.setLastName(attributes.get("family_name").toString());
        }

        if (attributes.containsKey("email")) {
            user.setEmail(attributes.get("email").toString());
        }

        user.setRole(extractRole(attributes));

        return user;
    }

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

    private Role extractRole(Map<String, Object> attributes) {
        try {
            Map<String, Object> realmAccess =
                    (Map<String, Object>) attributes.get("realm_access");
            List<String> roles = (List<String>) realmAccess.get("roles");

            if (roles.contains("GUIDE"))       return Role.GUIDE;
            if (roles.contains("ARTISAN"))     return Role.ARTISAN;
            if (roles.contains("PRESTATAIRE")) return Role.PRESTATAIRE;
            if (roles.contains("ADMIN"))       return Role.ADMIN;

        } catch (Exception e) {
            // si realm_access absent → TOURISTE par défaut
        }
        return Role.TOURISTE;
    }

}
