package com.tuvarna.delivery.gui.service;

import com.tuvarna.delivery.gui.AccessTokenStorage;
import com.tuvarna.delivery.office.payload.request.UpdateCourierRequestDTO;
import com.tuvarna.delivery.office.payload.response.CourierResponseDTO;
import com.tuvarna.delivery.utils.AppConstants;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class CourierService {

    public ResponseEntity<CourierResponseDTO> fetchAllCouriers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AccessTokenStorage.retrieveAccessToken());

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        String url = AppConstants.DOMAIN + "couriers";

        return restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
        });
    }

    public ResponseEntity<Void> fetchDeleteCourier(Long officeId, Long courierId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AccessTokenStorage.retrieveAccessToken());

        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        String url = AppConstants.DOMAIN + "offices/" + officeId + "/couriers" + courierId;

        return restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
    }

    public ResponseEntity<UpdateCourierRequestDTO> fetchUpdateCourier(Long officeId, Long courierId,
                                                                      UpdateCourierRequestDTO requestDTO) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AccessTokenStorage.retrieveAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UpdateCourierRequestDTO> entity = new HttpEntity<>(requestDTO, headers);

        RestTemplate restTemplate = new RestTemplate();

        String url = AppConstants.DOMAIN + "offices/" + officeId + "/couriers" + courierId;
        return restTemplate.exchange(url, HttpMethod.PUT, entity, UpdateCourierRequestDTO.class);
}
}
