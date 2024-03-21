package com.tuvarna.delivery.bootstrap;

import com.tuvarna.delivery.city.model.City;
import com.tuvarna.delivery.city.repository.CityRepository;
import com.tuvarna.delivery.delivery.model.Status;
import com.tuvarna.delivery.delivery.model.constant.StatusType;
import com.tuvarna.delivery.delivery.repository.StatusRepository;
import com.tuvarna.delivery.user.model.Role;
import com.tuvarna.delivery.user.model.User;
import com.tuvarna.delivery.user.repository.RoleRepository;
import com.tuvarna.delivery.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Set;

public class BootstrapHelper {
    public static void setUp(RoleRepository roleRepository, UserRepository userRepository, CityRepository cityRepository,
                             StatusRepository statusRepository) {
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

        City sofia = new City();
        sofia.setName("Sofia");
        City varna = new City();
        varna.setName("Varna");
        City plovdiv = new City();
        plovdiv.setName("Plovdiv");

        cityRepository.saveAll(List.of(sofia, varna, plovdiv));

        Status waiting = new Status();
        waiting.setType(StatusType.WAITING);

        statusRepository.save(waiting);
    }
}
