package com.example.interviewWebapp.Mapper;


import com.example.interviewWebapp.Dto.InterviewResponseDTO;
import com.example.interviewWebapp.Entity.Interviews;

public class InterviewMapper {

    public static InterviewResponseDTO toDTO(Interviews interview) {
        InterviewResponseDTO dto = new InterviewResponseDTO();
        dto.setId(interview.getId().toHexString());
        dto.setUserId(interview.getUserId().toHexString());
        dto.setSelectedLevel(interview.getSelectedLevel().name());
        dto.setSelectedCategory(interview.getSelectedCategory().name());
        dto.setStartTime(interview.getStartTime());
        dto.setEndTime(interview.getEndTime());
        dto.setStatus(interview.getStatus().name());
        dto.setTotalQuestions(interview.getTotalQuestions());
        dto.setCreatedAt(interview.getCreatedAt());
        return dto;
    }


}

