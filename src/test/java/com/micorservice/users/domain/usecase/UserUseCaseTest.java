package com.micorservice.users.domain.usecase;

import com.micorservice.users.domain.model.RoleModel;
import com.micorservice.users.domain.model.UserModel;
import com.micorservice.users.domain.spi.IRolePersistencePort;
import com.micorservice.users.domain.spi.IUserPersistencePort;
import com.micorservice.users.domain.validation.UserRulesValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IRolePersistencePort rolePersistencePort;

    @Mock
    private UserRulesValidator userRulesValidator;

    @InjectMocks
    private UserUseCase userUseCase;

    private UserModel userModel;
    private RoleModel roleModel;

    @BeforeEach
    void setUp() {
        userModel = new UserModel();
        userModel.setId(1L);
        userModel.setFirstName("John");
        userModel.setLastName("Angel");
        userModel.setDocumentNumber("10035678934");
        userModel.setPhoneNumber("314575937");
        userModel.setBirthDate(LocalDate.of(2003, 07, 26));
        userModel.setEmail("john@gmail.com");
        userModel.setPassword("password");
        userModel.setRole(roleModel);

        roleModel = new RoleModel();
        roleModel.setId(2L);
        roleModel.setName("OWNER");
        roleModel.setDescription("User owner");
    }

    @Test
    void saveUser() {
        when(rolePersistencePort.findById(2L)).thenReturn(roleModel);
        when(userPersistencePort.passwordEncode("password")).thenReturn("encode-password");

        userUseCase.saveUser(userModel);

        verify(rolePersistencePort, times(1)).findById(2L);
        verify(userRulesValidator, times(1)).validateUserData(userModel);
        verify(userPersistencePort, times(1)).passwordEncode("password");
        verify(userPersistencePort, times(1)).saveUser(userModel);

        assert userModel.getRole().equals(roleModel);
        assert userModel.getPassword().equals("encode-password");
    }
}