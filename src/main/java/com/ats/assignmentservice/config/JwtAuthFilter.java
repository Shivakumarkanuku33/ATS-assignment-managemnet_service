//package com.ats.assignmentservice.config;
//
//import org.springframework.stereotype.Service;
//
//import com.ats.assignmentservice.dto.UserResponse;
//import com.ats.assignmentservice.feign.UserServiceClient;
//
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//public class JwtAuthFilter {
//
//    private final UserServiceClient userClient;
//
//    public UserResponse validateUserRole(String authHeader) {
//        UserResponse user = userClient.getCurrentUser(authHeader);
//
//        if (!"ADMIN".equals(user.getRole()) && !"ASSET_MANAGER".equals(user.getRole())) {
//            throw new RuntimeException("Not allowed for this user");
//        }
//
//        return user;
//    }
//}
