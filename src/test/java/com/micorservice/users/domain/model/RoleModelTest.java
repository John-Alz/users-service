package com.micorservice.users.domain.model;

import org.junit.jupiter.api.Test;

import javax.management.relation.Role;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RoleModelTest {

    @Test
    void testConstructorAndGettersSetters() {
        // Arrange
        Long id = 1L;
        String name = "ADMINISTRATOR";
        String description = "User admin";

        // Act
        RoleModel role = new RoleModel();
        role.setId(id);
        role.setName(name);
        role.setDescription(description);

        // Assert
        assertEquals(id, role.getId());
        assertEquals(name, role.getName());
        assertEquals(description, role.getDescription());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        Long id = 1L;
        String name = "ADMINISTRATOR";
        String description = "User admin";

        // Act
        RoleModel role = new RoleModel(id, name, description);

        // Assert
        assertEquals(id, role.getId());
        assertEquals(name, role.getName());
        assertEquals(description, role.getDescription());
    }

}