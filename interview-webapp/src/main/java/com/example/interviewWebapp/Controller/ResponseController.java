package com.example.interviewWebapp.Controller;

import com.example.interviewWebapp.Dto.*;
import com.example.interviewWebapp.Service.InterviewService;
import com.example.interviewWebapp.Service.ResponseService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interviews/{interviewId}/responses")
public class ResponseController {
    private final ResponseService responseService;

    public ResponseController(ResponseService responseService) {
        this.responseService = responseService;
    }

    @PostMapping
    public ResponseEntity<Void> submitResponse(
            @PathVariable ObjectId interviewId,
            @RequestBody SubmitMultipleResponsesRequestDTO request
    ) {
        responseService.submitResponse(interviewId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<GetResponsesDTO>> getAllResponses(@PathVariable("interviewId") ObjectId interviewId) {
        List<GetResponsesDTO> responses = responseService.getAllResponsesByInterviewId(interviewId);
        return ResponseEntity.ok(responses);
    }
}
