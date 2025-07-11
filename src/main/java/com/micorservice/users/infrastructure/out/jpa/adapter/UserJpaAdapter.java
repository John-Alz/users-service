package com.micorservice.users.infrastructure.out.jpa.adapter;

import com.micorservice.users.application.dto.auth.AuthInfo;
import com.micorservice.users.domain.model.UserModel;
import com.micorservice.users.domain.spi.IUserPersistencePort;
import com.micorservice.users.infrastructure.feign.clients.RestaurantClient;
import com.micorservice.users.infrastructure.exception.AlreadyExistsException;
import com.micorservice.users.infrastructure.exception.InvalidUserRoleException;
import com.micorservice.users.infrastructure.exception.NoDataFoundException;
import com.micorservice.users.infrastructure.exception.UserNotFoundByEmailException;
import com.micorservice.users.infrastructure.out.jpa.entity.UserEntity;
import com.micorservice.users.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.micorservice.users.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;
    private final PasswordEncoder passwordEncoder;
    private final RestaurantClient restaurantClient;

    @Override
    public void saveUser(UserModel userModel) {
        userRepository.save(userEntityMapper.toEntity(userModel));
    }

    @Override
    public String passwordEncode(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public boolean passwordDecode(String passwordRequest, String passwordUserDb) {
        return passwordEncoder.matches(passwordRequest, passwordUserDb);
    }

    @Override
    public void userExistWithEmail(String email) {
        UserEntity userFound = userRepository.findByEmail(email).orElse(null);
        if (userFound != null) {
            throw new AlreadyExistsException("Ya existe un usuario registrado con ese email en nuestro sistema.");
        }
    }

    @Override
    public UserModel getUserByEmail(String email) {
        UserEntity userFound = userRepository.findByEmail(email).orElse(null);
        if (userFound == null) {
            throw new UserNotFoundByEmailException();
        }
        return userEntityMapper.toModel(userFound);
    }

    @Override
    public void validateUserRole(Long userId, String expectedRole) {
        UserEntity userFound = userRepository.findById(userId).orElse(null);
        if (userFound == null) {
            throw new NoDataFoundException("No se econtro usuario con ese id.");
        }
        System.out.println(userFound.getRole());

        if (!userFound.getRole().getName().equals(expectedRole)) {
            throw new InvalidUserRoleException();
        }
    }

    @Override
    public Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthInfo authInfo = (AuthInfo) authentication.getPrincipal();
        return authInfo.id();
    }

    @Override
    public String getRoleUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthInfo authInfo = (AuthInfo) authentication.getPrincipal();
        return authInfo.role();
    }

    @Override
    public void isOwnerOfRestaurant(Long restaurantId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthInfo authInfo = (AuthInfo) authentication.getPrincipal();
        restaurantClient.isOwnerOfRestaurant(restaurantId, authInfo.id());
    }


}
