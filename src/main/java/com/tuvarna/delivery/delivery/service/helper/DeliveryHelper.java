package com.tuvarna.delivery.delivery.service.helper;

import com.tuvarna.delivery.city.model.City;
import com.tuvarna.delivery.city.repository.CityRepository;
import com.tuvarna.delivery.delivery.model.Delivery;
import com.tuvarna.delivery.delivery.model.Status;
import com.tuvarna.delivery.delivery.model.constant.StatusType;
import com.tuvarna.delivery.delivery.payload.mapper.DeliveryMapper;
import com.tuvarna.delivery.delivery.payload.request.DeliveryRequestDTO;
import com.tuvarna.delivery.delivery.payload.response.DeliveryDTO;
import com.tuvarna.delivery.delivery.payload.response.DeliveryResponse;
import com.tuvarna.delivery.delivery.repository.StatusRepository;
import com.tuvarna.delivery.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DeliveryHelper {
    private final CityRepository cityRepository;
    private final StatusRepository statusRepository;

    public DeliveryResponse getDeliveryResponse(Page<Delivery> deliveryPage) {
        List<DeliveryDTO> deliveries = DeliveryMapper.INSTANCE.entityToDTO(deliveryPage.getContent());
        return new DeliveryResponse(
                deliveryPage.getNumber(),
                deliveryPage.getSize(),
                deliveryPage.getTotalElements(),
                deliveryPage.getTotalPages(),
                deliveryPage.isLast(),
                deliveries
        );
    }

    public void setupDelivery(DeliveryRequestDTO requestDTO, Delivery delivery) {
        City fromCity = cityRepository.findCityByNameIgnoreCase(requestDTO.fromCityName())
                .orElseThrow(() -> new ResourceNotFoundException("City", "name", requestDTO.fromCityName()));

        City toCity = cityRepository.findCityByNameIgnoreCase(requestDTO.toCityName())
                .orElseThrow(() -> new ResourceNotFoundException("City", "name", requestDTO.toCityName()));

        Status status = statusRepository.findByType(StatusType.WAITING.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Status", "type", StatusType.WAITING.getName()));

        delivery.setFromCity(fromCity);
        delivery.setToCity(toCity);
        delivery.setStatus(status);
    }
}
