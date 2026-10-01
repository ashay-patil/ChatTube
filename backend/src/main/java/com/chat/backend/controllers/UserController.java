package com.chat.backend.controllers;


import com.chat.backend.entities.User;
import com.chat.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
        try {
            userService.save(user);
        }
        catch(Exception e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT) // HTTP 409
                    .body("An account with this email or username already exists.");
        }
        return ResponseEntity.ok("User Registered Successfully");
    }

    @GetMapping("/get-profile")
    public User getProfile(@AuthenticationPrincipal User user) {
        return user;
    }
}
