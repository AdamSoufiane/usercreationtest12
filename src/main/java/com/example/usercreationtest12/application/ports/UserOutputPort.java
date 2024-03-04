package com.example.usercreationtest12.application.ports;

import com.example.usercreationtest12.application.dtos.UserRegistrationResponse;
import com.example.usercreationtest12.application.exceptions.UserOutputException;

/**
 * The UserOutputPort interface defines the output port for sending the registration result back to the primary adapter, such as the UserRegistrationController.
 */
public interface UserOutputPort {

    /**
     * Sends the user registration response to the primary adapter.
     * 
     * @param response the user registration response
     * @throws UserOutputException if an error occurs while sending the response
     */
    void sendUserRegistrationResponse(UserRegistrationResponse response) throws UserOutputException;

}