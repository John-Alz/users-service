package com.micorservice.users.domain.spi;

import com.micorservice.users.domain.model.RoleModel;

public interface IRolePersistencePort {

    RoleModel findById(Long id);


}
