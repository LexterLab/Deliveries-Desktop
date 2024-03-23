package com.tuvarna.delivery.bootstrap;

import com.tuvarna.delivery.city.model.City;
import com.tuvarna.delivery.city.repository.CityRepository;
import com.tuvarna.delivery.delivery.model.Status;
import com.tuvarna.delivery.delivery.model.constant.StatusType;
import com.tuvarna.delivery.delivery.repository.StatusRepository;
import com.tuvarna.delivery.office.model.Courier;
import com.tuvarna.delivery.office.model.Office;
import com.tuvarna.delivery.office.repository.CourierRepository;
import com.tuvarna.delivery.office.repository.OfficeRepository;
import com.tuvarna.delivery.user.model.Role;
import com.tuvarna.delivery.user.model.User;
import com.tuvarna.delivery.user.repository.RoleRepository;
import com.tuvarna.delivery.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Set;

public class BootstrapHelper {
    public static void setUp(RoleRepository roleRepository, UserRepository userRepository, CityRepository cityRepository,
                             StatusRepository statusRepository, CourierRepository courierRepository,
                             OfficeRepository officeRepository) {
        Role adminRole = new Role();
        Role userRole = new Role();
        Role courierRole = new Role();
        userRole.setName("ROLE_USER");
        adminRole.setName("ROLE_ADMIN");
        courierRole.setName("Courier");
        roleRepository.saveAll(List.of(userRole, adminRole, courierRole));

        User admin = new User();
        admin.setFirstName("admin");
        admin.setLastName("admin");
        admin.setUsername("admin");
        admin.setAddress("address");
        admin.setPhoneNumber("+359********");
        admin.setPassword(new BCryptPasswordEncoder().encode("!Admin123"));
        admin.setRoles(Set.of(adminRole, userRole, courierRole));

        User user = new User();
        user.setFirstName("user");
        user.setLastName("user");
        user.setUsername("user");
        user.setAddress("address");
        user.setPhoneNumber("+3598*******");
        user.setPassword(new BCryptPasswordEncoder().encode("!user123"));
        user.setRoles(Set.of(userRole));

        User courierUser = new User();
        user.setFirstName("George");
        user.setLastName("Russel");
        user.setUsername("calendar");
        user.setAddress("address");
        user.setPhoneNumber("+35986******");
        user.setPassword(new BCryptPasswordEncoder().encode("!user123"));
        user.setRoles(Set.of(courierRole));

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

        Office office = new Office();
        office.setCity(varna);
        office.setPhoneNumber("+3598*******");
        office.setName("Office 1");


        Office office2 = new Office();
        office.setCity(sofia);
        office.setPhoneNumber("+359********");
        office.setName("Office 2");

        officeRepository.saveAll(List.of(office, office2));

        Courier courier = new Courier();
        courier.setUser(courierUser);
        courier.setOffice(office);
        courier.setYearsOfExperience(2);
        courier.setWorkPhoneNumber("+359********");

        courierRepository.saveAll(List.of(courier));
    }
}
