package com.micorservice.users.domain.usecase;

import com.micorservice.users.domain.api.IUserServicePort;
import com.micorservice.users.domain.model.RoleModel;
import com.micorservice.users.domain.model.UserModel;
import com.micorservice.users.domain.spi.IRolePersistencePort;
import com.micorservice.users.domain.spi.IUserPersistencePort;
import com.micorservice.users.domain.validation.UserRulesValidator;

public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IRolePersistencePort rolePersistencePort;
    private final UserRulesValidator userRulesValidator;

    public UserUseCase(IUserPersistencePort userPersistencePort, IRolePersistencePort rolePersistencePort, UserRulesValidator userRulesValidator) {
        this.userPersistencePort = userPersistencePort;
        this.rolePersistencePort = rolePersistencePort;
        this.userRulesValidator = userRulesValidator;
    }

    @Override
    public void saveUser(UserModel userModel) {
        RoleModel roleFound = rolePersistencePort.findById(2L);
        userPersistencePort.userExistWithEmail(userModel.getEmail());
        userModel.setRole(roleFound);
        userRulesValidator.validateUserData(userModel);
        userModel.setPassword(userPersistencePort.passwordEncode(userModel.getPassword()));
        userPersistencePort.saveUser(userModel);
    }

    @Override
    public void validateUserRole(Long userId, String expectedRole) {
        userPersistencePort.validateUserRole(userId, expectedRole);
    }
}
