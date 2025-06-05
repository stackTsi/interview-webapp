package com.example.interviewWebapp.Service;

import com.example.interviewWebapp.Dto.GetResponsesDTO;
import com.example.interviewWebapp.Dto.SubmitResponsesRequestDTO;
import com.example.interviewWebapp.Entity.Interviews;
import com.example.interviewWebapp.Entity.Questions;
import com.example.interviewWebapp.Entity.Responses;
import com.example.interviewWebapp.Mapper.ResponseMapper;
import com.example.interviewWebapp.Repository.InterviewRepo;
import com.example.interviewWebapp.Repository.QuestionRepo;
import com.example.interviewWebapp.Repository.ResponseRepo;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

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

    public void submitResponse(ObjectId interviewId, SubmitResponsesRequestDTO request) {
        Interviews interview = interviewRepo.findById(interviewId)
                .orElseThrow(()-> new NoSuchElementException("Interview not Found"));
        ObjectId questionObjectId = request.getQuestionId();
        Questions questions = questionRepo.findById(questionObjectId)
                .orElseThrow(()-> new NoSuchElementException("Question not found"));
        Responses responses = responseMapper.toEntity(interviewId.toHexString(),request);

        responseRepo.save(responses);
    }

    public Page<GetResponsesDTO> getAllResponsesByInterviewId(String interviewId) {
        throw new UnsupportedOperationException();
    }


}
