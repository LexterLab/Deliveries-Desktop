package com.tuvarna.delivery.authentication.service.helper;

import com.tuvarna.delivery.authentication.payload.request.SignUpRequestDTO;
import com.tuvarna.delivery.user.model.Role;
import com.tuvarna.delivery.user.model.User;
import com.tuvarna.delivery.user.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AuthenticationHelper {
    private final RoleRepository repository;
    private final PasswordEncoder encoder;

    private User setRoles(User user) {
        Optional<Role> userRole = repository.findByName("ROLE_USER");
        Role role = new Role();
        if (userRole.isPresent()) {
            role = userRole.get();
        }
        user.setRoles(Set.of(role));
        return user;
    }

    public User buildUser(SignUpRequestDTO requestDTO) {
        User user = new User();
        user.setFirstName(requestDTO.firstName());
        user.setLastName(requestDTO.lastName());
        user.setPassword(encoder.encode(requestDTO.password()));
        user.setUsername(requestDTO.username());
        user.setPhoneNumber(requestDTO.phoneNumber());
        user.setAddress(requestDTO.address());
        return setRoles(user);
    }
}
