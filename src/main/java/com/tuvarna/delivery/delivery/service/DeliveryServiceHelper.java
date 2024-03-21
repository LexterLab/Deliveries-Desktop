package com.tuvarna.delivery.delivery.service;

import com.tuvarna.delivery.delivery.model.Delivery;
import com.tuvarna.delivery.delivery.payload.mapper.DeliveryMapper;
import com.tuvarna.delivery.delivery.payload.response.DeliveryDTO;
import com.tuvarna.delivery.delivery.payload.response.DeliveryResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeliveryServiceHelper {

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
}
