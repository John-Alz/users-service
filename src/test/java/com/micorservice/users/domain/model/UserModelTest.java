package com.micorservice.users.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserModelTest {

    @Test
    void testConstructorAndGettersSetters() {
        // Arrange
        Long id = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String documentNumber = "123456789";
        String phoneNumber = "+573001112233";
        LocalDate birthDate = LocalDate.of(2000, 1, 1);
        String email = "john.doe@example.com";
        String password = "securePassword";
        RoleModel role = new RoleModel(1L, "ADMIN", "Administrador");

        // Act
        UserModel user = new UserModel();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDocumentNumber(documentNumber);
        user.setPhoneNumber(phoneNumber);
        user.setBirthDate(birthDate);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);

        // Assert
        assertEquals(id, user.getId());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(documentNumber, user.getDocumentNumber());
        assertEquals(phoneNumber, user.getPhoneNumber());
        assertEquals(birthDate, user.getBirthDate());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(role, user.getRole());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        Long id = 2L;
        String firstName = "Alice";
        String lastName = "Smith";
        String documentNumber = "987654321";
        String phoneNumber = "+573009998877";
        LocalDate birthDate = LocalDate.of(1995, 5, 10);
        String email = "alice.smith@example.com";
        String password = "anotherSecurePassword";
        RoleModel role = new RoleModel(2L, "USER", "Usuario estándar");

        // Act
        UserModel user = new UserModel(id, firstName, lastName, documentNumber, phoneNumber, birthDate, email, password, role);

        // Assert
        assertEquals(id, user.getId());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(documentNumber, user.getDocumentNumber());
        assertEquals(phoneNumber, user.getPhoneNumber());
        assertEquals(birthDate, user.getBirthDate());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(role, user.getRole());
    }
}
