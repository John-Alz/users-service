package com.micorservice.users.application.handler.impl;

import com.micorservice.users.application.dto.auth.AuthInfo;
import com.micorservice.users.application.dto.request.LoginRequestDto;
import com.micorservice.users.application.dto.response.LoginUserResponseDto;
import com.micorservice.users.application.handler.IAuthHandler;
import com.micorservice.users.application.mapper.IAuthRequestMapper;
import com.micorservice.users.application.utils.JwtUtils;
import com.micorservice.users.domain.api.IAuthServicePort;
import com.micorservice.users.domain.model.UserModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthHandler implements IAuthHandler {

    private final IAuthServicePort authServicePort;
    private final IAuthRequestMapper authRequestMapper;
    private final JwtUtils jwtUtils;

    @Override
    public LoginUserResponseDto loginUser(LoginRequestDto loginRequestDto) {
        Authentication authentication = this.authenticate(loginRequestDto.email(), loginRequestDto.password());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtUtils.createToken(authentication);
        return new LoginUserResponseDto(loginRequestDto.email(), "Logueado", accessToken);
    }

    public Authentication authenticate(String email, String password) {
        UserModel user = authServicePort.loginUser(email, password);
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getName()));
        AuthInfo authInfo = new AuthInfo(user.getId(), email);
        return new UsernamePasswordAuthenticationToken(authInfo, password, authorities);
    }
}
