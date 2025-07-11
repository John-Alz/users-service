package com.micorservice.users.infrastructure.dto;

import java.time.LocalDateTime;

public record ErrorResponse(
        String message,
        String timeStamp
) {
}
