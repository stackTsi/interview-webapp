package com.example.interviewWebapp.Mapper;

import com.example.interviewWebapp.Dto.GetResponsesDTO;
import com.example.interviewWebapp.Dto.SubmitResponseRequestDTO;
import com.example.interviewWebapp.Entity.Responses;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ResponseMapper {
    private final ModelMapper modelMapper;

    public Responses toEntity(String interviewId, SubmitResponseRequestDTO dto) {
        Responses response = modelMapper.map(dto, Responses.class);
        response.setInterviewId(new ObjectId(interviewId));
        response.setQuestionId(dto.getQuestionId());
        response.setAnsweredAt(new Date());
        return response;
    }

    public ResponseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public GetResponsesDTO toDTO(Responses responses){
        return modelMapper.map(responses, GetResponsesDTO.class);
    }
}