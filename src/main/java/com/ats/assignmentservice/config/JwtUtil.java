//package com.ats.assignmentservice.config;
//
//import io.jsonwebtoken.*;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.SecretKey;
//import java.util.List;
//
//@Component
//public class JwtUtil {
//
//	private final SecretKey secretKey;
//
//	public JwtUtil(@Value("${jwt.secret}") String base64Secret) {
//		this.secretKey = Keys.hmacShaKeyFor(java.util.Base64.getDecoder().decode(base64Secret));
//	}
//
//	public Jws<Claims> validateToken(String token) {
//		return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
//	}
//}
