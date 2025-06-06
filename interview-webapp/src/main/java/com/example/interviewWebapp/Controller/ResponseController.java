package com.example.interviewWebapp.Controller;

import com.example.interviewWebapp.Dto.GetResponsesDTO;
import com.example.interviewWebapp.Dto.PagedResponseDTO;
import com.example.interviewWebapp.Dto.SubmitResponsesRequestDTO;
import com.example.interviewWebapp.Service.ResponseService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @RequestBody SubmitResponsesRequestDTO request
    ) {
        responseService.submitResponse(interviewId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<PagedResponseDTO<GetResponsesDTO>> getAllResponse(
            @PathVariable ObjectId interviewId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
            ){
        return ResponseEntity.ok(responseService.getAllResponsesByInterviewId(interviewId, page, size));

    }
}
