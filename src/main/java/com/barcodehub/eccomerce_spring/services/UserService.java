package com.barcodehub.eccomerce_spring.services;

import com.barcodehub.eccomerce_spring.dto.UserRequestDTO;
import com.barcodehub.eccomerce_spring.dto.UserResponseDTO;
import com.barcodehub.eccomerce_spring.dto.errors.SuccessResponseDTO;
import com.barcodehub.eccomerce_spring.exception.EmailAlreadyExistsException;
import com.barcodehub.eccomerce_spring.models.Role;
import com.barcodehub.eccomerce_spring.models.User;
import com.barcodehub.eccomerce_spring.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    @Transactional
    public SuccessResponseDTO<UserResponseDTO> createUser(@Valid UserRequestDTO userRequest) {
        // Verificar si el email ya existe
        String email = userRequest.email().trim().toLowerCase();
        // Verificar email existente
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("El email ya está registrado", "email");
        }

        // Crear nuevo usuario
        User user = new User();
        user.setName(userRequest.name());
        user.setEmail(userRequest.email());
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        user.setRole(Role.SOCIO);
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        UserResponseDTO response = mapToUserResponseDTO(savedUser);

        // Devolver el wrapper completo
        return new SuccessResponseDTO<>(response);


    }

    // Otros métodos del servicio...

    private UserResponseDTO mapToUserResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt()
        );
    }






}