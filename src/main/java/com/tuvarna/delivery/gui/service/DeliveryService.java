package com.tuvarna.delivery.gui.service;

import com.tuvarna.delivery.delivery.payload.request.DeliveryRequestDTO;
import com.tuvarna.delivery.delivery.payload.response.DeliveryDTO;
import com.tuvarna.delivery.delivery.payload.response.DeliveryResponse;
import com.tuvarna.delivery.gui.utils.AccessTokenStorage;
import com.tuvarna.delivery.office.payload.request.CourierRequestDTO;
import com.tuvarna.delivery.utils.AppConstants;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class DeliveryService {
    public ResponseEntity<DeliveryResponse> fetchFilterDeliveries(String username, Long statusId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AccessTokenStorage.retrieveAccessToken());

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(AppConstants.DOMAIN + "deliveries");
        if (username != null) {
            builder.queryParam("username", username);
        }
        if (statusId != null && statusId > 0) {
            builder.queryParam("statusId", statusId);
        }

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        String url = builder.toUriString();

        return restTemplate.exchange(url, HttpMethod.GET, entity, DeliveryResponse.class);
    }

    public ResponseEntity<DeliveryDTO> fetchUpdateDelivery(Long deliveryId, DeliveryRequestDTO requestDTO) {
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        headers.setBearerAuth(AccessTokenStorage.retrieveAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<DeliveryRequestDTO> request = new HttpEntity<>(requestDTO, headers);
        return restTemplate.exchange(
                AppConstants.DOMAIN + "deliveries/" + deliveryId,
                HttpMethod.PUT,
                request,
                DeliveryDTO.class);
    }

    public ResponseEntity<DeliveryDTO> fetchUpdateDeliveryStatus(Long deliveryId, long statusId) {
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        headers.setBearerAuth(AccessTokenStorage.retrieveAccessToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = AppConstants.DOMAIN + "deliveries/" + deliveryId + "/statuses/" + statusId;
        return restTemplate.exchange(url, HttpMethod.PUT, entity, DeliveryDTO.class);
    }

    public ResponseEntity<Void> fetchDeleteDelivery(Long deliveryId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AccessTokenStorage.retrieveAccessToken());

        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        String url = AppConstants.DOMAIN + "deliveries/" + deliveryId ;

        return restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
    }

    public ResponseEntity<DeliveryRequestDTO> requestDelivery(DeliveryRequestDTO requestDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AccessTokenStorage.retrieveAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<DeliveryRequestDTO> entity = new HttpEntity<>(requestDTO, headers);

        RestTemplate restTemplate = new RestTemplate();

        String url = AppConstants.DOMAIN + "deliveries";
        return restTemplate.exchange(url, HttpMethod.POST, entity, DeliveryRequestDTO.class);
    }
}
