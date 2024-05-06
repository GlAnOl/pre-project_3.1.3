package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public void register(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());//шифруем пароль, который прислали с формы
        user.setPassword(encodedPassword);//передаем зашифрованный пароль



        Role role = new Role("ROLE_USER");
        roleService.addRole(role);
        Set<Role> setUser = new HashSet<>();
        setUser.add(role);
        user.setRole(setUser);

        userRepository.save(user);
    }

    public User findByUsername(String username) {
//        Optional<User> user = userRepository.findByUsername(username);
//        return user.orElse(null);

        return userRepository.findByName(username);
    }


}
