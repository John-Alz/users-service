package com.micorservice.users.infrastructure.input.rest;

import com.micorservice.users.application.dto.request.UserRequestDto;
import com.micorservice.users.application.dto.response.SaveMessageResponse;
import com.micorservice.users.application.handler.IUserHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Usuarios", description = "Endpoints de gestion de usuarios.")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {

    private final IUserHandler userHandler;


    @Operation(summary = "Crear un nuevo usuario con rol propietario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Propietario creado.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Error de validacion", content = @Content),
    })
    @PostMapping()
    public ResponseEntity<SaveMessageResponse> saveUserOwner(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userHandler.saveUser(userRequestDto));
    }

    
    @GetMapping("/{userId}")
    public ResponseEntity<Void> validateUserRole(@PathVariable Long userId, @RequestParam String role) {
        userHandler.validateUserRole(userId, role);
        return ResponseEntity.ok().build();
    }


}
