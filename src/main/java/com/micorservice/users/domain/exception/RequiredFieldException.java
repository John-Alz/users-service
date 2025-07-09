package com.micorservice.users.domain.exception;

import lombok.RequiredArgsConstructor;

public class RequiredFieldException extends RuntimeException {
    public RequiredFieldException(String message) {
        super(message);
    }
}
