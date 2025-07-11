package com.micorservice.users.application.dto.response;

public record LoginUserResponseDto(String email, String message, String token) {
}
