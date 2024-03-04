package com.example.usercreationtest12.application.dtos;

import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * The UserRegistrationResponse class is a data transfer object that represents the structure of the response sent back to clients after a successful user registration, containing fields such as userId, name, and email.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;
    private String name;
    private String email;

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, email);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UserRegistrationResponse that = (UserRegistrationResponse) obj;
        return Objects.equals(userId, that.userId) &&
               Objects.equals(name, that.name) &&
               Objects.equals(email, that.email);
    }

    @Override
    public String toString() {
        return "UserRegistrationResponse{" +
               "userId=" + userId +
               ", name='" + name + '\'' +
               ", email='" + email + '\'' +
               '}';
    }
}
