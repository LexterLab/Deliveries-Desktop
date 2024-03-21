package com.tuvarna.delivery.delivery.service;

import com.tuvarna.delivery.city.model.City;
import com.tuvarna.delivery.city.repository.CityRepository;
import com.tuvarna.delivery.delivery.model.Delivery;
import com.tuvarna.delivery.delivery.model.Status;
import com.tuvarna.delivery.delivery.model.constant.StatusType;
import com.tuvarna.delivery.delivery.payload.request.DeliveryRequestDTO;
import com.tuvarna.delivery.delivery.payload.mapper.DeliveryMapper;
import com.tuvarna.delivery.delivery.repository.DeliveryRepository;
import com.tuvarna.delivery.delivery.repository.StatusRepository;
import com.tuvarna.delivery.exception.ResourceNotFoundException;
import com.tuvarna.delivery.user.model.User;
import com.tuvarna.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final StatusRepository statusRepository;


    public DeliveryRequestDTO requestDelivery(DeliveryRequestDTO requestDTO, String username) {
        User user = userRepository.findUserByUsernameIgnoreCase(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Username", username));
        Delivery delivery  = DeliveryMapper.INSTANCE.dtoToEntity(requestDTO);

        City fromCity = cityRepository.findCityByNameIgnoreCase(requestDTO.fromCityName())
                .orElseThrow(() -> new ResourceNotFoundException("City", "name", requestDTO.fromCityName()));

        City toCity = cityRepository.findCityByNameIgnoreCase(requestDTO.toCityName())
                .orElseThrow(() -> new ResourceNotFoundException("City", "name", requestDTO.toCityName()));

        Status status = statusRepository.findByType(StatusType.WAITING.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Status", "type", StatusType.WAITING.getName()));

        delivery.setUser(user);
        delivery.setFromCity(fromCity);
        delivery.setToCity(toCity);
        delivery.setStatus(status);

        deliveryRepository.save(delivery);
        return requestDTO;
    }



}
