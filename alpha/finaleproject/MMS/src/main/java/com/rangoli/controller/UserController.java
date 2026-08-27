package com.rangoli.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.rangoli.dto.*;
import com.rangoli.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public ApiResponse register(@RequestBody RegisterRequest req) {
        return service.register(req);
    }

    @PostMapping("/login")
    public ApiResponse login(@RequestBody LoginRequest req) {
        return service.login(req);
    }

    @GetMapping("/profile")
    public ApiResponse profile(Authentication authentication) {
        return service.getProfile(authentication.getName());
    }
    @PostMapping("/logout")
    public ApiResponse logout() {
        return service.logout();
    }


    @PostMapping("/forgot-password")
    public ApiResponse forgot(@RequestBody ForgotPasswordRequest req) {
        return service.forgotPassword(req);
    }

    @PostMapping("/reset-password")
    public ApiResponse reset(@RequestBody ResetPasswordRequest req) {
        return service.resetPassword(req);
    }
    @PutMapping("/unlock/{username}")
    public ApiResponse unlockUser(@PathVariable String username) {
    	//UserService userService = null;
		return service.unlockUser(username);
    }
    
    
}