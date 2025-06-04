package com.example.interviewWebapp.Repository;

import com.example.interviewWebapp.Entity.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<Users, String> {
    Optional<Users> findByUsername(String username);

//    @Query("{username:'?0'}")
//    Users findByUsername(String username);
}
