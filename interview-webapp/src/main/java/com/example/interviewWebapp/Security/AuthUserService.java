package com.example.interviewWebapp.Security;

import com.example.interviewWebapp.Dto.UserDTO.RegisterRequestDTO;
import com.example.interviewWebapp.Entity.Enum.UserRole;
import com.example.interviewWebapp.Entity.Users;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.interviewWebapp.Repository.UserRepo;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthUserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthUserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public void testConnection() {
        long count = userRepo.count();
        System.out.println("connection OK, user count: " + count);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Optional<Users> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        String username = authentication.getName();
        return userRepo.findByUsername(username);
    }

    public Users registerNewUsers(RegisterRequestDTO dto){
        if (userRepo.findByUsername(dto.getUsername()).isPresent()){
            throw new IllegalArgumentException("Username already exists");
        }

        Users newUser = new Users();
        newUser.setUsername(dto.getUsername());
        newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        newUser.setFullName(dto.getFullName());
        newUser.setRole(dto.getRole() != null ? dto.getRole() : UserRole.USER);
        newUser.setCreatedAt(new Date());

        return userRepo.save(newUser);
    }

}
