package com.tuvarna.delivery.bootstrap;

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

    @Override
    public void run(String... args) {
        if(roleRepository.count() == 0 && userRepository.count() == 0) {
            BootstrapHelper.setUp(roleRepository, userRepository);
        }
    }
}
