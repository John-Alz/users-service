package com.micorservice.users.infrastructure.input.rest;

import com.micorservice.users.application.dto.response.PhoneResponseDto;
import com.micorservice.users.application.dto.request.UserRequestDto;
import com.micorservice.users.application.dto.response.SaveMessageResponse;
import com.micorservice.users.application.handler.IUserHandler;
import com.micorservice.users.infrastructure.utils.InfrastructureConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Usuarios", description = "Endpoints de gestion de usuarios.")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {

    private final IUserHandler userHandler;


    @Operation(summary = "Crear un nuevo usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Error de validacion", content = @Content),
    })
    @PostMapping()
    public ResponseEntity<SaveMessageResponse> saveUserOwner(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userHandler.saveUser(userRequestDto));
    }

    @Operation(summary = "Crear un nuevo usuario con rol empelado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "empleado creado.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Error de validacion", content = @Content),
    })

    @PostMapping("/employee")
    @PreAuthorize(InfrastructureConstants.HAS_ROLE_OWNER)
    public ResponseEntity<SaveMessageResponse> saveUserEmployee(@RequestBody UserRequestDto userRequestDto, @RequestParam Long restaurantId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userHandler.saveEmployee(userRequestDto, restaurantId));
    }

    
    @GetMapping("/{userId}")
    public ResponseEntity<Void> validateUserRole(@PathVariable Long userId, @RequestParam String role) {
        userHandler.validateUserRole(userId, role);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/phone")
    public ResponseEntity<PhoneResponseDto> getPhoneByUserId(@PathVariable Long userId) {
        PhoneResponseDto phoneNumber = userHandler.getPhoneNumberByUserId(userId);
        return ResponseEntity.ok(phoneNumber);
    }


}
