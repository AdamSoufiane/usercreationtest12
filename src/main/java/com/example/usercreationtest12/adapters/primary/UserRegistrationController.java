package com.example.usercreationtest12.adapters.primary;

import com.example.usercreationtest12.application.dtos.UserRegistrationRequest;
import com.example.usercreationtest12.application.dtos.UserRegistrationResponse;
import com.example.usercreationtest12.application.exceptions.InvalidUserDataException;
import com.example.usercreationtest12.application.exceptions.PersistenceException;
import com.example.usercreationtest12.application.services.UserRegistrationApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserRegistrationController {

    private final UserRegistrationApplicationService userRegistrationApplicationService;

    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponse> registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest) {
        try {
            UserRegistrationResponse response = userRegistrationApplicationService.registerUser(userRegistrationRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (InvalidUserDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserRegistrationResponse(null, null, "Invalid user data: " + e.getMessage()));
        } catch (PersistenceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new UserRegistrationResponse(null, null, "Unable to register user at this time."));
        }
    }
}
