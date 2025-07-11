package com.micorservice.users.application.handler;

import com.micorservice.users.application.dto.request.LoginRequestDto;
import com.micorservice.users.application.dto.response.LoginUserResponseDto;

public interface IAuthHandler {

    LoginUserResponseDto loginUser(LoginRequestDto loginRequestDto);

}
