package com.example.interviewWebapp.Entity;

import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "responses")
public class Responses {
    @Id
    private ObjectId id;

    @NotNull
    private ObjectId interviewId;

    @NotNull
    private ObjectId questionId;

    @Length(max = 5000)
    private String userAnswer;

    private Date answeredAt;

    @NotNull
    private int questionOrder;


    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(ObjectId interviewId) {
        this.interviewId = interviewId;
    }

    public ObjectId getQuestionId() {
        return questionId;
    }

    public void setQuestionId(ObjectId questionId) {
        this.questionId = questionId;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public Date getAnsweredAt() {
        return answeredAt;
    }

    public void setAnsweredAt(Date answeredAt) {
        this.answeredAt = answeredAt;
    }

    public int getQuestionOrder() {
        return questionOrder;
    }

    public void setQuestionOrder(int questionOrder) {
        this.questionOrder = questionOrder;
    }
}
