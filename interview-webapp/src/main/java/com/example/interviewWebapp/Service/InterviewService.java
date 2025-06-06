package com.example.interviewWebapp.Service;


import com.example.interviewWebapp.Dto.InterviewResponseDTO;
import com.example.interviewWebapp.Dto.StartInterviewRequestDTO;
import com.example.interviewWebapp.Entity.Enum.Category;
import com.example.interviewWebapp.Entity.Enum.InterviewStatus;
import com.example.interviewWebapp.Entity.Enum.Level;
import com.example.interviewWebapp.Entity.Interviews;
import com.example.interviewWebapp.Entity.Questions;
import com.example.interviewWebapp.Entity.Users;
import com.example.interviewWebapp.Repository.InterviewRepo;
import com.example.interviewWebapp.Repository.QuestionRepo;
import com.example.interviewWebapp.Repository.UserRepo;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.NoSuchElementException;

@Service
public class InterviewService {
    private final UserRepo userRepo;
    private final QuestionRepo questionRepo;
    private final InterviewRepo interviewRepo;
    private final ModelMapper modelMapper;

    public InterviewService(UserRepo userRepo, QuestionRepo questionRepo, InterviewRepo interviewRepo, ModelMapper modelMapper) {
        this.userRepo = userRepo;
        this.questionRepo = questionRepo;
        this.interviewRepo = interviewRepo;
        this.modelMapper = modelMapper;
    }

    public InterviewResponseDTO startInterview(String username, StartInterviewRequestDTO request) {
        Users user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Level level = request.getSelectedLevel();
        Category category = request.getSelectedCategory();

        Page<Questions> questions = questionRepo.findByLevelAndCategory(level, category, Pageable.unpaged());
        if (questions.isEmpty()) {
            throw new IllegalStateException("No questions found for the specified level and category");
        }

        Interviews interview = new Interviews();
        interview.setUserId(user.getId());
        interview.setSelectedLevel(level);
        interview.setSelectedCategory(category);
        interview.setStartTime(new Date());
        interview.setStatus(InterviewStatus.IN_PROGRESS);
        interview.setTotalQuestions(questions.getContent().size());
        interview.setCreatedAt(new Date());

        interview = interviewRepo.save(interview);
        return modelMapper.map(interview, InterviewResponseDTO.class);
    }

    public InterviewResponseDTO getInterviewById(ObjectId id) {
        Interviews interview = interviewRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Interview not found"));
        return modelMapper.map(interview, InterviewResponseDTO.class);
    }

    public InterviewResponseDTO completeInterview(ObjectId id, Users authUsers){
        Interviews interview = interviewRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Interview not found"));

        if (!interview.getUserId().equals(authUsers.getId())){
            throw new AccessDeniedException("You cannot complete the interview of someone else");
        }

        if (interview.getStatus() == InterviewStatus.COMPLETED) {
            throw new IllegalStateException("Interview is already completed");
        }

        interview.setStatus(InterviewStatus.COMPLETED);
        interview.setEndTime(new Date());
        interviewRepo.save(interview);

        return modelMapper.map(interview, InterviewResponseDTO.class);
    }

}
