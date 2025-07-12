package com.micorservice.users.domain.usecase;

import com.micorservice.users.domain.model.RoleModel;
import com.micorservice.users.domain.model.UserModel;
import com.micorservice.users.domain.spi.IRolePersistencePort;
import com.micorservice.users.domain.spi.IUserPersistencePort;
import com.micorservice.users.domain.validation.UserRulesValidator;
import com.micorservice.users.infrastructure.exception.NoDataFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
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
    private RoleModel adminRole;
    private RoleModel ownerRole;
    private RoleModel employeeRole;

    @BeforeEach
    void setUp() {
        adminRole = new RoleModel();
        adminRole.setId(2L);
        adminRole.setName("ADMINISTRATOR");

        ownerRole = new RoleModel();
        ownerRole.setId(3L);
        ownerRole.setName("OWNER");

        employeeRole = new RoleModel();
        employeeRole.setId(4L);
        employeeRole.setName("EMPLOYEE");

        userModel = new UserModel();
        userModel.setId(1L);
        userModel.setFirstName("John");
        userModel.setLastName("Angel");
        userModel.setDocumentNumber("10035678934");
        userModel.setPhoneNumber("314575937");
        userModel.setBirthDate(LocalDate.of(2003, 7, 26));
        userModel.setEmail("john@gmail.com");
        userModel.setPassword("password");
        userModel.setRestaurantId(77L);
    }

    @Test
    void saveUser_ShouldAssignAdministratorRole_WhenCallerIsAdministrator() {
        when(userPersistencePort.getRoleUser()).thenReturn("ROLE_ADMINISTRATOR");
        when(rolePersistencePort.findById(2L)).thenReturn(adminRole);
        when(userPersistencePort.passwordEncode("password")).thenReturn("encoded-password");

        userUseCase.saveUser(userModel);

        verify(userPersistencePort).getRoleUser();
        verify(rolePersistencePort).findById(2L);
        verify(userPersistencePort).userExistWithEmail("john@gmail.com");
        verify(userRulesValidator).validateUserData(userModel);
        verify(userPersistencePort).passwordEncode("password");
        verify(userPersistencePort).saveUser(userModel);

        assertEquals(adminRole, userModel.getRole());
        assertEquals("encoded-password", userModel.getPassword());
        assertNull(userModel.getRestaurantId());  // Se espera que se seteé a null
    }

    @Test
    void saveUser_ShouldAssignOwnerRole_WhenCallerIsOwner() {
        when(userPersistencePort.getRoleUser()).thenReturn("ROLE_OWNER");
        when(rolePersistencePort.findById(3L)).thenReturn(ownerRole);
        when(userPersistencePort.passwordEncode("password")).thenReturn("encoded-password");

        userUseCase.saveUser(userModel);

        verify(userPersistencePort).getRoleUser();
        verify(userPersistencePort).isOwnerOfRestaurant(77L);
        verify(rolePersistencePort).findById(3L);
        verify(userPersistencePort).userExistWithEmail("john@gmail.com");
        verify(userRulesValidator).validateUserData(userModel);
        verify(userPersistencePort).passwordEncode("password");
        verify(userPersistencePort).saveUser(userModel);

        assertEquals(ownerRole, userModel.getRole());
        assertEquals("encoded-password", userModel.getPassword());
    }

    @Test
    void saveUser_ShouldAssignEmployeeRole_WhenRoleIsNull() {
        when(userPersistencePort.getRoleUser()).thenReturn(null);
        when(rolePersistencePort.findById(4L)).thenReturn(employeeRole);
        when(userPersistencePort.passwordEncode("password")).thenReturn("encoded-password");

        userUseCase.saveUser(userModel);

        verify(userPersistencePort).getRoleUser();
        verify(rolePersistencePort).findById(4L);
        verify(userPersistencePort).userExistWithEmail("john@gmail.com");
        verify(userRulesValidator).validateUserData(userModel);
        verify(userPersistencePort).passwordEncode("password");
        verify(userPersistencePort).saveUser(userModel);

        assertEquals(employeeRole, userModel.getRole());
        assertEquals("encoded-password", userModel.getPassword());
        assertNull(userModel.getRestaurantId());  // Se espera que se seteé a null
    }

    @Test
    void saveUser_ShouldThrowException_WhenRoleIsUnknown() {
        when(userPersistencePort.getRoleUser()).thenReturn("ROLE_CLIENT");

        NoDataFoundException exception = assertThrows(NoDataFoundException.class, () -> userUseCase.saveUser(userModel));

        assertEquals("Rol no econtrado.", exception.getMessage());

        verify(userPersistencePort).getRoleUser();
        verify(rolePersistencePort, never()).findById(anyLong());
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void validateUserRole_ShouldDelegateToPersistencePort() {
        Long userId = 5L;
        String expectedRole = "OWNER";

        userUseCase.validateUserRole(userId, expectedRole);

        verify(userPersistencePort).validateUserRole(userId, expectedRole);
    }
}
