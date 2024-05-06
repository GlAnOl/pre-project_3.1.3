package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;

import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;


@Controller
public class AdminController {

    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;
    private final UserValidator userValidator;

    @Autowired
    public AdminController(UserServiceImpl userService, RoleServiceImpl roleService, UserValidator userValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.userValidator = userValidator;
    }


    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("user", userService.findAll());
        return "admin/admin";
    }




        @GetMapping("/admin/edit")
    public String editUser(Model model, @RequestParam (value="id") int id){
            model.addAttribute("user", userService.findOne(id));
            model.addAttribute("allRoles", roleService.getRoles());
            return "admin/edit";
    }

    @PatchMapping("/admin/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return "admin/edit";
        }

        userService.update(user);
        return "redirect:/";
    }


    @DeleteMapping("/admin/delete")
    public String delete (@RequestParam (value="id", required = false) int id){
        userService.delete(id);
        return "redirect:/admin";
    }


    @GetMapping("/admin/new")
    public String showPageCreatingUser(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getRoles());
        return "admin/new";
    }

    @PostMapping("/admin/user") //создаем нового пользователя
    public String createNewUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/new";
        }
        userService.save(user);
        return "redirect:/admin";
    }
}
