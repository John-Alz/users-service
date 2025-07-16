package com.micorservice.users.domain.utils;

public class DomainConstants {

    private DomainConstants(){}

    //Regex
    public static final String REGEX_EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static final String REGEX_PHONE_NUMBER = "^\\+?[0-9]{1,13}$";
    public static final String REGEX_IDENTITY_NUMBER = "^[0-9]+$";

    public static final int MIN_AGE = 18;

    public static final String REQUIRED_FIRSTNAME = "El nombre es requerido.";
    public static final String REQUIRED_LASTNAME = "El apellido es requerido.";
    public static final String REQUIRED_PASSWORD = "La contraseña es requerida.";

    public static final String REQUIRED_EMAIL = "El email es requerido.";
    public static final String INVALID_EMAIL = "El email tiene un formato no valido ej: (example@exmaple.com).";

    public static final String REQUIRED_PHONE_NUMBER = "El numero de telefono es requerido.";
    public static final String INVALID_PHONE_NUMBER = "El numero de telefono excede los 13 caracteres ej: (+573152473024).";

    public static final String REQUIRED_IDENTITY_NUMBER = "El numero de identificacion es requerido.";
    public static final String INVALID_IDENTITY_NUMBER  = "El umero de identificacion solo puede ser numerico ej: (1003678345)";

    public static final String REQUIRED_BIRTHDAY = "La fecha de nacimiento es requerida.";
    public static final String INVALID_BIRTHDAY   = "No se puede crear la cuenta a un menor de edad.";


    // Role constants

    public static final Long ROLE_CUSTOMER = 4L;
    public static final Long ROLE_OWNER = 2L;
    public static final Long ROLE_EMPLOYEE = 3L;

    public static final String ROLE_ADMIN = "ROLE_ADMINISTRATOR";
    public static final String ROLE_NOT_FOUND= "Rol no econtrado.";
}
