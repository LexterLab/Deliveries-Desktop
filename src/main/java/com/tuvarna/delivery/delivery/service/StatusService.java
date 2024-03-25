package com.tuvarna.delivery.delivery.service;

import com.tuvarna.delivery.delivery.payload.mapper.StatusMapper;
import com.tuvarna.delivery.delivery.payload.response.StatusDTO;
import com.tuvarna.delivery.delivery.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusService {
    private final StatusRepository statusRepository;

    public List<StatusDTO> retrieveStatusList() {
        return StatusMapper.INSTANCE.entityToDTO(statusRepository.findAll());
    }

}
