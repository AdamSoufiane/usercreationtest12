package com.example.usercreationtest12.application.services;

import com.example.usercreationtest12.application.dtos.UserRegistrationRequest;
import com.example.usercreationtest12.application.dtos.UserRegistrationResponse;
import com.example.usercreationtest12.application.exceptions.InvalidUserDataException;
import com.example.usercreationtest12.application.exceptions.PersistenceException;
import com.example.usercreationtest12.application.ports.RegisterUserUseCase;
import com.example.usercreationtest12.application.ports.UserOutputPort;
import com.example.usercreationtest12.application.ports.UserRepositoryPort;
import com.example.usercreationtest12.domain.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRegistrationApplicationService implements RegisterUserUseCase {

    private final UserRepositoryPort userRepository;
    private final UserOutputPort userOutputPort;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserRegistrationResponse registerUser(UserRegistrationRequest request) throws InvalidUserDataException, PersistenceException {
        if (!request.validate()) {
            throw new InvalidUserDataException("User registration data is invalid.");
        }

        try {
            Optional<UserEntity> existingUser = userRepository.findByEmail(request.getEmail());
            if (existingUser.isPresent()) {
                throw new InvalidUserDataException("A user with the given email already exists.");
            }

            validatePasswordStrength(request.getPassword());
            UserEntity userEntity = mapToUserEntity(request);
            userEntity = userRepository.createUser(userEntity);
            UserRegistrationResponse response = new UserRegistrationResponse(userEntity.getId(), userEntity.getName(), userEntity.getEmail());
            userOutputPort.sendUserRegistrationResponse(response);
            return response;
        } catch (InvalidUserDataException e) {
            log.error("Invalid user data: ", e);
            throw e;
        } catch (DuplicateKeyException e) {
            log.error("Duplicate key error: ", e);
            throw new PersistenceException("User with the same identifier already exists.", e);
        } catch (Exception e) {
            log.error("Error during user registration: ", e);
            throw new PersistenceException("An error occurred while persisting user data.", e);
        }
    }

    private void validatePasswordStrength(String password) throws InvalidUserDataException {
        if (password == null || password.trim().isEmpty() || !password.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}")) {
            throw new InvalidUserDataException("Password does not meet the required criteria.");
        }
    }

    private UserEntity mapToUserEntity(UserRegistrationRequest request) {
        String encryptedPassword = passwordEncoder.encode(request.getPassword());
        return new UserEntity(request.getName(), null, null, request.getEmail(), encryptedPassword);
    }
}
