package com.micorservice.users.application.dto.request;

import java.time.LocalDate;

public record UserRequestDto(
         String firstName,
         String lastName,
         String documentNumber,
         String phoneNumber,
         LocalDate birthDate,
         String email,
         String password
) {
}
