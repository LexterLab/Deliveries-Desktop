package com.tuvarna.delivery.office.service.helper;

import com.tuvarna.delivery.office.model.Courier;
import com.tuvarna.delivery.office.payload.mapper.CourierMapper;
import com.tuvarna.delivery.office.payload.response.CourierDTO;
import com.tuvarna.delivery.office.payload.response.CourierResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourierHelper {
    public CourierResponseDTO getCourierResponse(Page<Courier> courierPage) {
        List<CourierDTO> couriers = CourierMapper.INSTANCE.entityToDTO(courierPage.getContent());
        return new CourierResponseDTO(
                courierPage.getNumber(),
                courierPage.getSize(),
                courierPage.getTotalElements(),
                courierPage.getTotalPages(),
                courierPage.isLast(),
                couriers
        );
    }
}
