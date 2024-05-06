package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.HashSet;
import java.util.Set;

@Component
public class CommandLineInit implements CommandLineRunner {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public CommandLineInit(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }




    @Override
    public void run(String... args) {

        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");

        if (roleService.getRoles().isEmpty()) {  //  проверяем, есть ли такие роли, если нет, то добавляем
            roleService.addRole(roleAdmin);
            roleService.addRole(roleUser);
        }
        Set<Role> setAdmin = new HashSet<>();   //  добавляем роли
        Set<Role> setUser = new HashSet<>();

        setAdmin.add(roleAdmin);
        setAdmin.add(roleUser);
        setUser.add(roleUser);

        User user = new User("user","userovich",(byte)1,"u","u",setUser);
        User admin = new User("admin","adminovich",(byte)2,"a","a",setAdmin);
        userService.save(admin);
        userService.save(user);


    }
}
