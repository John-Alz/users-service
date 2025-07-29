package com.micorservice.users.infrastructure.configuration;

import com.micorservice.users.domain.api.IAuthServicePort;
import com.micorservice.users.domain.api.IUserServicePort;
import com.micorservice.users.domain.spi.IRolePersistencePort;
import com.micorservice.users.domain.spi.IUserPersistencePort;
import com.micorservice.users.domain.usecase.AuthUseCase;
import com.micorservice.users.domain.usecase.UserUseCase;
import com.micorservice.users.domain.validation.UserRulesValidator;
import com.micorservice.users.infrastructure.feign.clients.RestaurantClient;
import com.micorservice.users.infrastructure.out.jpa.adapter.RoleJpaAdapter;
import com.micorservice.users.infrastructure.out.jpa.adapter.UserJpaAdapter;
import com.micorservice.users.infrastructure.out.jpa.mapper.IRoleEntityMapper;
import com.micorservice.users.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.micorservice.users.infrastructure.out.jpa.repository.IRoleRepository;
import com.micorservice.users.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    private final IRoleRepository roleRepository;
    private final IRoleEntityMapper roleEntityMapper;
    private final RestaurantClient restaurantClient;

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserJpaAdapter(userRepository, userEntityMapper, passwordEncoder(), restaurantClient);
    }

    @Bean
    public IRolePersistencePort rolePersistencePort() {
        return new RoleJpaAdapter(roleRepository, roleEntityMapper);
    }

    @Bean
    public IUserServicePort userServicePort() {
        return new UserUseCase(userPersistencePort(), rolePersistencePort(), userRulesValidator());
    }

    @Bean
    public IAuthServicePort authServicePort() {
        return new AuthUseCase(userPersistencePort());
    }

    @Bean
    public UserRulesValidator userRulesValidator() {
        return new UserRulesValidator();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
