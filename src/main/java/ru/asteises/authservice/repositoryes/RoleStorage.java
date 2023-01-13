package ru.asteises.authservice.repositoryes;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.asteises.authservice.model.ERole;
import ru.asteises.authservice.model.Role;

import java.util.Optional;

public interface RoleStorage extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(ERole name);
}
