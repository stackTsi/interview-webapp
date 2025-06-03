package com.example.interview_webapp.runner;

import com.example.interview_webapp.Service.AuthUserService;
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
