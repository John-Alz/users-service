package com.micorservice.users.domain.api;

import com.micorservice.users.domain.model.UserModel;

public interface IUserServicePort {

    void saveUser(UserModel userModel);

    void validateUserRole(Long userId, String expectedRole);

    Long getRestaurantByUser(Long employeeId);

}

