package com.tuvarna.delivery.user.repository;

import com.tuvarna.delivery.user.model.Role;
import com.tuvarna.delivery.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsernameIgnoreCase(String username);
    Boolean existsByUsernameIgnoreCase(String username);
    Page<User> findAllByRolesIsContainingAndRolesIsNotContaining(Role userRole, Role adminRole, Pageable pageable);
}

