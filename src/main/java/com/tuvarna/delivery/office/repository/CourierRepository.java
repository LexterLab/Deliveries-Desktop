package com.tuvarna.delivery.office.repository;

import com.tuvarna.delivery.office.model.Courier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourierRepository extends JpaRepository<Courier, Long> {
    Optional<Courier> findByIdAndOfficeId(Long courierId, Long officeId);
    Page<Courier> findAllByOfficeId(Long officeId, Pageable pageable);
}
