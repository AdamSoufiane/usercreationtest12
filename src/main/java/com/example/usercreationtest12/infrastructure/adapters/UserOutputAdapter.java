package com.example.usercreationtest12.infrastructure.adapters;

import com.example.usercreationtest12.application.dtos.UserRegistrationResponse;
import com.example.usercreationtest12.application.ports.UserOutputPort;
import com.example.usercreationtest12.infrastructure.exceptions.ExternalSystemCommunicationException;
import org.slf4j.Logger;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * The UserOutputAdapter class implements the UserOutputPort interface to adapt the output from the application layer to the infrastructure layer,
 * such as sending email notifications or logging.
 */
public class UserOutputAdapter implements UserOutputPort {

    private final Logger logger;
    private final String externalSystemIdentifier;

    /**
     * Constructor to initialize the UserOutputAdapter with the required logger and external system identifier.
     * @param logger The logger instance.
     * @param externalSystemIdentifier The identifier for the external system.
     */
    public UserOutputAdapter(Logger logger, String externalSystemIdentifier) {
        this.logger = logger;
        this.externalSystemIdentifier = externalSystemIdentifier;
    }

    /**
     * Sends the user registration result to an external system, such as an email service or a logging system.
     * Metadata is optional and can be passed as an empty map if not used.
     * @param response The user registration response to send.
     * @param metadata Additional metadata that might be required by the external system.
     */
    @Override
    public void sendUserRegistrationResult(UserRegistrationResponse response, Map<String, Object> metadata) {
        Objects.requireNonNull(metadata, "Metadata cannot be null");
        try {
            // Logic to send the response to the external system using the externalSystemIdentifier
            // This is a placeholder for actual implementation, which may involve an API call, a message queue, etc.
            // For example, this could be a call to an EmailService to send an email notification
            logger.info("User registration result sent to external system {}: {}", externalSystemIdentifier, response);
        } catch (ExternalSystemCommunicationException e) {
            logger.error("External system communication exception", e);
            throw e;
        } catch (Exception e) {
            logger.error("Failed to send user registration result to external system", e);
            throw new ExternalSystemCommunicationException("Failed to communicate with external system", e);
        }
    }

    /**
     * Convenience method to send the user registration result without metadata.
     * @param response The user registration response to send.
     */
    public void sendUserRegistrationResult(UserRegistrationResponse response) {
        sendUserRegistrationResult(response, Collections.emptyMap());
    }

}
