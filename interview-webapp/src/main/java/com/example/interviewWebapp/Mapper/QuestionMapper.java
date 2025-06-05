package com.example.interviewWebapp.Mapper;

import com.example.interviewWebapp.Dto.QuestionResponseDTO;
import com.example.interviewWebapp.Entity.Questions;
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

}
