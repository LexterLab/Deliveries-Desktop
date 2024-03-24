package com.tuvarna.delivery.user.service;

import com.tuvarna.delivery.delivery.model.Delivery;
import com.tuvarna.delivery.delivery.payload.response.DeliveryResponse;
import com.tuvarna.delivery.delivery.repository.DeliveryRepository;
import com.tuvarna.delivery.delivery.service.helper.DeliveryHelper;
import com.tuvarna.delivery.exception.ResourceNotFoundException;
import com.tuvarna.delivery.user.model.User;
import com.tuvarna.delivery.user.payload.mapper.UserMapper;
import com.tuvarna.delivery.user.payload.request.UserRequestDTO;
import com.tuvarna.delivery.user.payload.response.UserDTO;
import com.tuvarna.delivery.user.payload.response.UserResponseDTO;
import com.tuvarna.delivery.user.repository.UserRepository;
import com.tuvarna.delivery.user.service.helper.UserHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryHelper deliveryHelper;
    private final UserHelper userHelper;


    public DeliveryResponse retrieveUserDeliveries(String username, int pageNo, int pageSize, String sortBy, String sortDir) {
        User user = userRepository.findUserByUsernameIgnoreCase(username)
                .orElseThrow(()-> new ResourceNotFoundException("User", "username", username));


        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Delivery> deliveries = deliveryRepository.findAllByUserUsername(user.getUsername(),pageable);
        return deliveryHelper.getDeliveryResponse(deliveries);
    }

    public UserDTO retrieveUserInfo(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return UserMapper.INSTANCE.entityToDTO(user);
    }

    public UserResponseDTO retrieveAllUsers(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<User> userPage = userRepository.findAll(pageable);

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
