package com.micorservice.users.application.handler;

import com.micorservice.users.application.dto.response.PhoneResponseDto;
import com.micorservice.users.application.dto.response.RestaurantIdResponseDto;
import com.micorservice.users.application.dto.request.UserRequestDto;
import com.micorservice.users.application.dto.response.SaveMessageResponse;

public interface IUserHandler {

    SaveMessageResponse saveUser(UserRequestDto userRequestDto);

    void validateUserRole(Long userId, String expectedRole);

    RestaurantIdResponseDto getRestaurantByUser(Long employeeId);

    PhoneResponseDto getPhoneNumberByUserId(Long customerId);


}
