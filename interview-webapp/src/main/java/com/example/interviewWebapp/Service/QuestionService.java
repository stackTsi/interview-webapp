package com.example.interviewWebapp.Service;

import com.example.interviewWebapp.Dto.CreateQuestionsRequestDTO;
import com.example.interviewWebapp.Dto.PagedResponseDTO;
import com.example.interviewWebapp.Dto.QuestionResponseDTO;
import com.example.interviewWebapp.Entity.Enum.Category;
import com.example.interviewWebapp.Entity.Enum.Level;
import com.example.interviewWebapp.Entity.Questions;
import com.example.interviewWebapp.Mapper.QuestionMapper;
import com.example.interviewWebapp.Repository.QuestionRepo;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionRepo questionRepo;
    private final QuestionMapper questionMapper;


    public QuestionService(QuestionRepo questionRepo, QuestionMapper questionMapper) {
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

    public Optional<Questions> updateQuestion(ObjectId id, CreateQuestionsRequestDTO dto){
        return questionRepo.findById(id).map(existing -> {
            existing.setTitle(dto.getTitle());
            existing.setContent(dto.getContent());
            existing.setLevel(dto.getLevel());
            existing.setCategory(dto.getCategory());
            existing.setType(dto.getType());
            existing.setOptions(dto.getOptions());
            existing.setCorrectAnswer(dto.getCorrectAnswer());
            existing.setUpdatedAt(new Date());
            return questionRepo.save(existing);
        });
    }

    public void deleteQuestion(ObjectId id){
        questionRepo.deleteById(id);
    }
}
