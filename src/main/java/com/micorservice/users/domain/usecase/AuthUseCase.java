package com.micorservice.users.domain.usecase;

import com.micorservice.users.domain.api.IAuthServicePort;
import com.micorservice.users.domain.exception.PasswordInvalidException;
import com.micorservice.users.domain.model.UserModel;
import com.micorservice.users.domain.spi.IUserPersistencePort;

public class AuthUseCase implements IAuthServicePort {

    private final IUserPersistencePort userPersistencePort;

    public AuthUseCase (IUserPersistencePort userPersistencePort) {
        this.userPersistencePort = userPersistencePort;
    }

    @Override
    public UserModel loginUser(String email, String password) {
        UserModel userFound = userPersistencePort.getUserByEmail(email);
        if (!userPersistencePort.passwordDecode(password, userFound.getPassword())){
            throw new PasswordInvalidException();
        }
        return userFound;
    }
}
