package com.tuvarna.delivery.delivery.repository;

import com.tuvarna.delivery.delivery.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
