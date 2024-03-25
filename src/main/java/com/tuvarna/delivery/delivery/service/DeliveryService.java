package com.tuvarna.delivery.delivery.service;

import com.tuvarna.delivery.city.model.City;
import com.tuvarna.delivery.city.repository.CityRepository;
import com.tuvarna.delivery.delivery.model.Delivery;
import com.tuvarna.delivery.delivery.model.Status;
import com.tuvarna.delivery.delivery.model.constant.StatusType;
import com.tuvarna.delivery.delivery.payload.request.DeliveryRequestDTO;
import com.tuvarna.delivery.delivery.payload.mapper.DeliveryMapper;
import com.tuvarna.delivery.delivery.payload.response.DeliveryDTO;
import com.tuvarna.delivery.delivery.payload.response.DeliveryResponse;
import com.tuvarna.delivery.delivery.repository.DeliveryRepository;
import com.tuvarna.delivery.delivery.repository.StatusRepository;
import com.tuvarna.delivery.delivery.service.helper.DeliveryHelper;
import com.tuvarna.delivery.exception.ResourceNotFoundException;
import com.tuvarna.delivery.user.model.User;
import com.tuvarna.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final UserRepository userRepository;
    private final DeliveryHelper deliveryHelper;

    @Transactional
    public DeliveryRequestDTO requestDelivery(DeliveryRequestDTO requestDTO, String username) {
        User user = userRepository.findUserByUsernameIgnoreCase(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Username", username));

        Delivery delivery  = DeliveryMapper.INSTANCE.dtoToEntity(requestDTO);
        deliveryHelper.setupDelivery(requestDTO, delivery);
        delivery.setUser(user);

        deliveryRepository.save(delivery);
        return requestDTO;
    }

    public DeliveryResponse retrieveAndFilterDeliveries(String username, String statusName, int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Delivery> deliveries = deliveryRepository.findAndFilterDeliveries(username, statusName, pageable);

        return deliveryHelper.getDeliveryResponse(deliveries);
    }


    @Transactional
    public void deleteDelivery(Long id) {
        Delivery delivery = deliveryRepository
                .findById(id).orElseThrow(() -> new ResourceNotFoundException("Delivery", "Id", id));

        deliveryRepository.delete(delivery);
    }

    public DeliveryDTO retrieveDeliveryInfo(Long id) {
        Delivery delivery = deliveryRepository
                .findById(id).orElseThrow(() -> new ResourceNotFoundException("Delivery", "Id", id));

        return DeliveryMapper.INSTANCE.entityToDTO(delivery);
    }


    public DeliveryDTO updateDelivery(Long id, DeliveryRequestDTO requestDTO) {
        Delivery delivery = deliveryRepository
                .findById(id).orElseThrow(() -> new ResourceNotFoundException("Delivery", "Id", id));

        DeliveryMapper.INSTANCE.updateEntityWithDTO(requestDTO, delivery);
        deliveryHelper.setupDelivery(requestDTO, delivery);

        return DeliveryMapper.INSTANCE.entityToDTO(deliveryRepository.save(delivery));
    }
}
