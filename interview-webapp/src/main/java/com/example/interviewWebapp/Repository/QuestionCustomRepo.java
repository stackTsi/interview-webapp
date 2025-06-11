package com.example.interviewWebapp.Repository;

import com.example.interviewWebapp.Entity.Enum.Category;
import com.example.interviewWebapp.Entity.Enum.Level;
import com.example.interviewWebapp.Entity.Questions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionCustomRepo {
    Page<Questions> findQuestions(Level level, Category category, Pageable pageable);
}
