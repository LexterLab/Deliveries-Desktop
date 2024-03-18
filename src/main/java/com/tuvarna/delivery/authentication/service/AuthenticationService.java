package com.tuvarna.delivery.authentication.service;

import com.tuvarna.delivery.authentication.payload.request.LoginRequestDTO;
import com.tuvarna.delivery.authentication.payload.request.SignUpRequestDTO;
import com.tuvarna.delivery.authentication.service.helper.AuthenticationHelper;
import com.tuvarna.delivery.exception.APIException;
import com.tuvarna.delivery.jwt.JWTAuthenticationResponse;
import com.tuvarna.delivery.jwt.JwtTokenProvider;
import com.tuvarna.delivery.user.model.User;
import com.tuvarna.delivery.user.repository.UserRepository;
import com.tuvarna.delivery.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final AuthenticationHelper helper;

    public JWTAuthenticationResponse login(LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.email(), loginRequestDTO.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        JWTAuthenticationResponse authenticationResponse = new JWTAuthenticationResponse();
        authenticationResponse.setAccessToken(tokenProvider.generateToken(authentication.getName()));
        return authenticationResponse;
    }

    @Transactional
    public String signUp(SignUpRequestDTO requestDTO) {
        if (userRepository.existsByUsernameIgnoreCase(requestDTO.username())) {
            throw new APIException(HttpStatus.BAD_REQUEST, Messages.USER_EXISTS);
        }

        User user = helper.buildUser(requestDTO);
        userRepository.save(user);
        return Messages.USER_SUCCESSFULLY_REGISTERED;
    }
}
