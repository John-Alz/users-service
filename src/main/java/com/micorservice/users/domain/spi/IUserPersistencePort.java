package com.micorservice.users.domain.spi;

import com.micorservice.users.domain.model.UserModel;

public interface IUserPersistencePort {

    void saveUser(UserModel userModel);

    String passwordEncode(String password);

    boolean passwordDecode(String passwordRequest, String passwordUserDb);

    void userExistWithEmail(String email);

    UserModel getUserByEmail(String email);

    void validateUserRole(Long userId, String expectedRole);

    Long getUserId();
    String getRoleUser();

    void isOwnerOfRestaurant(Long restaurantId);

    Long getRestaurantByUser(Long employeeId);

    String getPhoneNumberByUserId(Long customerId);



}
