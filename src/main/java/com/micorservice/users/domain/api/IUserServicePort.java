package com.micorservice.users.domain.api;

import com.micorservice.users.domain.model.UserModel;

public interface IUserServicePort {

    void saveUser(UserModel userModel);

    void saveEmployee(UserModel userModel, Long restaurantId);

    void validateUserRole(Long userId, String expectedRole);

    UserModel getInfoByUserId(Long customerId);

}

