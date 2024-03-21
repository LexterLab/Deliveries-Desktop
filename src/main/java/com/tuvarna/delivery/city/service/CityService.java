package com.tuvarna.delivery.city.service;

import com.tuvarna.delivery.city.payload.CityDTO;
import com.tuvarna.delivery.city.payload.mapper.CityMapper;
import com.tuvarna.delivery.city.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;

    public List<CityDTO> getAllCities() {
        return CityMapper.INSTANCE.entityToDto(cityRepository.findAll());
    }
}
