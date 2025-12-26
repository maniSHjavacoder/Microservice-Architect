package com.inventory.auth.service;

import com.inventory.auth.dto.RegisterRequest;
import com.inventory.auth.model.User;
import com.inventory.auth.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class UserService {

    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User register(RegisterRequest req) {
        User u = new User();
        u.setUsername(req.getUsername());
        u.setPassword(encoder.encode(req.getPassword()));
        u.setRole(req.getRole() == null ? "ROLE_USER" : req.getRole());
        return repo.save(u);
    }

    public User findByUsername(String username) {
        return repo.findByUsername(username).orElse(null);
    }

    public boolean verifyPassword(String raw, String encoded) {
        return encoder.matches(raw, encoded);
    }

}
