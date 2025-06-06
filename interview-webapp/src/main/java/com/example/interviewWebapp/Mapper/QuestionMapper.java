package com.example.interviewWebapp.Mapper;

import com.example.interviewWebapp.Dto.CreateQuestionsRequestDTO;
import com.example.interviewWebapp.Dto.QuestionResponseDTO;
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

    public QuestionResponseDTO toDTO(Questions questions){
        return modelMapper.map(questions, QuestionResponseDTO.class);
    }
    public Questions toEntity(QuestionResponseDTO dto){
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
