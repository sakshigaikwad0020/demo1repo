package com.rangoli.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rangoli.dto.LoginRequest;
import com.rangoli.entity.User;
import com.rangoli.service.UserService;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")

public class AuthController {
private final UserService userService ;
public AuthController(UserService userService) {
	this.userService =userService;
}

@PostMapping("/register")
public ResponseEntity<?>register(@RequestBody User user){
	
	userService.register(user);
	return ResponseEntity.ok("user registered successfully");
	
}
@PostMapping("/login")
public ResponseEntity<?>login(@RequestBody LoginRequest request){
	
	
	UserService authService = null;
	return ResponseEntity.ok(authService.login(request));
	
}
}
