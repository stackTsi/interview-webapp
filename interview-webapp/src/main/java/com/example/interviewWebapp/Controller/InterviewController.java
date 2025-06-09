package com.example.interviewWebapp.Controller;

import com.example.interviewWebapp.Dto.InterviewsDTO.InterviewResponseDTO;
import com.example.interviewWebapp.Dto.InterviewsDTO.StartInterviewRequestDTO;
import com.example.interviewWebapp.Entity.Users;
import com.example.interviewWebapp.Security.AuthUserService;
import com.example.interviewWebapp.Service.InterviewService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/interviews")
@PreAuthorize("hasRole('USER')")
public class InterviewController {
    private final InterviewService interviewService;
    private final AuthUserService authUserService;

    public InterviewController(InterviewService interviewService, AuthUserService authUserService) {
        this.interviewService = interviewService;
        this.authUserService = authUserService;
    }

    @PostMapping
    public ResponseEntity<InterviewResponseDTO> startInterview(@RequestBody StartInterviewRequestDTO request) {
        Users user = authUserService.getAuthenticatedUser()
                .orElseThrow(() -> new UsernameNotFoundException("Authenticated user not found"));

        InterviewResponseDTO response = interviewService.startInterview(user.getUsername(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterviewResponseDTO> getInterviewById(@PathVariable ObjectId id) {
        InterviewResponseDTO response = interviewService.getInterviewById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<InterviewResponseDTO> completeInterview(@PathVariable ObjectId id){
        Users user = authUserService.getAuthenticatedUser()
                .orElseThrow(()-> new UsernameNotFoundException("Authenticated user not found"));
        InterviewResponseDTO response = interviewService.completeInterview(id, user);
        return ResponseEntity.ok(response);
    }
}


