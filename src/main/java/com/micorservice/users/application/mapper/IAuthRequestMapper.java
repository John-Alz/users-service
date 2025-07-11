package com.micorservice.users.application.mapper;

import com.micorservice.users.application.dto.request.LoginRequestDto;
import com.micorservice.users.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface IAuthRequestMapper {

    UserModel requestToModel(LoginRequestDto loginRequestDto);

}
