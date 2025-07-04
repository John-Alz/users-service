package com.micorservice.users.application.dto.response;

import java.time.LocalDateTime;

public record SaveMessageResponse(String message, LocalDateTime time) {
}
