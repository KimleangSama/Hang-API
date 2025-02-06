package io.sovann.hang.api.features.users.services;


import io.sovann.hang.api.features.users.entities.User;
import io.sovann.hang.api.features.users.repos.*;
import io.sovann.hang.api.utils.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.cache.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl {
    private final UserRepository userRepository;

    @Cacheable(value = "users", key = "#username")
    public User findByUsername(String username) {
        User found = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found."));
        SoftEntityDeletable.throwErrorIfSoftDeleted(found);
        return found;
    }

    @Cacheable(value = "users", key = "#email")
    public User findByEmail(String email) {
        User found = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + " not found."));
        SoftEntityDeletable.throwErrorIfSoftDeleted(found);
        return found;
    }
}