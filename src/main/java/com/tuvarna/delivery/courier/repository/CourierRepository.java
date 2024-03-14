package com.tuvarna.delivery.courier.repository;

import com.tuvarna.delivery.courier.model.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourierRepository extends JpaRepository<Courier, Long> {
}
