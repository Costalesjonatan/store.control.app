package com.stock.control.app.rest.controller;

import com.stock.control.app.domain.service.UserService;
import com.stock.control.app.rest.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final UserDetailsService userDetailsService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody UserRequest user) {
        try {
            userService.createUser(user);
            return ResponseEntity.ok("Sign up!");
        } catch (Exception exception){
            return ResponseEntity.badRequest().body("Cannot create given user.");
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody UserRequest user) {
        try {
            userDetailsService.loadUserByUsername(user.getUsername());
            return ResponseEntity.ok("Sign in!");
        } catch (Exception exception){
            return ResponseEntity.badRequest().body("Bad credentials!");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody UserRequest user) {
        try {
            return ResponseEntity.ok("Logout!");
        } catch (Exception exception){
            return ResponseEntity.badRequest().body("Something went wrong.");
        }
    }
}
