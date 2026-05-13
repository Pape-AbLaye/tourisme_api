package tech.laye.tourisme_api.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSynchronizer {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public void synchronizeWithIdp(Jwt token) {
        String sub = token.getClaim("sub");
        String email = token.getClaim("email");

        User userFromToken = userMapper.fromTokenAttributes(token.getClaims());

        userRepository.findById(sub).ifPresentOrElse(
                existingUser -> {
                    if (!existingUser.getEmail().equals(email)) {
                        existingUser.setEmail(email);
                        existingUser.setFirstName(userFromToken.getFirstName());
                        existingUser.setLastName(userFromToken.getLastName());
                        userRepository.save(existingUser);
                    }
                },
                () -> {
                    userFromToken.setId(sub);
                    User savedUser = userRepository.save(userFromToken);
                }
        );
    }


    private Optional<String> getUserEmail(Jwt token) {
        Map<String, Object> attributes = token.getClaims();
        if (attributes.containsKey("email")) {
            return Optional.of(attributes.get("email").toString());
        }
        return Optional.empty();

    }
}
