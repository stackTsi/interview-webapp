package com.example.interview_webapp.Repository;

import com.example.interview_webapp.Model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<Users, String> {
    Optional<Users> findByUsername(String username);

//    @Query("{username:'?0'}")
//    Users findByUsername(String username);
}
