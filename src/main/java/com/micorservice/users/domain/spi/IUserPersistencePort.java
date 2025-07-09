package com.micorservice.users.domain.spi;

import com.micorservice.users.domain.model.UserModel;

public interface IUserPersistencePort {

    void saveUser(UserModel userModel);

    String passwordEncode(String password);
}
