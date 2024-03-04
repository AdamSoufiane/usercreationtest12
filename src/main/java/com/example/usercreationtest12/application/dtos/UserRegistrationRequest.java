package com.example.usercreationtest12.application.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * The UserRegistrationRequest class is a data transfer object that carries user data from the external layers to the application layer,
 * containing fields such as name, email, and password.
 */
@Getter
@Setter
public class UserRegistrationRequest {

    @NotNull(message = "Name cannot be null.")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters long.")
    private String name;

    @NotNull(message = "Email cannot be null.")
    @Email(message = "Email must be a valid email format.")
    private String email;

    @NotNull(message = "Password cannot be null.")
    @Size(min = 8, max = 30, message = "Password must be between 8 and 30 characters long.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}", message = "Password must contain at least one digit, one lower case, one upper case, and one special character.")
    private String password;

    @Value("${security.password-hash-strength:10}")
    private int passwordHashStrength;

    public UserRegistrationRequest() {
        // Default constructor for serialization frameworks.
    }

    public UserRegistrationRequest(@JsonProperty("name") String name, @JsonProperty("email") String email, @JsonProperty("password") String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String hashPassword() {
        return BCrypt.hashpw(password, BCrypt.gensalt(passwordHashStrength));
    }

    @Override
    public String toString() {
        return "UserRegistrationRequest{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UserRegistrationRequest that = (UserRegistrationRequest) obj;
        return Objects.equals(name, that.name) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email);
    }
}
