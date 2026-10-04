package com.ccpm.evaluacion.service.impl;

import com.ccpm.evaluacion.dto.PhoneDto;
import com.ccpm.evaluacion.dto.UserRequestDto;
import com.ccpm.evaluacion.dto.UserResponseDto;
import com.ccpm.evaluacion.exception.UserAlreadyExistsException;
import com.ccpm.evaluacion.model.Phone;
import com.ccpm.evaluacion.model.User;
import com.ccpm.evaluacion.repository.UserRepository;
import com.ccpm.evaluacion.security.JwtService;
import com.ccpm.evaluacion.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }


    @Override
    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return mapToDto(user);
    }

    @Override
    public UserResponseDto createUser(UserRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.email())) {
            throw new UserAlreadyExistsException("El correo ya registrado");
        }
        String token = jwtService.generateToken(requestDto.email());


        User user = new User();
        user.setName(requestDto.name());
        user.setEmail(requestDto.email());
        user.setPassword(requestDto.password()); // Recuerda encriptar si usas Spring Security
        user.setToken(token); // O tu lógica para generar el token JWT

        List<Phone> phones = requestDto.phones() != null
                ? requestDto.phones().stream().map(phoneDto -> {
            Phone phone = new Phone();
            phone.setNumber(phoneDto.number());
            phone.setCityCode(phoneDto.citycode());
            phone.setContryCode(phoneDto.contrycode()); // Ajustado al getter del DTO
            return phone;
        }).toList()
                : new ArrayList<>();

        user.setPhones(phones);

        User savedUser = userRepository.save(user);
        return mapToDto(savedUser);
    }

    @Override
    public void deleteUser(Long id) {
    }

    // funciones privadas
    private UserResponseDto mapToDto(User user) {
        List<PhoneDto> phoneDtos = user.getPhones() != null
                ? user.getPhones().stream()
                  .map(phone -> new PhoneDto(
                          phone.getNumber(),
                          phone.getCityCode(),
                          phone.getContryCode()
                  ))
                  .toList()
                : List.of();

        return new UserResponseDto(
                user.getId(),
                user.getCreated(),
                user.getModified(),
                user.getLastLogin(),
                user.getToken(),
                user.isActive(),
                user.getName(),
                user.getEmail(),
                phoneDtos
        );
    }
}
