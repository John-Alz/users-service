package com.micorservice.users.infrastructure.exceptionHandler;

import com.micorservice.users.domain.exception.InvalidFieldException;
import com.micorservice.users.domain.exception.RequiredFieldException;
import com.micorservice.users.infrastructure.exception.AlreadyExistsException;
import com.micorservice.users.infrastructure.exception.InvalidUserRoleException;
import com.micorservice.users.infrastructure.exception.NoDataFoundException;
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

}
