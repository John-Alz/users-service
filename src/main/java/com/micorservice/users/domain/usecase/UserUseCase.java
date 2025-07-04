package com.micorservice.users.domain.usecase;

import com.micorservice.users.domain.api.IUserServicePort;
import com.micorservice.users.domain.model.UserModel;
import com.micorservice.users.domain.spi.IUserPersistencePort;

public class UserUseCase implements IUserServicePort {

    private IUserPersistencePort userPersistencePort;

    public UserUseCase(IUserPersistencePort userPersistencePort) {
        this.userPersistencePort = userPersistencePort;
    }

    @Override
    public void saveUser(UserModel userModel) {
        userPersistencePort.saveUser(userModel);
    }
}
