package com.tuvarna.delivery.bootstrap;

import com.tuvarna.delivery.city.repository.CityRepository;
import com.tuvarna.delivery.delivery.repository.StatusRepository;
import com.tuvarna.delivery.office.repository.CourierRepository;
import com.tuvarna.delivery.office.repository.OfficeRepository;
import com.tuvarna.delivery.user.repository.RoleRepository;
import com.tuvarna.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataCommandLineRunner implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final StatusRepository statusRepository;
    private final CourierRepository courierRepository;
    private final OfficeRepository officeRepository;

    @Override
    public void run(String... args) {
        if(roleRepository.count() == 0 && userRepository.count() == 0 && cityRepository.count() == 0 &&
        statusRepository.count() == 0 && courierRepository.count() == 0 && officeRepository.count() == 0) {
            BootstrapHelper.setUp(roleRepository, userRepository, cityRepository, statusRepository, courierRepository,
                    officeRepository);
        }
    }
}
