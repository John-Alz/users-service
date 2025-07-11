package com.micorservice.users.domain.usecase;

import com.micorservice.users.domain.exception.PasswordInvalidException;
import com.micorservice.users.domain.model.UserModel;
import com.micorservice.users.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @InjectMocks
    private AuthUseCase authUseCase;

    private UserModel userModel;

    @BeforeEach
    void setUp() {
        userModel = new UserModel();
        userModel.setEmail("john@example.com");
        userModel.setPassword("encoded-password");
    }

    @Test
    void loginUser_ShouldReturnUser_WhenPasswordIsCorrect() {
        // Arrange
        when(userPersistencePort.getUserByEmail("john@example.com")).thenReturn(userModel);
        when(userPersistencePort.passwordDecode("raw-password", "encoded-password")).thenReturn(true);

        // Act
        UserModel result = authUseCase.loginUser("john@example.com", "raw-password");

        // Assert
        assertNotNull(result);
        assertEquals("john@example.com", result.getEmail());
        verify(userPersistencePort).getUserByEmail("john@example.com");
        verify(userPersistencePort).passwordDecode("raw-password", "encoded-password");
    }

    @Test
    void loginUser_ShouldThrowPasswordInvalidException_WhenPasswordIsInvalid() {
        // Arrange
        when(userPersistencePort.getUserByEmail("john@example.com")).thenReturn(userModel);
        when(userPersistencePort.passwordDecode("wrong-password", "encoded-password")).thenReturn(false);

        // Act & Assert
        assertThrows(PasswordInvalidException.class, () ->
                authUseCase.loginUser("john@example.com", "wrong-password"));

        verify(userPersistencePort).getUserByEmail("john@example.com");
        verify(userPersistencePort).passwordDecode("wrong-password", "encoded-password");
    }
}
