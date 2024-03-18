package com.tuvarna.delivery.bootstrap;

import com.tuvarna.delivery.user.model.Role;
import com.tuvarna.delivery.user.model.User;
import com.tuvarna.delivery.user.repository.RoleRepository;
import com.tuvarna.delivery.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Set;

public class BootstrapHelper {
    public static void setUp(RoleRepository roleRepository, UserRepository userRepository) {
        Role adminRole = new Role();
        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        adminRole.setName("ROLE_ADMIN");
        roleRepository.saveAll(List.of(userRole, adminRole));

        User admin = new User();
        admin.setFirstName("admin");
        admin.setLastName("admin");
        admin.setUsername("admin");
        admin.setAddress("address");
        admin.setPhoneNumber("+359********");
        admin.setPassword(new BCryptPasswordEncoder().encode("!Admin123"));
        admin.setRoles(Set.of(adminRole, userRole));

        User user = new User();
        user.setFirstName("user");
        user.setLastName("user");
        user.setUsername("user");
        user.setAddress("address");
        user.setPhoneNumber("+3598*******");
        user.setPassword(new BCryptPasswordEncoder().encode("!user123"));
        user.setRoles(Set.of(userRole));


        userRepository.saveAll(List.of(admin, user));
    }
}
