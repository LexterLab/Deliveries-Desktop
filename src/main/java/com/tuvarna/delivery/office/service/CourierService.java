package com.tuvarna.delivery.office.service;

import com.tuvarna.delivery.office.payload.response.CourierResponseDTO;
import com.tuvarna.delivery.office.repository.CourierRepository;
import com.tuvarna.delivery.office.service.helper.CourierHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourierService {
    private final CourierRepository courierRepository;
    private final CourierHelper courierHelper;


    public CourierResponseDTO getAllCouriers(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        return courierHelper.getCourierResponse(courierRepository.findAll(pageable));
    }

}
