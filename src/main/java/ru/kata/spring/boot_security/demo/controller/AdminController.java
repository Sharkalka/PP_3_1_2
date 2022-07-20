package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String allUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @GetMapping("/create")
    public String createUserForm(@ModelAttribute User user, Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        return "create";
    }

    @PostMapping("/createUser")
    public String createUser(@ModelAttribute User user,
                             @RequestParam(value = "inputRoles",required = false) Long[] inputRoles) {
        user.setRoles(roleService.getSetOfChosenRoles(inputRoles));
        userService.createUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/update/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "update";
    }

    @PutMapping("/userUpdate/{id}")
    public String updateUser(@ModelAttribute("user") User user,
                             @PathVariable Long id,
                             @RequestParam(value = "inputRoles", required = false) Long[] inputRoles) {
        User editUser = userService.getUser(id);
        editUser.setRoles(roleService.getSetOfChosenRoles(inputRoles));
        userService.updateUser(editUser);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
