package com.micorservice.users.infrastructure.out.jpa.adapter;

import com.micorservice.users.application.dto.auth.AuthInfo;
import com.micorservice.users.domain.model.UserModel;
import com.micorservice.users.domain.spi.IUserPersistencePort;
import com.micorservice.users.infrastructure.exception.*;
import com.micorservice.users.infrastructure.feign.clients.RestaurantClient;
import com.micorservice.users.infrastructure.out.jpa.entity.UserEntity;
import com.micorservice.users.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.micorservice.users.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
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
        String info = authentication.getPrincipal().toString();
        System.out.println(info);
        return authInfo.id();
    }

    @Override
    public String getRoleUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthInfo authInfo;
        if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        } else {
          authInfo  = (AuthInfo) authentication.getPrincipal();
        }
        return authInfo.role();
    }

    @Override
    public void isOwnerOfRestaurant(Long restaurantId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthInfo authInfo = (AuthInfo) authentication.getPrincipal();
        System.out.println(authInfo.id());
        restaurantClient.isOwnerOfRestaurant(restaurantId, authInfo.id());
    }

    @Override
    public Long getRestaurantByUser(Long employeeId) {
        UserEntity userFound = userRepository.findById(employeeId).orElse(null);
        if (userFound == null) {
            throw new UserNotFoundByIdException();
        }
        if (!userFound.getRole().getName().equals("EMPLOYEE")) {
            throw new InvalidUserRoleException();
        }
        return userFound.getRestaurantId();
    }


}
