package com.tuvarna.delivery.office.repository;

import com.tuvarna.delivery.office.model.Office;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfficeRepository extends JpaRepository<Office, Long> {
}
