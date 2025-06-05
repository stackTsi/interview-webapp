package com.example.interviewWebapp.Repository;

import com.example.interviewWebapp.Entity.Responses;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseRepo extends MongoRepository<Responses, ObjectId> {

}
