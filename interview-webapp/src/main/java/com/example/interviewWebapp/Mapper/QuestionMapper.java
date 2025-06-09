package com.example.interviewWebapp.Mapper;

import com.example.interviewWebapp.Dto.QuestionDTO.CreateQuestionsRequestDTO;
import com.example.interviewWebapp.Dto.QuestionDTO.QuestionResponse;
import com.example.interviewWebapp.Entity.Questions;
import com.example.interviewWebapp.Entity.Users;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class QuestionMapper {
    private final ModelMapper modelMapper;

    public QuestionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public QuestionResponse toDTO(Questions questions){
        return modelMapper.map(questions, QuestionResponse.class);
    }
    public Questions toEntity(QuestionResponse dto){
        return modelMapper.map(dto, Questions.class);
    }

    public Questions toEntity(CreateQuestionsRequestDTO dto, Users createdBy) {
        Questions questions = new Questions();
        questions.setTitle(dto.getTitle());
        questions.setContent(dto.getContent());
        questions.setLevel(dto.getLevel());
        questions.setCategory(dto.getCategory());
        questions.setType(dto.getType());
        questions.setOptions(dto.getOptions());
        questions.setCorrectAnswer(dto.getCorrectAnswer());
        questions.setCreatedBy(createdBy);
        return questions;
    }


}
