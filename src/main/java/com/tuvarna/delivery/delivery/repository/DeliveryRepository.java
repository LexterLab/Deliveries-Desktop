package com.tuvarna.delivery.delivery.repository;

import com.tuvarna.delivery.delivery.model.Delivery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;


public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    @Query("SELECT d FROM Delivery d " +
            "WHERE (:username IS NULL OR d.user.username = :username) " +
            "AND (:statusId IS NULL OR d.status.id = :statusId) ")
    Page<Delivery> findAndFilterDeliveries(String username, Long statusId, Pageable pageable);

    @Query("SELECT d FROM Delivery d " +
            "WHERE d.user.username = :username " +
            "AND (cast(:afterDate as timestamp) IS NULL OR d.orderedAt >= :afterDate) " +
            "AND (cast(:fiveDays as timestamp) IS NULL OR d.orderedAt BETWEEN :fiveDays AND CURRENT_TIMESTAMP)")

    Page<Delivery> findAndFilterUserDeliveries(String username, LocalDateTime afterDate, LocalDateTime fiveDays,  Pageable pageable);
}
