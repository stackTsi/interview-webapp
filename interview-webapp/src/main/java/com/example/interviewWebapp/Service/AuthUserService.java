package com.example.interviewWebapp.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.interviewWebapp.Repository.UserRepo;
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

}
