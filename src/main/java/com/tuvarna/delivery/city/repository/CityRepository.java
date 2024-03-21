package com.tuvarna.delivery.city.repository;

import com.tuvarna.delivery.city.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findCityByNameIgnoreCase(String name);
}
