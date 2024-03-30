package com.tuvarna.delivery.user.service;

import com.tuvarna.delivery.delivery.model.Delivery;
import com.tuvarna.delivery.delivery.payload.response.DeliveryResponse;
import com.tuvarna.delivery.delivery.repository.DeliveryRepository;
import com.tuvarna.delivery.delivery.service.helper.DeliveryHelper;
import com.tuvarna.delivery.exception.ResourceNotFoundException;
import com.tuvarna.delivery.user.model.Role;
import com.tuvarna.delivery.user.model.User;
import com.tuvarna.delivery.user.payload.mapper.UserMapper;
import com.tuvarna.delivery.user.payload.request.UserRequestDTO;
import com.tuvarna.delivery.user.payload.response.UserDTO;
import com.tuvarna.delivery.user.payload.response.UserResponseDTO;
import com.tuvarna.delivery.user.repository.RoleRepository;
import com.tuvarna.delivery.user.repository.UserRepository;
import com.tuvarna.delivery.user.service.helper.UserHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final DeliveryRepository deliveryRepository;
    private final RoleRepository roleRepository;
    private final DeliveryHelper deliveryHelper;
    private final UserHelper userHelper;


    public DeliveryResponse retrieveUserDeliveries(String username, LocalDate afterDate, int pageNo, int pageSize, String sortBy, String sortDir) {
        User user = userRepository.findUserByUsernameIgnoreCase(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));


        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        LocalDateTime convertedDate = null;
        if(afterDate != null) {
            convertedDate = LocalDateTime.of(afterDate.getYear(), afterDate.getMonth(),
                    afterDate.getDayOfMonth(), 0, 0, 0, 0);
        }

        Page<Delivery> deliveries = deliveryRepository.findAndFilterUserDeliveries(user.getUsername(),convertedDate, pageable);
        return deliveryHelper.getDeliveryResponse(deliveries);
    }

    public UserDTO retrieveUserInfo(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return UserMapper.INSTANCE.entityToDTO(user);
    }

    public UserDTO retrieveUserInfo(String username) {
        User user = userRepository.findUserByUsernameIgnoreCase(username)
                .orElseThrow(()-> new ResourceNotFoundException("User", "username", username));

        return UserMapper.INSTANCE.entityToDTO(user);
    }

    public UserResponseDTO retrieveAllUsers(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new ResourceNotFoundException("Role", "name", "ROLE_USER"));

        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new ResourceNotFoundException("Role", "name", "ROLE_ADMIN"));

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<User> userPage = userRepository
                .findAllByRolesIsContainingAndRolesIsNotContaining(userRole, adminRole, pageable);

        return userHelper.getUserResponse(userPage);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userRepository.delete(user);
    }

    public UserDTO updateUserInfo(Long id, UserRequestDTO requestDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        UserMapper.INSTANCE.updateUserWithDTO(requestDTO, user);

        return UserMapper.INSTANCE.entityToDTO(userRepository.save(user));
    }
}
