package com.inventory.auth.dto;

import lombok.Data;

@Data
public class RegisterRequest {
	
    private String username;
    private String password;
    private String role; // optional: default ROLE_USER

}
