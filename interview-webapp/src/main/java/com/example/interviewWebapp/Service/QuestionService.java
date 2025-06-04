package com.example.interviewWebapp.Service;

import com.example.interviewWebapp.Entity.Enum.Category;
import com.example.interviewWebapp.Entity.Enum.Level;
import com.example.interviewWebapp.Entity.Questions;
import com.example.interviewWebapp.Repository.QuestionRepo;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<Questions> getAllQuestions(Level level, Category category){
        if (level != null && category != null){
            return questionRepo.findAll().stream()
                    .filter(q -> q.getLevel().equals(level) && q.getCategory().equals(category))
                    .toList();
        }
        return questionRepo.findAll();
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
