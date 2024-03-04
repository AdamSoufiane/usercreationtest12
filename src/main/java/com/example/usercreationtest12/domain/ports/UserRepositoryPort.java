package com.example.usercreationtest12.domain.ports;

import com.example.usercreationtest12.domain.entities.UserEntity;
import com.example.usercreationtest12.domain.exceptions.SpecificEntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.OptimisticLockingFailureException;

/**
 * The UserRepositoryPort interface declares the necessary persistence operations required for the user entity such as saving a user.
 */
public interface UserRepositoryPort {

    /**
     * Creates a new user entity in the database.
     * @param userEntity the user entity to create
     * @return the created user entity
     * @throws DuplicateKeyException if a user with the same identifier already exists
     */
    UserEntity createUser(UserEntity userEntity) throws DuplicateKeyException;

    /**
     * Updates an existing user entity in the database with the provided user entity's details.
     * @param userEntity the user entity to update
     * @return the updated user entity
     * @throws OptimisticLockingFailureException if a version conflict occurs, indicating a concurrent update attempt
     */
    UserEntity saveUser(UserEntity userEntity) throws OptimisticLockingFailureException;

    /**
     * Retrieves a user entity by its unique identifier.
     * @param id the unique identifier of the user
     * @return an Optional containing the user entity if found, or an empty Optional if no user is found with the provided id
     */
    Optional<UserEntity> findUserById(Long id);

    /**
     * Deletes the user entity with the specified unique identifier from the database.
     * @param id the unique identifier of the user to delete
     * @throws EmptyResultDataAccessException if no user is found with the provided id
     */
    void deleteUser(Long id) throws EmptyResultDataAccessException;

    /**
     * Updates an existing user entity in the database with the provided user entity's details.
     * @param userEntity the user entity to update
     * @return the updated user entity
     * @throws SpecificEntityNotFoundException if the user entity does not exist
     */
    UserEntity updateUser(UserEntity userEntity) throws SpecificEntityNotFoundException;

    /**
     * Retrieves all user entities from the database.
     * @return a list of user entities
     */
    List<UserEntity> findAllUsers();

    /**
     * Retrieves a user entity by its email.
     * @param email the email of the user to find
     * @return an Optional containing the user entity if found, or an empty Optional if no user with the provided email exists
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * Counts the total number of user entities in the database.
     * @return the count as a long
     */
    long countUsers();

}
