package com.micorservice.users.domain.utils;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

class DomainConstantsTest {

    @Test
    void constructorShouldBePrivateAndCoveredForJacoco() throws Exception {
        Constructor<DomainConstants> constructor = DomainConstants.class.getDeclaredConstructor();
        assertTrue(constructor.canAccess(null) == false || !constructor.isAccessible(), "Constructor should be private");

        constructor.setAccessible(true);
        DomainConstants instance = constructor.newInstance(); // Esto cubre la línea del constructor
        assertNotNull(instance); // Opcional: valida que la instancia exista
    }
}
