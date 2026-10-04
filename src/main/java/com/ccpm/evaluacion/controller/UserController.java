package com.ccpm.evaluacion.controller;

import com.ccpm.evaluacion.dto.UserRequestDto;
import com.ccpm.evaluacion.dto.UserResponseDto;
import com.ccpm.evaluacion.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Usuarios", description = "Endpoints para la gestión de usuarios")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Obtener todos los usuarios")
    @GetMapping()
    public ResponseEntity<List<UserResponseDto>> getUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @Operation(summary = "Crear un nuevo usuario", description = "Registra un usuario, valida correo único y genera un token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o correo ya registrado")
    })
    @PostMapping()
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto requestDto) {
        UserResponseDto savedUser = userService.createUser(requestDto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
}
