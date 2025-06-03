package com.example.interviewWebapp.runner;

import com.example.interviewWebapp.Service.AuthUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {

    private final AuthUserService userService;

    public StartupRunner(AuthUserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        userService.testConnection();
    }
}
