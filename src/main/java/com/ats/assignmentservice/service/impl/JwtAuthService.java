package com.ats.assignmentservice.service.impl;

import org.springframework.stereotype.Service;
import com.ats.assignmentservice.dto.UserResponse;
import com.ats.assignmentservice.feign.UserServiceClient;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtAuthService {

    private final UserServiceClient userClient;

    public UserResponse validateUserRole(String authHeader) {
        UserResponse user = userClient.getCurrentUser(authHeader);

        if (!"ADMIN".equals(user.getRole()) && !"ASSET_MANAGER".equals(user.getRole())) {
            throw new RuntimeException("User not authorized for this operation");
        }

        return user;
    }
}

