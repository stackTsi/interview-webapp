package com.example.interviewWebapp.Repository;

import com.example.interviewWebapp.Entity.Interviews;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewRepo extends MongoRepository<Interviews, ObjectId> {
    //impl
}
