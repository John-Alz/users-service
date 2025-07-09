package com.micorservice.users.infrastructure.out.jpa.adapter;

import com.micorservice.users.domain.model.UserModel;
import com.micorservice.users.domain.spi.IUserPersistencePort;
import com.micorservice.users.infrastructure.exception.AlreadyExistsException;
import com.micorservice.users.infrastructure.exception.NoDataFoundException;
import com.micorservice.users.infrastructure.out.jpa.entity.UserEntity;
import com.micorservice.users.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.micorservice.users.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(UserModel userModel) {
        userRepository.save(userEntityMapper.toEntity(userModel));
    }

    @Override
    public String passwordEncode(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public void userExistWithEmail(String email) {
        UserEntity userFound = userRepository.findByEmail(email).orElse(null);
        if (userFound != null) {
            throw new AlreadyExistsException("Ya existe un usuario registrado con ese email en nuestro sistema.");
        }
    }
}
