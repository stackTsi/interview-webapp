package com.example.interviewWebapp.Service;

import com.example.interviewWebapp.Dto.PagedResponseDTO;
import com.example.interviewWebapp.Dto.QuestionResponseDTO;
import com.example.interviewWebapp.Entity.Enum.Category;
import com.example.interviewWebapp.Entity.Enum.Level;
import com.example.interviewWebapp.Entity.Questions;
import com.example.interviewWebapp.Entity.Users;
import com.example.interviewWebapp.Mapper.QuestionMapper;
import com.example.interviewWebapp.Repository.QuestionRepo;
import com.example.interviewWebapp.Repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class QuestionService {
    private final UserRepo userRepo;
    private final QuestionRepo questionRepo;
    private final QuestionMapper questionMapper;


    public QuestionService(UserRepo userRepo, QuestionRepo questionRepo, QuestionMapper questionMapper) {
        this.userRepo = userRepo;
        this.questionRepo = questionRepo;
        this.questionMapper = questionMapper;
    }

    public Questions createQuestions(Questions questions){
        questions.setCreatedAt(new Date());
        questions.setUpdatedAt(new Date());
        return questionRepo.save(questions);
    }

    public PagedResponseDTO<QuestionResponseDTO> getAllQuestions(Level level, Category category, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Questions> questionsPage;
        if (level != null && category != null) {
            questionsPage = questionRepo.findByLevelAndCategory(level, category, pageable);
        } else {
            questionsPage = questionRepo.findAll(pageable);
        }
        List<QuestionResponseDTO> dtos = questionsPage.getContent().stream()
                .map(questionMapper::toDTO)
                .toList();

        PagedResponseDTO<QuestionResponseDTO> response = new PagedResponseDTO<>();
        response.setContent(dtos);
        response.setPageNumber(questionsPage.getNumber());
        response.setPageSize(questionsPage.getSize());
        response.setTotalElements(questionsPage.getTotalElements());
        response.setTotalPages(questionsPage.getTotalPages());
        response.setLast(questionsPage.isLast());


        return response;
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
