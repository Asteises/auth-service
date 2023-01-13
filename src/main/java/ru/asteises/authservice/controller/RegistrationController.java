package ru.asteises.authservice.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.asteises.authservice.model.User;
import ru.asteises.authservice.service.UserService;

@RestController("/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;

    //TODO Model - что за зверь?
    @GetMapping()
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    //TODO ModelAttribute, BindingResult - что за зверь?
    @PostMapping()
    public String addUser(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "registration";
        }
        if (!userService.saveUser(userForm)){
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "registration";
        }

        return "redirect:/";
    }
}
