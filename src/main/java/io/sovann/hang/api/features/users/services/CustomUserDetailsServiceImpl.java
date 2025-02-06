package io.sovann.hang.api.features.users.services;


import io.sovann.hang.api.features.users.entities.User;
import io.sovann.hang.api.features.users.repos.*;
import io.sovann.hang.api.features.users.securities.*;
import java.util.*;
import lombok.*;
import org.springframework.cache.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Cacheable(value = "userDetails", key = "#username")
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username)));
        User found = user.orElse(null);
        return new CustomUserDetails(found);
    }
}

