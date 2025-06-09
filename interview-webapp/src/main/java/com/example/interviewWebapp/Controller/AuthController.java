package com.example.interviewWebapp.Controller;

import com.example.interviewWebapp.Dto.UserDTO.RegisterRequestDTO;
import com.example.interviewWebapp.Dto.UserDTO.RegisterResponseDTO;
import com.example.interviewWebapp.Entity.Users;
import com.example.interviewWebapp.Security.AuthUserService;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthUserService authUserService;
    private final ModelMapper modelMapper;

    public AuthController(AuthUserService authUserService, ModelMapper modelMapper) {
        this.authUserService = authUserService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/me")
    public ResponseEntity<?> currentUser(Authentication authentication, HttpSession session) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }
        Users user = (Users) authentication.getPrincipal();
        return ResponseEntity.ok(Map.of(
                "id", user.getId(),
                "username", user.getUsername(),
                "role", user.getRole(),
                "fullName", user.getFullName()
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequestDTO requestDTO){
        try {
            Users user = authUserService.registerNewUsers(requestDTO);

            RegisterResponseDTO responseDTO = modelMapper.map(user,RegisterResponseDTO.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
