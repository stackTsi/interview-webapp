package com.example.interviewWebapp.Controller;

import com.example.interviewWebapp.Dto.CreateQuestionsRequestDTO;
import com.example.interviewWebapp.Dto.PagedResponseDTO;
import com.example.interviewWebapp.Dto.QuestionResponseDTO;
import com.example.interviewWebapp.Entity.Enum.Category;
import com.example.interviewWebapp.Entity.Enum.Level;
import com.example.interviewWebapp.Entity.Questions;
import com.example.interviewWebapp.Entity.Users;
import com.example.interviewWebapp.Mapper.QuestionMapper;
import com.example.interviewWebapp.Security.AuthUserService;
import com.example.interviewWebapp.Service.QuestionService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/questions")
@PreAuthorize("hasRole('ADMIN')")
public class QuestionController {
    private final QuestionService questionService;
    private final QuestionMapper questionMapper;
    private final AuthUserService authUserService;
    public QuestionController(QuestionService questionService, QuestionMapper questionMapper, AuthUserService authUserService) {
        this.questionService = questionService;
        this.questionMapper = questionMapper;
        this.authUserService = authUserService;
    }

    @PostMapping
    public ResponseEntity<QuestionResponseDTO> createQuestion(
            @RequestBody CreateQuestionsRequestDTO dto
    ) {
        Users adminUser = authUserService.getAuthenticatedUser()
                .orElseThrow(() -> new RuntimeException("User not authenticated"));
        Questions question = questionMapper.toEntity(dto, adminUser);
        Questions saved = questionService.createQuestions(question);
        return ResponseEntity.status(HttpStatus.CREATED).body(questionMapper.toDTO(saved));
    }


    @GetMapping
    public ResponseEntity<PagedResponseDTO<QuestionResponseDTO>> getAllQuestions(
            @RequestParam(required = false) Level level,
            @RequestParam(required = false) Category category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size)
    {
        PagedResponseDTO<QuestionResponseDTO> response = questionService.getAllQuestions(level, category, page, size);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Questions> updateQuestion(@PathVariable ObjectId id, @RequestBody Questions question){
        return questionService.updateQuestion(id, question)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable ObjectId id){
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}


