package com.micorservice.users.domain.api;

import com.micorservice.users.domain.model.UserModel;

public interface IAuthServicePort {

    UserModel loginUser(String email, String password);

}
