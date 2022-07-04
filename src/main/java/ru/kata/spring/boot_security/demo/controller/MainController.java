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
public class MainController {

    private final UserService userService;
    private final RoleService roleService;

    public MainController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String allUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @GetMapping("/admin/create")
    public String createUserForm(@ModelAttribute User user) {
        return "create";
    }

    @PostMapping("/admin/createUser")
    public String createUser(@ModelAttribute User user,
                             @RequestParam(value = "isAdmin", required = false) boolean isAdmin) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRoleByName("ROLE_USER"));
        if (isAdmin) {
            roles.add(roleService.getRoleByName("ROLE_ADMIN"));
        }
        user.setRoles(roles);
        userService.createUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/update/{id}")
    public String editUser(@PathVariable long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "update";
    }

    @PostMapping("/admin/updateUser")
    public String updateUser(@ModelAttribute User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String userPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("user", userService.findByUserName(userDetails.getUsername()));
        return "user";
    }
}
