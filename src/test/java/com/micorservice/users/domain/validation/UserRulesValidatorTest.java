package com.micorservice.users.domain.validation;

import com.micorservice.users.domain.exception.InvalidFieldException;
import com.micorservice.users.domain.exception.RequiredFieldException;
import com.micorservice.users.domain.model.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserRulesValidatorTest {

    private UserRulesValidator validator;

    @BeforeEach
    void setUp() {
        validator = new UserRulesValidator();
    }

    @Test
    void validateEmail_shouldThrowRequiredFieldException_whenNull() {
        RequiredFieldException ex = assertThrows(RequiredFieldException.class, () -> validator.validateEmail(null));
        assertEquals("El email es requerido.", ex.getMessage());
    }

    @Test
    void validateEmail_shouldThrowInvalidFieldException_whenInvalid() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> validator.validateEmail("email-invalido"));
        assertEquals("El email tiene un formato no valido ej: (example@exmaple.com).", ex.getMessage());
    }

    @Test
    void validateEmail_shouldPass_whenValid() {
        assertDoesNotThrow(() -> validator.validateEmail("john@gmail.com"));
    }

    @Test
    void validatePhoneNumber_shouldThrow_whenNull() {
        RequiredFieldException ex = assertThrows(RequiredFieldException.class, () -> validator.validatePhoneNumber(null));
        assertEquals("El numero de telefono es requerido.", ex.getMessage());
    }

    @Test
    void validateIdentityNumber_shouldThrow_whenNull() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> validator.validatePhoneNumber("telefonoinvalido"));
        assertEquals("El numero de telefono excede los 13 caracteres ej: (+573152473024).", ex.getMessage());
    }

    @Test
    void validateIdentityNumber_shouldThrow_whenInvalid() {
        assertDoesNotThrow(() -> validator.validatePhoneNumber("+573143678923"));
    }

    @Test
    void validateIdentityNumber_shouldPass_whenValid() {
        RequiredFieldException ex = assertThrows(RequiredFieldException.class, () -> validator.validateIdentityNumber(null));
        assertEquals("El numero de identificacion es requerido.", ex.getMessage());
    }

    @Test
    void validatePhoneNumber_shouldThrow_whenInvalid() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> validator.validateIdentityNumber("numero de identificacion invalido"));
        assertEquals("El umero de identificacion solo puede ser numerico ej: (1003678345)", ex.getMessage());
    }

    @Test
    void validatePhoneNumber_shouldPass_whenValid() {
        assertDoesNotThrow(() -> validator.validateIdentityNumber("20785235"));
    }

    @Test
    void validateAge_shouldThrow_whenNull() {
        RequiredFieldException ex = assertThrows(RequiredFieldException.class, () -> validator.validateAge(null));
        assertEquals("La fecha de nacimiento es requerida.", ex.getMessage());
    }

    @Test
    void vvalidateAge_shouldThrow_whenInvalid() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> validator.validateAge(LocalDate.of(2010, 07, 25)));
        assertEquals("No se puede crear la cuenta a un menor de edad.", ex.getMessage());
    }

    @Test
    void vvalidateAge_shouldPass_whenValid() {
        assertDoesNotThrow(() -> validator.validateAge(LocalDate.of(2000, 02,23)));
    }

    @Test
    void validateUserData_shouldPass_whenValidUser() {
        UserModel user = new UserModel();
        user.setEmail("john@example.com");
        user.setPhoneNumber("+573001234567");
        user.setDocumentNumber("123456789");
        user.setBirthDate(LocalDate.now().minusYears(25));
        assertDoesNotThrow(() -> validator.validateUserData(user));
    }


}