package ru.asteises.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.asteises.authservice.service.UserService;

@RestController("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.findAllUsers());
        return "admin";
    }

    @PostMapping()
    public String deleteUser(@RequestParam(required = true, defaultValue = "" ) Long userId,
                              @RequestParam(required = true, defaultValue = "" ) String action,
                              Model model) {
        if (action.equals("delete")){
            userService.deleteUser(userId);
        }
        return "redirect:/admin";
    }

    @GetMapping("/admin/gt/{userId}")
    public String getUser(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("allUsers", userService.userGetList(userId));
        return "admin";
    }
}
