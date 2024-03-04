package com.example.usercreationtest12.infrastructure.repositories;

import com.example.usercreationtest12.domain.entities.UserEntity;
import com.example.usercreationtest12.domain.ports.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserJpaRepository userRepository;

    @Override
    public UserEntity createUser(UserEntity userEntity) throws DuplicateKeyException {
        if (userRepository.existsByEmailIgnoreCase(userEntity.getEmail())) {
            throw new DuplicateKeyException("User with email '" + userEntity.getEmail() + "' already exists.");
        }
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity saveUser(UserEntity userEntity) throws OptimisticLockingFailureException {
        if (userRepository.existsById(userEntity.getId())) {
            throw new OptimisticLockingFailureException("User with id '" + userEntity.getId() + "' already exists.");
        }
        return userRepository.save(userEntity);
    }

    @Override
    public Optional<UserEntity> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EmptyResultDataAccessException("No user found with the provided id: " + id, 1);
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserEntity updateUser(UserEntity userEntity) throws OptimisticLockingFailureException {
        UserEntity existingUser = userRepository.findById(userEntity.getId())
                .orElseThrow(() -> new EmptyResultDataAccessException("No user found with the provided id: " + userEntity.getId(), 1));
        if (!existingUser.getVersion().equals(userEntity.getVersion())) {
            throw new OptimisticLockingFailureException("Version conflict for user with id '" + userEntity.getId() + "', indicating a concurrent update attempt.");
        }
        return userRepository.save(userEntity);
    }

    @Override
    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    @Override
    public long countUsers() {
        return userRepository.count();
    }
}

interface UserJpaRepository extends org.springframework.data.jpa.repository.JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
    boolean existsById(Long id);
}
