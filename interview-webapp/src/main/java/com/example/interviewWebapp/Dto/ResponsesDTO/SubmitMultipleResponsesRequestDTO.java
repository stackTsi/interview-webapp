package com.example.interviewWebapp.Dto.ResponsesDTO;

import java.util.List;

public class SubmitMultipleResponsesRequestDTO {
    private List<SubmitResponseRequestDTO> responses;

    public List<SubmitResponseRequestDTO> getResponses() {
        return responses;
    }

    public void setResponses(List<SubmitResponseRequestDTO> responses) {
        this.responses = responses;
    }
}
