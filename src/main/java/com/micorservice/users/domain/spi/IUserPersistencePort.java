package com.micorservice.users.domain.spi;

import com.micorservice.users.domain.model.UserModel;

public interface IUserPersistencePort {

    void saveUser(UserModel userModel);

    UserModel saveEmployee(UserModel userModel, Long restaurantId);

    String passwordEncode(String password);

    boolean passwordDecode(String passwordRequest, String passwordUserDb);

    void userExistWithEmail(String email);

    UserModel getUserByEmail(String email);

    void validateUserRole(Long userId, String expectedRole);

    Long getUserId();
    String getRoleUser();

    void isOwnerOfRestaurant(Long restaurantId);

    void createEmployee(Long employeeId, Long restaurantId);

    UserModel getInfoByUserId(Long customerId);



}
