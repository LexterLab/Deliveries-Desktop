package com.tuvarna.delivery.office.service;

import com.tuvarna.delivery.exception.ResourceNotFoundException;
import com.tuvarna.delivery.office.model.Courier;
import com.tuvarna.delivery.office.model.Office;
import com.tuvarna.delivery.office.payload.mapper.CourierMapper;
import com.tuvarna.delivery.office.payload.request.CourierRequestDTO;
import com.tuvarna.delivery.office.repository.CourierRepository;
import com.tuvarna.delivery.office.repository.OfficeRepository;
import com.tuvarna.delivery.user.model.Role;
import com.tuvarna.delivery.user.model.User;
import com.tuvarna.delivery.user.repository.RoleRepository;
import com.tuvarna.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OfficeService {
    private final CourierRepository courierRepository;
    private final OfficeRepository officeRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public CourierRequestDTO enlistCourierToOffice(Long officeId, CourierRequestDTO requestDTO) {
        Office office = officeRepository.findById(officeId)
                .orElseThrow(() -> new ResourceNotFoundException("Office", "Id", officeId));

        User user = userRepository.findUserByUsernameIgnoreCase(requestDTO.username())
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", requestDTO.username()));

        Role courierRole = roleRepository.findByName("ROLE_COURIER")
                .orElseThrow(() -> new ResourceNotFoundException("Role", "name", "COURIER"));


        user.getRoles().add(courierRole);

        userRepository.save(user);

        Courier courier = CourierMapper.INSTANCE.dtoToEntity(requestDTO);
        courier.setOffice(office);
        courier.setUser(user);
        courierRepository.save(courier);

        return requestDTO;
    }



}
