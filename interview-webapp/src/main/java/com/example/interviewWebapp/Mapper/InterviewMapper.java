package com.example.interviewWebapp.Mapper;


import com.example.interviewWebapp.Dto.InterviewsDTO.InterviewResponse;
import com.example.interviewWebapp.Dto.InterviewsDTO.StartInterviewRequestDTO;
import com.example.interviewWebapp.Entity.Interviews;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class InterviewMapper {

    private final ModelMapper modelMapper;

    public InterviewMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public InterviewResponse toDTO(Interviews interview) {
        return modelMapper.map(interview, InterviewResponse.class);
    }

    public Interviews toEntity(StartInterviewRequestDTO dto) {
        return modelMapper.map(dto, Interviews.class);
    }
}


