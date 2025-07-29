package com.micorservice.users.infrastructure.utils;

public class InfrastructureConstants {

    private InfrastructureConstants () {}

    public static final String USER_EXITS_WITH_EMAIL = "Ya existe un usuario registrado con ese email en nuestro sistema.";
    public static final String USER_NOT_FOUND = "No se econtro usuario con ese id.";
    public static final String ROLE_NOT_FOUND = "Rol no encontrado.";

    //PreAuthorize
    public static final String HAS_ROLE_OWNER = "hasRole('OWNER')";

}
