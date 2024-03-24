package com.tuvarna.delivery.office.service;

import com.tuvarna.delivery.exception.ResourceNotFoundException;
import com.tuvarna.delivery.office.model.Courier;
import com.tuvarna.delivery.office.model.Office;
import com.tuvarna.delivery.office.payload.mapper.CourierMapper;
import com.tuvarna.delivery.office.payload.request.CourierRequestDTO;
import com.tuvarna.delivery.office.payload.request.UpdateCourierRequestDTO;
import com.tuvarna.delivery.office.payload.response.CourierDTO;
import com.tuvarna.delivery.office.payload.response.CourierResponseDTO;
import com.tuvarna.delivery.office.repository.CourierRepository;
import com.tuvarna.delivery.office.repository.OfficeRepository;
import com.tuvarna.delivery.office.service.helper.CourierHelper;
import com.tuvarna.delivery.user.model.User;
import com.tuvarna.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OfficeService {
    private final CourierRepository courierRepository;
    private final OfficeRepository officeRepository;
    private final UserRepository userRepository;
    private final CourierHelper courierHelper;

    @Transactional
    public CourierRequestDTO enlistCourierToOffice(Long officeId, CourierRequestDTO requestDTO) {
        Office office = officeRepository.findById(officeId)
                .orElseThrow(() -> new ResourceNotFoundException("Office", "Id", officeId));

        User user = userRepository.findUserByUsernameIgnoreCase(requestDTO.username())
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", requestDTO.username()));

        Courier courier = CourierMapper.INSTANCE.dtoToEntity(requestDTO);
        courier.setOffice(office);
        courier.setUser(user);
        courierRepository.save(courier);

        return requestDTO;
    }

    @Transactional
    public void deleteCourier(Long officeId, Long courierId) {
        Courier courier = courierRepository.findByIdAndOfficeId(courierId, officeId)
                .orElseThrow(() -> new ResourceNotFoundException("Courier", "officeId, courierId", courierId + " "
                        + officeId));

        courierRepository.delete(courier);
    }

    @Transactional
    public UpdateCourierRequestDTO updateCourier(Long officeId, Long courierId, UpdateCourierRequestDTO requestDTO) {
        Courier courier = courierRepository.findByIdAndOfficeId(courierId, officeId)
                .orElseThrow(() -> new ResourceNotFoundException("Courier", "officeId, courierId", courierId + " "
                        + officeId));
        Office office = officeRepository.findById(requestDTO.officeId())
                .orElseThrow(() -> new ResourceNotFoundException("Office", "id", requestDTO.officeId()));

        CourierMapper.INSTANCE.updateEntityWithDTO(requestDTO, courier);
        courier.setOffice(office);

        return CourierMapper.INSTANCE.entityToDTO(courierRepository.save(courier));
    }


    public CourierDTO retrieveCourierInfo(Long officeId, Long courierId) {
        Courier courier = courierRepository.findByIdAndOfficeId(courierId, officeId)
                .orElseThrow(() -> new ResourceNotFoundException("Courier", "officeId, courierId", courierId + " "
                        + officeId));

        return CourierMapper.INSTANCE.entityToResponse(courier);
    }

    public CourierResponseDTO getAllCouriersFromOffice(Long officeId, int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        return courierHelper.getCourierResponse(courierRepository.findAllByOfficeId(officeId, pageable));
    }

}
