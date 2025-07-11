package com.micorservice.users.infrastructure.exceptionHandler;

import com.micorservice.users.domain.exception.InvalidFieldException;
import com.micorservice.users.domain.exception.PasswordInvalidException;
import com.micorservice.users.domain.exception.RequiredFieldException;
import com.micorservice.users.infrastructure.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNoDataFoundException(NoDataFoundException e) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(e.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(RequiredFieldException.class)
    public ResponseEntity<ExceptionResponse> handleRequiredFieldException(RequiredFieldException e) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(e.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(InvalidFieldException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidFieldException(InvalidFieldException e) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(e.getMessage(), LocalDateTime.now()));
    }


    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleAlreadyExistsException(AlreadyExistsException e) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(e.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(InvalidUserRoleException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidUserRoleException(InvalidUserRoleException e) {
        return ResponseEntity.badRequest().body(new ExceptionResponse("EL usuario no cuenta con el rol necesario.", LocalDateTime.now()));
    }

    @ExceptionHandler(UserNotFoundByEmailException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotFoundByEmailException(UserNotFoundByEmailException e) {
        return ResponseEntity.badRequest().body(new ExceptionResponse("Este email no corresponde a ningun usuario.", LocalDateTime.now()));
    }

    @ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity<ExceptionResponse> handlePasswordInvalidException(PasswordInvalidException e) {
        return ResponseEntity.badRequest().body(new ExceptionResponse("Contraseña incorrecta.", LocalDateTime.now()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionResponse> handleUnauthorizedException(UnauthorizedException e) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(e.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(e.getMessage(), LocalDateTime.now()));
    }



}
