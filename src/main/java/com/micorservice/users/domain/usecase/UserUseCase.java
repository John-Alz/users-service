package com.micorservice.users.domain.usecase;

import com.micorservice.users.domain.api.IUserServicePort;
import com.micorservice.users.domain.model.RoleModel;
import com.micorservice.users.domain.model.UserModel;
import com.micorservice.users.domain.spi.IRolePersistencePort;
import com.micorservice.users.domain.spi.IUserPersistencePort;
import com.micorservice.users.domain.validation.UserRulesValidator;
import com.micorservice.users.infrastructure.exception.NoDataFoundException;

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

        String role = userPersistencePort.getRoleUser();
        RoleModel roleFound = null;

        if (role == null) {
            roleFound = rolePersistencePort.findById(4L);
            userModel.setRestaurantId(null);
        } else if (role.equals("ROLE_ADMINISTRATOR")) {
            roleFound = rolePersistencePort.findById(2L);
            userModel.setRestaurantId(null);
        } else if (role.equals("ROLE_OWNER")) {
            userPersistencePort.isOwnerOfRestaurant(userModel.getRestaurantId());
            roleFound = rolePersistencePort.findById(3L);
        } else {
            throw new NoDataFoundException("Rol no econtrado.");
        }

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
