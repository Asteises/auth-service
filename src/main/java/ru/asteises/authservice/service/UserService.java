package ru.asteises.authservice.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.asteises.authservice.model.ERole;
import ru.asteises.authservice.model.User;
import ru.asteises.authservice.repositoryes.RoleStorage;
import ru.asteises.authservice.repositoryes.UserStorage;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class UserService implements UserDetailsService {

    @PersistenceContext
    private EntityManager entityManager;

    private UserStorage userStorage;

    private RoleStorage roleStorage;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserStorage userStorage, RoleStorage roleStorage) {
        this.userStorage = userStorage;
        this.roleStorage = roleStorage;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userStorage.findByUsername(username).orElseThrow();
    }

    public User findUserById(Long userId) {

        return userStorage.findById(userId).orElse(new User());
    }

    public List<User> findAllUsers() {

        return userStorage.findAll();
    }

    public boolean saveUser(User user) {

        user.setRoles(Collections.singleton(roleStorage.findByName(ERole.ROLE_USER).orElseThrow()));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userStorage.save(user);

        return true;
    }

    public boolean deleteUser(Long userId) {

        if (userStorage.findById(userId).isPresent()) {
            userStorage.deleteById(userId);
            return true;
        }
        return false;
    }

    public List<User> userGetList(Long idMin) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class)
                .setParameter("paramId", idMin).getResultList();
    }
}
