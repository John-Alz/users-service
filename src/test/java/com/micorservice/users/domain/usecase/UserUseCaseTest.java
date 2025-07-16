package com.micorservice.users.domain.usecase;

import com.micorservice.users.domain.model.RoleModel;
import com.micorservice.users.domain.model.UserModel;
import com.micorservice.users.domain.spi.IRolePersistencePort;
import com.micorservice.users.domain.spi.IUserPersistencePort;
import com.micorservice.users.domain.validation.UserRulesValidator;
import com.micorservice.users.infrastructure.exception.NoDataFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IRolePersistencePort rolePersistencePort;

    @Mock
    private UserRulesValidator userRulesValidator;

    @InjectMocks
    private UserUseCase userUseCase;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveUser_ShouldSaveCustomer_WhenRoleIsNull() {
        // Arrange
        UserModel user = new UserModel();
        user.setEmail("test@example.com");
        user.setPassword("123");

        RoleModel customerRole = new RoleModel(4L, "CUSTOMER", "CUSTOMER"); // ROLE_CUSTOMER = 4L
        when(userPersistencePort.getRoleUser()).thenReturn(null);
        when(rolePersistencePort.findById(4L)).thenReturn(customerRole);
        when(userPersistencePort.passwordEncode("123")).thenReturn("encodedPassword");

        // Act
        userUseCase.saveUser(user);

        // Assert
        verify(userPersistencePort).userExistWithEmail("test@example.com");
        verify(userRulesValidator).validateUserData(user);
        verify(userPersistencePort).saveUser(user);
        assertEquals("encodedPassword", user.getPassword());
        assertEquals(customerRole, user.getRole());
    }

    @Test
    void saveUser_ShouldSaveOwner_WhenRoleIsAdmin() {
        // Arrange
        UserModel user = new UserModel();
        user.setEmail("admin@example.com");
        user.setPassword("admin123");

        RoleModel ownerRole = new RoleModel(2L, "OWNER", "OWNER"); // ROLE_OWNER = 2L
        when(userPersistencePort.getRoleUser()).thenReturn("ROLE_ADMINISTRATOR"); // ROLE_ADMIN
        when(rolePersistencePort.findById(2L)).thenReturn(ownerRole);
        when(userPersistencePort.passwordEncode("admin123")).thenReturn("encoded");

        // Act
        userUseCase.saveUser(user);

        // Assert
        verify(userPersistencePort).userExistWithEmail("admin@example.com");
        verify(userRulesValidator).validateUserData(user);
        verify(userPersistencePort).saveUser(user);
        assertEquals("encoded", user.getPassword());
        assertEquals(ownerRole, user.getRole());
    }

    @Test
    void saveUser_ShouldThrowException_WhenRoleIsUnknown() {
        // Arrange
        UserModel user = new UserModel();
        when(userPersistencePort.getRoleUser()).thenReturn("INVALID_ROLE");

        // Act & Assert
        NoDataFoundException exception = assertThrows(NoDataFoundException.class, () -> userUseCase.saveUser(user));
        assertEquals("Rol no econtrado.", exception.getMessage()); // ROLE_NOT_FOUND
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void saveEmployee_ShouldSaveEmployeeCorrectly() {
        // Arrange
        Long restaurantId = 1L;
        UserModel user = new UserModel();
        user.setEmail("employee@example.com");
        user.setPassword("emp123");

        RoleModel employeeRole = new RoleModel(3L, "EMPLOYEE", "EMPLOYEE"); // ROLE_EMPLOYEE = 3L
        UserModel savedUser = new UserModel();
        savedUser.setId(100L);

        when(rolePersistencePort.findById(3L)).thenReturn(employeeRole);
        when(userPersistencePort.passwordEncode("emp123")).thenReturn("encodedEmp");
        when(userPersistencePort.saveEmployee(user, restaurantId)).thenReturn(savedUser);

        // Act
        userUseCase.saveEmployee(user, restaurantId);

        // Assert
        verify(userPersistencePort).isOwnerOfRestaurant(restaurantId);
        verify(userPersistencePort).userExistWithEmail("employee@example.com");
        verify(userRulesValidator).validateUserData(user);
        verify(userPersistencePort).createEmployee(100L, restaurantId);
        assertEquals("encodedEmp", user.getPassword());
        assertEquals(employeeRole, user.getRole());
    }

    @Test
    void validateUserRole_ShouldDelegateToPort() {
        // Act
        userUseCase.validateUserRole(10L, "CUSTOMER");

        // Assert
        verify(userPersistencePort).validateUserRole(10L, "CUSTOMER");
    }

    @Test
    void getPhoneNumberByUserId_ShouldReturnPhone() {
        // Arrange
        when(userPersistencePort.getPhoneNumberByUserId(99L)).thenReturn("+573001234567");

        // Act
        String result = userUseCase.getPhoneNumberByUserId(99L);

        // Assert
        assertEquals("+573001234567", result);
        verify(userPersistencePort).getPhoneNumberByUserId(99L);
    }
}
