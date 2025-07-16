package com.micorservice.users.application.handler;

import com.micorservice.users.application.dto.response.PhoneResponseDto;
import com.micorservice.users.application.dto.request.UserRequestDto;
import com.micorservice.users.application.dto.response.SaveMessageResponse;

public interface IUserHandler {

    SaveMessageResponse saveUser(UserRequestDto userRequestDto);

    SaveMessageResponse saveEmployee(UserRequestDto userRequestDto, Long restaurantId);

    void validateUserRole(Long userId, String expectedRole);

    PhoneResponseDto getPhoneNumberByUserId(Long customerId);


}
