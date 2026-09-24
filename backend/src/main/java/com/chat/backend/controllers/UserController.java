package com.chat.backend.controllers;


import com.chat.backend.entities.User;
import com.chat.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return ResponseEntity.ok("User Registered Successfully");
    }

    @GetMapping("/get-profile")
    public User getProfile() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User user =
                (User) authentication.getPrincipal();

        System.out.println(user.getId());
        System.out.println(user.getUsername());
        System.out.println(user.getRole());

        return user;
    }
}
