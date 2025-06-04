package com.example.interviewWebapp.Controller;

import com.example.interviewWebapp.Entity.Enum.Category;
import com.example.interviewWebapp.Entity.Enum.Level;
import com.example.interviewWebapp.Entity.Questions;
import com.example.interviewWebapp.Service.QuestionService;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@PreAuthorize("hasRole('ADMIN')")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public ResponseEntity<Questions> createQuestion(@RequestBody Questions question) {
        return ResponseEntity.ok(questionService.createQuestions(question));
    }

    @GetMapping
    public ResponseEntity<List<Questions>> getAllQuestions(
            @RequestParam(required = false) Level level,
            @RequestParam(required = false) Category category){
        return ResponseEntity.ok(questionService.getAllQuestions(level, category));
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


