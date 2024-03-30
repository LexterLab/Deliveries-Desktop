package com.tuvarna.delivery.gui.service;

import com.tuvarna.delivery.delivery.payload.response.DeliveryResponse;
import com.tuvarna.delivery.gui.utils.AccessTokenStorage;
import com.tuvarna.delivery.user.payload.request.UserRequestDTO;
import com.tuvarna.delivery.user.payload.response.UserDTO;
import com.tuvarna.delivery.user.payload.response.UserResponseDTO;
import com.tuvarna.delivery.utils.AppConstants;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;

public class UserService {

    public ResponseEntity<UserDTO> fetchUpdateUser(Long userId, UserRequestDTO requestDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AccessTokenStorage.retrieveAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UserRequestDTO> entity = new HttpEntity<>(requestDTO, headers);

        RestTemplate restTemplate = new RestTemplate();
        String url = AppConstants.DOMAIN + "users/" + userId;
        return restTemplate.exchange(url, HttpMethod.PUT, entity, UserDTO.class);
    }

    public ResponseEntity<Void> fetchDeleteUser(Long userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AccessTokenStorage.retrieveAccessToken());

        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        String url = AppConstants.DOMAIN + "users/" + userId ;

        return restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
    }

    public ResponseEntity<UserResponseDTO> fetAllUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AccessTokenStorage.retrieveAccessToken());

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        String url = AppConstants.DOMAIN + "users";

        return restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
        });
    }

    public ResponseEntity<DeliveryResponse> fetchFilteredUserDeliveries(LocalDate afterDate) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AccessTokenStorage.retrieveAccessToken());

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(AppConstants.DOMAIN + "users/deliveries");
        if (afterDate != null) {
            builder.queryParam("afterDate", afterDate);
        }

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        String url = builder.toUriString();

        return restTemplate.exchange(url, HttpMethod.GET, entity, DeliveryResponse.class);
    }
}
