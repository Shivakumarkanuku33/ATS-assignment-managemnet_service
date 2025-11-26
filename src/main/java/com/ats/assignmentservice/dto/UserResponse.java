package com.ats.assignmentservice.dto;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String role;
    private boolean active;  // Needed to check if employee is active
}

