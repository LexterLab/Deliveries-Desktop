package com.tuvarna.delivery.gui.service;

import com.tuvarna.delivery.gui.utils.AccessTokenStorage;
import com.tuvarna.delivery.office.payload.response.OfficeDTO;
import com.tuvarna.delivery.utils.AppConstants;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class OfficeService {

    public ResponseEntity<List<OfficeDTO>> fetchAllOffices() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AccessTokenStorage.retrieveAccessToken());

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        String url = AppConstants.DOMAIN + "offices";

        return restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
        });
    }
}
