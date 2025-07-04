package com.micorservice.users.application.mapper;

import com.micorservice.users.application.dto.request.UserRequestDto;
import com.micorservice.users.domain.model.UserModel;

public interface IUserRequestMapper {

    UserModel requestToModel(UserRequestDto userRequestDto);

}
