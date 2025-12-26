package com.inventory.auth.controller;


import com.inventory.auth.dto.LoginRequest;
import com.inventory.auth.dto.LoginResponse;
import com.inventory.auth.dto.RegisterRequest;
import com.inventory.auth.model.User;
import com.inventory.auth.security.JwtUtil;
import com.inventory.auth.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")

public class AuthController {

    private final TokenValidationController tokenValidationController;
	
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil, TokenValidationController tokenValidationController) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.tokenValidationController = tokenValidationController;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest req) {
        userService.register(req);
        return "OK";
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req) {
    		System.out.println(req);
        User u = userService.findByUsername(req.getUsername());
        if (u == null || !userService.verifyPassword(req.getPassword(), u.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        String token = jwtUtil.generateToken(u.getUsername(), u.getRole());
        return new LoginResponse(token, u.getUsername(), u.getRole());
    }

}
