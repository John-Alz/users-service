package com.micorservice.users.infrastructure.input.rest;

import com.micorservice.users.application.dto.request.LoginRequestDto;
import com.micorservice.users.application.dto.response.LoginUserResponseDto;
import com.micorservice.users.application.handler.IAuthHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/login")
@RequiredArgsConstructor
public class AuthRestController {

    private final IAuthHandler authHandler;

    @PostMapping()
    public ResponseEntity<LoginUserResponseDto> loginUser(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(authHandler.loginUser(loginRequestDto));
    }

}
