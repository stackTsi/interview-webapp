package com.example.interviewWebapp.Security;

import com.example.interviewWebapp.Entity.Users;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.interviewWebapp.Repository.UserRepo;

import java.util.Optional;

@Service
public class AuthUserService implements UserDetailsService {
    private final UserRepo userRepo;

    public AuthUserService(UserRepo userRepo) {
        this.userRepo = userRepo;
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
}
