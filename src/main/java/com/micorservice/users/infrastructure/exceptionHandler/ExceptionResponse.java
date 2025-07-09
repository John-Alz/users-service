package com.micorservice.users.infrastructure.exceptionHandler;

import java.time.LocalDateTime;

public record ExceptionResponse(String message, LocalDateTime timeStamp) {
}
