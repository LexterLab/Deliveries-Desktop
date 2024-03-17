package com.tuvarna.delivery.delivery.repository;

import com.tuvarna.delivery.delivery.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
}
