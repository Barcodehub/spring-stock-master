package com.barcodehub.eccomerce_spring.controller;


import com.barcodehub.eccomerce_spring.dto.UserRequestDTO;
import com.barcodehub.eccomerce_spring.dto.UserResponseDTO;
import com.barcodehub.eccomerce_spring.dto.errors.SuccessResponseDTO;
import com.barcodehub.eccomerce_spring.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<SuccessResponseDTO<UserResponseDTO>> createUser(@Valid @RequestBody UserRequestDTO userRequest) {
        SuccessResponseDTO<UserResponseDTO> response = userService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    




    }








