package ru.asteises.authservice.repositoryes;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.asteises.authservice.model.User;

import java.util.Optional;

public interface UserStorage extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findById(Long id);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
