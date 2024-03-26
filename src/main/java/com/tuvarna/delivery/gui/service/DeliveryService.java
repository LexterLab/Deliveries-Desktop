package com.tuvarna.delivery.gui.service;

import com.tuvarna.delivery.delivery.payload.response.DeliveryResponse;
import com.tuvarna.delivery.gui.AccessTokenStorage;
import com.tuvarna.delivery.utils.AppConstants;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
        if (statusId != null) {
            builder.queryParam("statusId", statusId);
        }

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        String url = builder.toUriString();

        return restTemplate.exchange(url, HttpMethod.GET, entity, DeliveryResponse.class);
    }
}
