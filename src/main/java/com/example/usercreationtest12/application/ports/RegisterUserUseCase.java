package com.example.usercreationtest12.application.ports;

import com.example.usercreationtest12.application.dtos.UserRegistrationRequest;
import com.example.usercreationtest12.application.dtos.UserRegistrationResponse;
import com.example.usercreationtest12.application.exceptions.UserRegistrationException;

/**
 * The RegisterUserUseCase interface defines the input port for the user registration process,
 * declaring methods for registering a new user.
 */
public interface RegisterUserUseCase {

    /**
     * Registers a new user into the system and returns the registration response.
     *
     * @param request the user registration request
     * @return the user registration response
     * @throws UserRegistrationException if there is an issue during the registration process
     */
    UserRegistrationResponse registerUser(UserRegistrationRequest request) throws UserRegistrationException;

}