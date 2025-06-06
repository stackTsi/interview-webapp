package com.example.interviewWebapp.Service;

import com.example.interviewWebapp.Dto.*;
import com.example.interviewWebapp.Entity.Interviews;
import com.example.interviewWebapp.Entity.Questions;
import com.example.interviewWebapp.Entity.Responses;
import com.example.interviewWebapp.Mapper.ResponseMapper;
import com.example.interviewWebapp.Repository.InterviewRepo;
import com.example.interviewWebapp.Repository.QuestionRepo;
import com.example.interviewWebapp.Repository.ResponseRepo;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ResponseService {
    private final ResponseRepo responseRepo;
    private final InterviewRepo interviewRepo;
    private final QuestionRepo questionRepo;
    private final ResponseMapper responseMapper;

    public ResponseService(ResponseRepo responseRepo, InterviewRepo interviewRepo, QuestionRepo questionRepo, ResponseMapper responseMapper) {
        this.responseRepo = responseRepo;
        this.interviewRepo = interviewRepo;
        this.questionRepo = questionRepo;
        this.responseMapper = responseMapper;
    }

    public void submitResponse(ObjectId interviewId, SubmitMultipleResponsesRequestDTO request) {
        Interviews interview = interviewRepo.findById(interviewId)
                .orElseThrow(()-> new NoSuchElementException("Interview not found"));
        for (SubmitResponseRequestDTO responseDTO : request.getResponses()) {
            ObjectId questionObjectId = responseDTO.getQuestionId();
            Questions question = questionRepo.findById(questionObjectId)
                    .orElseThrow(() -> new NoSuchElementException("Question not found"));

            Responses response = responseMapper.toEntity(interviewId.toHexString(), responseDTO);
            responseRepo.save(response);
        }
    }

    public List<GetResponsesDTO> getAllResponsesByInterviewId(ObjectId interviewId) {
        Interviews interview = interviewRepo.findById(interviewId)
                .orElseThrow(() -> new NoSuchElementException("Interview not found"));

        List<Responses> responses = responseRepo.findByInterviewId(interviewId);

        return responses.stream().map(response -> {
            GetResponsesDTO dto = new GetResponsesDTO();
            dto.setQuestionId(response.getQuestionId().toHexString());
            dto.setUserAnswer(response.getUserAnswer());
            dto.setQuestionOrder(response.getQuestionOrder());
            dto.setAnsweredAt(response.getAnsweredAt());

            questionRepo.findById(response.getQuestionId()).ifPresent(question -> {
                dto.setQuestionTitle(question.getTitle());
                dto.setQuestionContent(question.getContent());
            });

            return dto;
        }).toList();
    }


}
