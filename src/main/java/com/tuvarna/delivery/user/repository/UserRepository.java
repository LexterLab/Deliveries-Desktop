package com.tuvarna.delivery.user.repository;

import com.tuvarna.delivery.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
