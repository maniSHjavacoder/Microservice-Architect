package com.inventory.auth.controller;
import com.inventory.auth.security.JwtUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")

public class TokenValidationController {
	
    private final JwtUtil jwtUtil;

    public TokenValidationController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/validate")
    public String validate(@RequestHeader("Authorization") String header) {
        if (header == null || !header.startsWith("Bearer ")) return "INVALID";
        String token = header.substring(7);
        return jwtUtil.isTokenValid(token) ? "VALID" : "INVALID";
    }

}
