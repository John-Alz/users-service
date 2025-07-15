package com.micorservice.users.infrastructure.out.jpa.adapter;

import com.micorservice.users.domain.model.RoleModel;
import com.micorservice.users.domain.spi.IRolePersistencePort;
import com.micorservice.users.infrastructure.exception.NoDataFoundException;
import com.micorservice.users.infrastructure.out.jpa.entity.RoleEntity;
import com.micorservice.users.infrastructure.out.jpa.mapper.IRoleEntityMapper;
import com.micorservice.users.infrastructure.out.jpa.repository.IRoleRepository;
import com.micorservice.users.infrastructure.utils.InfrastructureConstants;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoleJpaAdapter implements IRolePersistencePort {

    private final IRoleRepository roleRepository;
    private final IRoleEntityMapper roleEntityMapper;

    @Override
    public RoleModel findById(Long id) {
        RoleEntity roleFound = roleRepository.findById(id).orElse(null);
        if (roleFound == null) {
            throw new NoDataFoundException(InfrastructureConstants.ROLE_NOT_FOUND);
        }
        return roleEntityMapper.entityToModel(roleFound);
    }
}
