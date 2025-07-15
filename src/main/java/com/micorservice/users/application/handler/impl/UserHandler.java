package com.micorservice.users.application.handler.impl;

import com.micorservice.users.application.dto.response.PhoneResponseDto;
import com.micorservice.users.application.dto.response.RestaurantIdResponseDto;
import com.micorservice.users.application.dto.request.UserRequestDto;
import com.micorservice.users.application.dto.response.SaveMessageResponse;
import com.micorservice.users.application.handler.IUserHandler;
import com.micorservice.users.application.mapper.IUserRequestMapper;
import com.micorservice.users.domain.api.IUserServicePort;
import com.micorservice.users.domain.model.UserModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class UserHandler implements IUserHandler {

    private final IUserServicePort userServicePort;
    private final IUserRequestMapper userRequestMapper;

    @Override
    public SaveMessageResponse saveUser(UserRequestDto userRequestDto) {
        UserModel userModel = userRequestMapper.requestToModel(userRequestDto);
        userServicePort.saveUser(userModel);
        return new SaveMessageResponse("Usuario creado.", LocalDateTime.now());
    }

    @Override
    public void validateUserRole(Long userId, String expectedRole) {
        userServicePort.validateUserRole(userId, expectedRole);
    }

    @Override
    public RestaurantIdResponseDto getRestaurantByUser(Long employeeId) {
        Long restaurantId = userServicePort.getRestaurantByUser(employeeId);
        return new RestaurantIdResponseDto(restaurantId);
    }

    @Override
    public PhoneResponseDto getPhoneNumberByUserId(Long customerId) {
        String phoneNumber = userServicePort.getPhoneNumberByUserId(customerId);
        return new PhoneResponseDto(phoneNumber);
    }
}
