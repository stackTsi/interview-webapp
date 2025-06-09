package com.example.interviewWebapp.Repository;

import com.example.interviewWebapp.Entity.Enum.Category;
import com.example.interviewWebapp.Entity.Enum.Level;
import com.example.interviewWebapp.Entity.Questions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class QuestionCustomRepoImpl implements QuestionCustomRepo {

    private final MongoTemplate mongoTemplate;

    public QuestionCustomRepoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Page<Questions> findQuestions(Level level, Category category, Pageable pageable) {
        Query query = new Query();

        if (level != null) {
            query.addCriteria(Criteria.where("level").is(level));
        }

        if (category != null) {
            query.addCriteria(Criteria.where("category").is(category));
        }

        long total = mongoTemplate.count(query, Questions.class);
        query.with(pageable);
        List<Questions> questions = mongoTemplate.find(query, Questions.class);

        return new PageImpl<>(questions, pageable, total);
    }
}

