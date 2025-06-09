package com.example.interviewWebapp.Repository;

import com.example.interviewWebapp.Entity.Enum.Category;
import com.example.interviewWebapp.Entity.Enum.Level;
import com.example.interviewWebapp.Entity.Questions;
import com.mongodb.lang.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QuestionRepo extends MongoRepository<Questions, ObjectId>, QuestionCustomRepo {
    Page<Questions> findByLevelAndCategory(Level level, Category category, Pageable pageable);

    @NonNull
    Page<Questions> findAll(@NonNull Pageable pageable);

}
