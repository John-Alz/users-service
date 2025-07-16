package com.micorservice.users.domain.usecase;

import com.micorservice.users.domain.api.IUserServicePort;
import com.micorservice.users.domain.model.RoleModel;
import com.micorservice.users.domain.model.UserModel;
import com.micorservice.users.domain.spi.IRolePersistencePort;
import com.micorservice.users.domain.spi.IUserPersistencePort;
import com.micorservice.users.domain.utils.DomainConstants;
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
            roleFound = rolePersistencePort.findById(DomainConstants.ROLE_CUSTOMER);
        } else if (role.equals(DomainConstants.ROLE_ADMIN)) {
            roleFound = rolePersistencePort.findById(DomainConstants.ROLE_OWNER);
        } else {
            throw new NoDataFoundException(DomainConstants.ROLE_NOT_FOUND);
        }

        userPersistencePort.userExistWithEmail(userModel.getEmail());
        userModel.setRole(roleFound);
        userRulesValidator.validateUserData(userModel);
        userModel.setPassword(userPersistencePort.passwordEncode(userModel.getPassword()));
        userPersistencePort.saveUser(userModel);
    }

    @Override
    public void saveEmployee(UserModel userModel, Long restaurantId) {
        userPersistencePort.isOwnerOfRestaurant(restaurantId);
        RoleModel roleFound = rolePersistencePort.findById(DomainConstants.ROLE_EMPLOYEE);
        userPersistencePort.userExistWithEmail(userModel.getEmail());
        userModel.setRole(roleFound);
        userRulesValidator.validateUserData(userModel);
        userModel.setPassword(userPersistencePort.passwordEncode(userModel.getPassword()));
        UserModel userCreated = userPersistencePort.saveEmployee(userModel, restaurantId);
        userPersistencePort.createEmployee(userCreated.getId(), restaurantId);
    }

    @Override
    public void validateUserRole(Long userId, String expectedRole) {
        userPersistencePort.validateUserRole(userId, expectedRole);
    }

    @Override
    public String getPhoneNumberByUserId(Long customerId) {
        return userPersistencePort.getPhoneNumberByUserId(customerId);
    }
}
