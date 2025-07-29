package com.micorservice.users.infrastructure.dto;

public record ErrorResponse(
        String message,
        String timeStamp
) {
}
