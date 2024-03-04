package com.example.usercreationtest12.domain.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import com.example.usercreationtest12.domain.Gender;
import com.example.usercreationtest12.domain.UserRole;
import com.example.usercreationtest12.domain.UserStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String middleName;
    private String nickname;
    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    @Column(nullable = false)
    private LocalDateTime lastLogin;
    private boolean active;
    private boolean disabled;
    private boolean locked;
    private boolean twoFactorEnabled;
    @Column(nullable = false)
    private LocalDateTime lastPasswordResetDate;
    @ElementCollection
    private Set<UserRole> roles;
    @Version
    private Long version;
    private String createdBy;
    private String updatedBy;
    private String lastModifiedBy;
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String phone;
    private String passwordResetToken;
    private String profilePictureUrl;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    private String bio;
    private String socialSecurityNumber;
    private String nationality;
    private String language;
    private String timeZone;

    public UserEntity(String name, String middleName, String nickname, String email, String password) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        // The password is assumed to be hashed in the service layer before being passed to the constructor
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        this.name = name;
        this.middleName = middleName;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public boolean updatePassword(String oldPassword, String newPassword) {
        // The old password must match the current password, and the new password must meet security requirements
        // These checks should be performed in the service layer before calling this method
        // The new password must be hashed before being passed to this method
        this.password = newPassword;
        return true;
    }

    public boolean changeEmail(String newEmail) {
        // The newEmail must be validated to match the email format specified in the file-fields description
        // This validation should be performed in the service layer before this method is invoked
        this.email = newEmail;
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        UserEntity that = (UserEntity) obj;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", middleName='" + middleName + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", active=" + active +
                ", status='" + status + '\'' +
                '}';
    }

}
