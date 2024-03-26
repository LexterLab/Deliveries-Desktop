package com.tuvarna.delivery.delivery.repository;

import com.tuvarna.delivery.delivery.model.Delivery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface DeliveryRepository extends JpaRepository<Delivery, Long> {


    Page<Delivery> findAllByUserUsername(String username, Pageable pageable);

    @Query("SELECT d FROM Delivery d " +
            "WHERE (:username IS NULL OR d.user.username = :username) " +
            "AND (:statusId IS NULL OR d.status.id = :statusId) ")
    Page<Delivery> findAndFilterDeliveries(String username, Long statusId, Pageable pageable);
}
