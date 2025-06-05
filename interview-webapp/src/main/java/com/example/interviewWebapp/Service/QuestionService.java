package com.example.interviewWebapp.Service;

import com.example.interviewWebapp.Entity.Enum.Category;
import com.example.interviewWebapp.Entity.Enum.Level;
import com.example.interviewWebapp.Entity.Questions;
import com.example.interviewWebapp.Repository.QuestionRepo;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionRepo questionRepo;


    public QuestionService(QuestionRepo questionRepo) {
        this.questionRepo = questionRepo;
    }

    public Questions createQuestions(Questions questions){
        return questionRepo.save(questions);
    }

    public Page<Questions> getAllQuestions(Level level, Category category, int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        if (level != null && category != null) {
            return questionRepo.findByLevelAndCategory(level, category, pageable);
        }
        return questionRepo.findAll(pageable);
    }

    public Optional<Questions> updateQuestion(ObjectId id, Questions updated){
        return questionRepo.findById(id).map(existing -> {
            existing.setTitle(updated.getTitle());
            existing.setContent(updated.getContent());
            existing.setLevel(updated.getLevel());
            existing.setCategory(updated.getCategory());
            existing.setType(updated.getType());
            existing.setOptions(updated.getOptions());
            existing.setCorrectAnswer(updated.getCorrectAnswer());
            return questionRepo.save(existing);
        });
    }

    public void deleteQuestion(ObjectId id){
        questionRepo.deleteById(id);
    }
}
