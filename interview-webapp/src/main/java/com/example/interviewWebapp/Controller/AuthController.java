package com.example.interviewWebapp.Controller;

import com.example.interviewWebapp.Entity.Users;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/me")
    public ResponseEntity<?> currentUser(Authentication authentication, HttpSession session) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }
        //[debug statement]: for authorization explanation
        String sessionID = session.getId();
        System.out.println(sessionID);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Roles: " + auth.getAuthorities());

        Users user = (Users) authentication.getPrincipal();
        return ResponseEntity.ok(Map.of(
                "id", user.getId(),
                "username", user.getUsername(),
                "role", user.getRole(),
                "fullName", user.getFullName()
        ));
    }
}
