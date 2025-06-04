package com.example.interviewWebapp.Repository;

import com.example.interviewWebapp.Entity.Questions;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepo extends MongoRepository<Questions, ObjectId> {
    //find by objectID implementation here
}
