package com.micorservice.users.infrastructure.out.jpa.mapper;

import com.micorservice.users.domain.model.RoleModel;
import com.micorservice.users.infrastructure.out.jpa.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IRoleEntityMapper {

    RoleModel entityToModel(RoleEntity roleEntity);

}
