package com.tuvarna.delivery.gui.service;

import com.tuvarna.delivery.gui.AccessTokenStorage;
import com.tuvarna.delivery.user.payload.response.UserDTO;
import com.tuvarna.delivery.utils.AppConstants;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class UserService {
    public ResponseEntity<UserDTO> fetchUserInfo() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AccessTokenStorage.retrieveAccessToken());

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        String url = AppConstants.DOMAIN + "info";

        return restTemplate.exchange(url, HttpMethod.GET, entity, UserDTO.class);
    }
}
