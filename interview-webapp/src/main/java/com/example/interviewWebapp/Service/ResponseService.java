package com.example.interviewWebapp.Service;

import com.example.interviewWebapp.Dto.GetResponsesDTO;
import com.example.interviewWebapp.Dto.PagedResponseDTO;
import com.example.interviewWebapp.Dto.SubmitResponsesRequestDTO;
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
                .orElseThrow(()-> new NoSuchElementException("Interview not found"));
        ObjectId questionObjectId = request.getQuestionId();
        Questions questions = questionRepo.findById(questionObjectId)
                .orElseThrow(()-> new NoSuchElementException("Question not found"));
        Responses responses = responseMapper.toEntity(interviewId.toHexString(),request);

        responseRepo.save(responses);
    }

    public PagedResponseDTO<GetResponsesDTO> getAllResponsesByInterviewId(ObjectId interviewId, int page, int size) {
        Interviews interviews = interviewRepo.findById(interviewId)
                .orElseThrow(() -> new NoSuchElementException("Interview not found"));
        Pageable pageable = PageRequest.of(page, size);

        Page<Responses> responsesPage = responseRepo.findByInterviewId(interviewId, pageable);
        List<GetResponsesDTO> dtos = responsesPage.getContent().stream()
                .map(response -> {
                    GetResponsesDTO dto = responseMapper.toDTO(response);

                    questionRepo.findById(response.getQuestionId()).ifPresent(question -> {
                        dto.setQuestionTitle(question.getTitle());
                        dto.setQuestionContent(question.getContent());
                    });

                    return dto;
                })
                .toList();

        PagedResponseDTO<GetResponsesDTO> pagedResponse = new PagedResponseDTO<>();
        pagedResponse.setContent(dtos);
        pagedResponse.setPageNumber(responsesPage.getNumber());
        pagedResponse.setPageSize(responsesPage.getSize());
        pagedResponse.setTotalElements(responsesPage.getTotalElements());
        pagedResponse.setTotalPages(responsesPage.getTotalPages());
        pagedResponse.setLast(responsesPage.isLast());

        return pagedResponse;
    }


}
