package com.tuvarna.delivery.office.repository;

import com.tuvarna.delivery.office.model.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourierRepository extends JpaRepository<Courier, Long> {
}
